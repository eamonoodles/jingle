package xyz.duncanruns.jingle.platform;

import java.awt.Rectangle;

/**
 * Interface representing a platform-specific window.
 * This provides a common interface for window operations across different platforms.
 */
public interface PlatformWindow {
    /**
     * Gets the native window handle/identifier
     */
    Object getNativeWindow();
    
    /**
     * Gets the window title
     */
    String getTitle();
    
    /**
     * Sets the window title
     */
    void setTitle(String title);
    
    /**
     * Gets the window bounds
     */
    Rectangle getBounds();
    
    /**
     * Sets the window bounds
     */
    void setBounds(Rectangle bounds);
    
    /**
     * Checks if this window is the foreground/focused window
     */
    boolean isForeground();
    
    /**
     * Checks if the window is still valid
     */
    boolean isValid();
}
