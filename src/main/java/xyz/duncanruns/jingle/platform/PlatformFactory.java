package xyz.duncanruns.jingle.platform;

import xyz.duncanruns.jingle.platform.macos.MacOSInputManager;
import xyz.duncanruns.jingle.platform.windows.WindowsInputManager;

/**
 * Factory for creating platform-specific implementations.
 * This class detects the current platform and returns the appropriate implementation.
 */
public class PlatformFactory {
    
    /**
     * Enum representing supported platforms.
     */
    public enum Platform {
        WINDOWS,
        MACOS,
        LINUX,
        UNKNOWN
    }
    
    private static Platform currentPlatform = null;
    private static PlatformInputManager inputManager;

    /**
     * Detects the current platform.
     * 
     * @return The detected platform
     */
    public static Platform detectPlatform() {
        if (currentPlatform != null) {
            return currentPlatform;
        }
        
        String osName = System.getProperty("os.name").toLowerCase();
        
        if (osName.contains("win")) {
            currentPlatform = Platform.WINDOWS;
        } else if (osName.contains("mac") || osName.contains("darwin")) {
            currentPlatform = Platform.MACOS;
        } else if (osName.contains("nux") || osName.contains("nix") || osName.contains("aix")) {
            currentPlatform = Platform.LINUX;
        } else {
            currentPlatform = Platform.UNKNOWN;
        }
        
        return currentPlatform;
    }
    
    /**
     * Creates a platform-specific window manager.
     * 
     * @return A PlatformWindowManager implementation for the current platform
     * @throws UnsupportedOperationException if the platform is not supported
     */
    public static PlatformWindowManager createWindowManager() {
        Platform platform = detectPlatform();
        
        switch (platform) {
            case WINDOWS:
                return new xyz.duncanruns.jingle.platform.windows.WindowsWindowManager();
            case MACOS:
                return new xyz.duncanruns.jingle.platform.macos.MacOSWindowManager();
            case LINUX:
                // Not implemented yet
                throw new UnsupportedOperationException("Linux platform is not yet supported");
            default:
                throw new UnsupportedOperationException("Unsupported platform: " + platform);
        }
    }
    
    /**
     * Creates or returns a platform-specific input manager.
     * 
     * @return A PlatformInputManager implementation for the current platform
     * @throws UnsupportedOperationException if the platform is not supported
     */
    public static synchronized PlatformInputManager getInputManager() {
        if (inputManager == null) {
            Platform platform = detectPlatform();
            switch (platform) {
                case WINDOWS:
                    inputManager = new WindowsInputManager();
                    break;
                case MACOS:
                    inputManager = new MacOSInputManager();
                    break;
                case LINUX:
                    throw new UnsupportedOperationException("Linux platform is not yet supported");
                default:
                    throw new UnsupportedOperationException("Unsupported platform: " + platform);
            }
        }
        return inputManager;
    }

    /**
     * Cleans up platform-specific resources.
     */
    public static void cleanup() {
        if (inputManager != null) {
            inputManager.cleanup();
            inputManager = null;
        }
    }
}
