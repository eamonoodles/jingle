package xyz.duncanruns.jingle.platform;

import java.awt.Rectangle;
import java.util.List;

/**
 * Interface for platform-specific window management operations.
 * This abstraction allows for cross-platform window management functionality.
 */
public interface PlatformWindowManager {
    
    /**
     * Finds all windows for a specific application.
     * 
     * @param applicationName The name of the application to find windows for
     * @return A list of PlatformWindow objects representing the application's windows
     */
    List<PlatformWindow> findWindowsByApplication(String applicationName);
    
    /**
     * Finds a window by its title.
     * 
     * @param windowTitle The title of the window to find
     * @return The PlatformWindow if found, null otherwise
     */
    PlatformWindow findWindowByTitle(String windowTitle);
    
    /**
     * Finds a Minecraft window.
     * This is a convenience method specifically for Jingle's primary use case.
     * 
     * @return The PlatformWindow for Minecraft if found, null otherwise
     */
    PlatformWindow findMinecraftWindow();
    
    /**
     * Creates a wrapper for an existing window using its platform-specific identifier.
     * 
     * @param windowId The platform-specific window identifier
     * @return A PlatformWindow wrapper for the specified window
     */
    PlatformWindow createWindowFromId(Object windowId);
    
    /**
     * Gets the screen dimensions.
     * 
     * @return An array of integers representing the screen width and height
     */
    int[] getScreenDimensions();
    
    /**
     * Gets the handle or identifier for a window
     * @param windowTitle The title of the window to find
     * @return Platform-specific window handle/identifier
     */
    Object getWindowHandle(String windowTitle);
    
    /**
     * Moves and resizes a window
     * @param windowHandle Platform-specific window handle
     * @param bounds The new bounds for the window
     */
    void setWindowBounds(Object windowHandle, Rectangle bounds);
    
    /**
     * Gets the title of a window
     * @param windowHandle Platform-specific window handle
     * @return The window title
     */
    String getWindowTitle(Object windowHandle);
    
    /**
     * Checks if a window is still valid/exists
     * @param windowHandle Platform-specific window handle
     * @return true if the window exists and is valid
     */
    boolean isWindowValid(Object windowHandle);
    
    /**
     * Gets the handle of the currently focused/foreground window
     * @return Platform-specific window handle
     */
    Object getForegroundWindow();
    
    /**
     * Sets the title of a window
     * @param windowHandle Platform-specific window handle
     * @param title The new title
     */
    void setWindowTitle(Object windowHandle, String title);
    
    /**
     * Gets the window bounds for a window
     * @param windowHandle Platform-specific window handle
     * @return The window bounds
     */
    Rectangle getWindowBounds(Object windowHandle);
}
