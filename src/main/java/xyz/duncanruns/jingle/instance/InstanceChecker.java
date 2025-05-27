package xyz.duncanruns.jingle.instance;

import xyz.duncanruns.jingle.platform.PlatformFactory;
import xyz.duncanruns.jingle.platform.PlatformWindow;
import xyz.duncanruns.jingle.platform.PlatformWindowManager;
import xyz.duncanruns.jingle.util.MCVersionUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The instance checker will run checks for Minecraft instances every second while Jingle has missing instances.
 * When it finds new instances, it will send an InstancesFoundQMessage to Jingle.
 * Additionally, it will remember all opened Minecraft instances on the computer for whenever needed (redetect instances).
 */
public final class InstanceChecker {
    private static final Pattern VERSION_PATTERN = Pattern.compile("\\d+\\.\\d+(?:\\.\\d+)?(?:-pre\\d+)?(?:-rc\\d+)?");
    private static final PlatformWindowManager windowManager = PlatformFactory.createWindowManager();

    private InstanceChecker() {
    }

    /**
     * Checks for new windows, then checks if they are minecraft windows and gets their path.
     * <p>
     * A general expected execution time is as follows:
     * <li>0.8ms-5ms when there are new non-Minecraft windows</li>
     * <li>300ms or more when there are new Minecraft windows</li>
     * <li>Otherwise 0.5ms-1.2ms</li>
     */
    private static void runChecks() {
        Set<OpenedInstanceInfo> instances = new HashSet<>();
        for (PlatformWindow window : windowManager.findWindowsByApplication("Minecraft")) {
            if (!window.isValid()) continue;

            String title = window.getTitle();
            if (!title.startsWith("Minecraft*")) continue;

            // Extract version from launcher_log.txt
            String version = getVersionFromPath(Paths.get(System.getProperty("user.home")));
            if (version == null) continue;

            Path instancePath = findInstancePath(version);
            if (instancePath == null) continue;

            instances.add(new OpenedInstanceInfo(
                    instancePath,
                    window.getNativeWindow(),
                    version
            ));
        }
        // Remove any opened instance windows that are NOT REAL!!!
        instances.removeIf(i -> !windowManager.isWindowValid(i.windowHandle));
        // Replace the last checked windows set
        lastCheckedWindows = instances;
    }

    private static Set<OpenedInstanceInfo> lastCheckedWindows = new HashSet<>();

    /**
     * Warning: can be blocking for hundreds of milliseconds!
     */
    public synchronized static Set<OpenedInstanceInfo> getAllOpenedInstances() {
        runChecks();
        // Return a set with lazy copies of the opened instances
        return new HashSet<>(lastCheckedWindows);
    }

    private static String getVersionFromPath(Path path) {
        try {
            Path launcherLogPath = path.resolve(".minecraft").resolve("launcher_log.txt");
            if (!Files.exists(launcherLogPath)) return null;

            String launcherLog = new String(Files.readAllBytes(launcherLogPath));
            Matcher matcher = VERSION_PATTERN.matcher(launcherLog);

            String latestVersion = null;
            while (matcher.find()) {
                String version = matcher.group();
                if (latestVersion == null || MCVersionUtil.isNewerThan(version, latestVersion)) {
                    latestVersion = version;
                }
            }
            return latestVersion;
        } catch (IOException e) {
            return null;
        }
    }

    private static Path findInstancePath(String version) {
        // First check .minecraft
        Path dotMinecraft = Paths.get(System.getProperty("user.home"), ".minecraft");
        if (isValidInstancePath(dotMinecraft, version)) {
            return dotMinecraft;
        }

        // Then check MultiMC/PrismLauncher instances
        Path[] launcherPaths = {
                Paths.get(System.getProperty("user.home"), "MultiMC", "instances"),
                Paths.get(System.getProperty("user.home"), "PrismLauncher", "instances"),
                Paths.get(System.getProperty("user.home"), ".local", "share", "multimc", "instances"),
                Paths.get(System.getProperty("user.home"), ".local", "share", "prismlauncher", "instances"),
                Paths.get("/Applications/MultiMC.app/Data/instances"),
                Paths.get("/Applications/PrismLauncher.app/Data/instances")
        };

        for (Path launcherPath : launcherPaths) {
            if (!Files.isDirectory(launcherPath)) continue;

            try {
                for (Path instancePath : Files.newDirectoryStream(launcherPath)) {
                    if (isValidInstancePath(instancePath, version)) {
                        return instancePath;
                    }
                }
            } catch (IOException e) {
                // Skip this launcher path if we can't read it
                continue;
            }
        }

        return null;
    }

    private static boolean isValidInstancePath(Path path, String version) {
        if (!Files.isDirectory(path)) return false;

        // Check for version file/folder
        Path[] versionPaths = {
                path.resolve(".minecraft").resolve("versions").resolve(version),
                path.resolve("versions").resolve(version),
                path.resolve("version.json")
        };

        for (Path versionPath : versionPaths) {
            if (Files.exists(versionPath)) {
                return true;
            }
        }

        return false;
    }
}
