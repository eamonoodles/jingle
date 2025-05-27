package xyz.duncanruns.jingle.platform.macos;

import xyz.duncanruns.jingle.platform.PlatformWindow;

import java.awt.*;

/**
 * macOS implementation of the PlatformWindow interface.
 * This class wraps a macOS NSWindow and provides platform-specific window management.
 */
public class MacOSWindow implements PlatformWindow {
    private final Window window;

    /**
     * Creates a new MacOSWindow for the specified NSWindow pointer.
     *
     * @param window The NSWindow pointer to wrap
     */
    public MacOSWindow(Window window) {
        this.window = window;
    }

    @Override
    public Object getNativeWindow() {
        return window;
    }

    @Override
    public String getTitle() {
        return window instanceof Frame ? ((Frame) window).getTitle() : "";
    }

    @Override
    public void setTitle(String title) {
        if (window instanceof Frame) {
            ((Frame) window).setTitle(title);
        }
    }

    @Override
    public Rectangle getBounds() {
        return window.getBounds();
    }

    @Override
    public void setBounds(Rectangle bounds) {
        window.setBounds(bounds);
    }

    @Override
    public boolean isForeground() {
        Window focusedWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
        return window.equals(focusedWindow);
    }

    @Override
    public boolean isValid() {
        return window.isValid() && window.isVisible();
    }
}
