package xyz.duncanruns.jingle.instance;

import xyz.duncanruns.jingle.platform.PlatformFactory;
import xyz.duncanruns.jingle.platform.PlatformWindowManager;
import xyz.duncanruns.jingle.util.KeyPresser;
import xyz.duncanruns.jingle.util.MCVersionUtil;
import xyz.duncanruns.jingle.instance.state.InstanceState;
import xyz.duncanruns.jingle.instance.state.InstanceStateTracker;
import xyz.duncanruns.jingle.instance.options.OptionsFileUtil;
import xyz.duncanruns.jingle.instance.mods.FabricModFolder;

import java.awt.Rectangle;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public class OpenedInstance {
    private static final PlatformWindowManager windowManager = PlatformFactory.createWindowManager();
    
    public final Path instancePath;
    public final Object windowHandle;
    public final String versionString;
    public final MinecraftPath mcPath;
    public final FabricModFolder fabricModFolder;
    public final OptionsFileUtil.StandardSettings standardSettings;
    public final OptionsFileUtil.OptionsText optionsTxt;
    public final InstanceStateTracker stateTracker;
    public final KeyPresser keyPresser;

    public OpenedInstance(OpenedInstanceInfo info, BiConsumer<InstanceState, InstanceState> onStateChange) {
        this.instancePath = info.instancePath;
        this.windowHandle = info.windowHandle;
        this.versionString = info.versionString;
        
        // Initialize MinecraftPath helper
        this.mcPath = new MinecraftPath(instancePath);
        
        // Initialize components with proper paths
        this.fabricModFolder = new FabricModFolder(mcPath.getDotMinecraft());
        this.standardSettings = new OptionsFileUtil.StandardSettings(mcPath.getDotMinecraft());
        this.optionsTxt = new OptionsFileUtil.OptionsText(mcPath.getDotMinecraft());
        this.stateTracker = new InstanceStateTracker(this, onStateChange);
        this.keyPresser = new KeyPresser(this);
    }

    /**
     * Gets the current window title of this instance.
     */
    public String getWindowTitle() {
        return windowManager.getWindowTitle(windowHandle);
    }

    /**
     * Sets the window title of this instance.
     */
    public void setWindowTitle(String title) {
        windowManager.setWindowTitle(windowHandle, title);
    }

    /**
     * Gets the window bounds of this instance.
     */
    public Rectangle getBounds() {
        return windowManager.getWindowBounds(windowHandle);
    }

    /**
     * Sets the window bounds of this instance.
     */
    public void setBounds(Rectangle bounds) {
        windowManager.setWindowBounds(windowHandle, bounds);
    }

    /**
     * Checks if this instance's window is still valid.
     */
    public boolean isValid() {
        return windowManager.isWindowValid(windowHandle);
    }

    /**
     * Checks if this instance's window is currently focused.
     */
    public boolean isForeground() {
        return windowHandle.equals(windowManager.getForegroundWindow());
    }

    /**
     * Gets whether this instance is newer than the specified version.
     */
    public boolean isNewerThan(String version) {
        return MCVersionUtil.isNewerThan(this.versionString, version);
    }

    /**
     * Resolves a path within the Minecraft directory.
     */
    public Path getPath(String... subPaths) {
        Path result = mcPath.getDotMinecraft();
        for (String subPath : subPaths) {
            result = result.resolve(subPath);
        }
        return result;
    }
}
