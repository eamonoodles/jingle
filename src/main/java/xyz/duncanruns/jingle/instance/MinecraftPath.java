package xyz.duncanruns.jingle.instance;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Helper class for resolving Minecraft instance paths.
 */
public class MinecraftPath {
    private final Path instanceRoot;
    private final Path dotMinecraft;

    public MinecraftPath(Path instancePath) {
        this.instanceRoot = instancePath;
        // Handle both .minecraft directly and launcher-specific paths
        this.dotMinecraft = instancePath.getFileName().toString().equals(".minecraft") ?
            instancePath :
            instancePath.resolve(".minecraft");
    }

    public Path getOptionsFile() {
        return dotMinecraft.resolve("options.txt");
    }

    public Path getModsFolder() {
        return dotMinecraft.resolve("mods");
    }

    public Path getVersionsFolder() {
        return dotMinecraft.resolve("versions");
    }

    public Path getLogFile() {
        return dotMinecraft.resolve("logs").resolve("latest.log");
    }

    public Path getDotMinecraft() {
        return dotMinecraft;
    }

    public Path getInstanceRoot() {
        return instanceRoot;
    }
}