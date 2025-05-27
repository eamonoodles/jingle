package xyz.duncanruns.jingle.platform.macos;

import xyz.duncanruns.jingle.platform.PlatformWindow;
import xyz.duncanruns.jingle.platform.PlatformWindowManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced macOS implementation of the PlatformWindowManager interface.
 * This class provides macOS-specific window management functionality with improved window detection.
 */
public class MacOSWindowManager implements PlatformWindowManager {

    @Override
    public List<PlatformWindow> findWindowsByApplication(String applicationName) {
        List<PlatformWindow> windows = new ArrayList<>();
        for (Window window : Window.getWindows()) {
            if (window.isDisplayable() && window.isVisible()) {
                String title = getWindowTitle(window);
                if (title != null && title.contains(applicationName)) {
                    windows.add(new MacOSWindow(window));
                }
            }
        }
        return windows;
    }

    @Override
    public PlatformWindow findWindowByTitle(String windowTitle) {
        for (Window window : Window.getWindows()) {
            if (window.isDisplayable() && window.isVisible()) {
                String title = getWindowTitle(window);
                if (title != null && title.equals(windowTitle)) {
                    return new MacOSWindow(window);
                }
            }
        }
        return null;
    }

    @Override
    public PlatformWindow findMinecraftWindow() {
        return findWindowByTitle("Minecraft*");
    }

    @Override
    public PlatformWindow createWindowFromId(Object windowId) {
        if (!(windowId instanceof Window)) {
            throw new IllegalArgumentException("Window ID must be a Window for macOS platform");
        }
        return new MacOSWindow((Window) windowId);
    }

    @Override
    public int[] getScreenDimensions() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        return new int[]{dm.getWidth(), dm.getHeight()};
    }

    @Override
    public Object getWindowHandle(String windowTitle) {
        for (Window window : Window.getWindows()) {
            if (window.isDisplayable() && window.isVisible()) {
                String title = getWindowTitle(window);
                if (title != null && title.equals(windowTitle)) {
                    return window;
                }
            }
        }
        return null;
    }

    @Override
    public void setWindowBounds(Object windowHandle, Rectangle bounds) {
        if (!(windowHandle instanceof Window)) {
            throw new IllegalArgumentException("Window handle must be a Window for macOS platform");
        }
        ((Window) windowHandle).setBounds(bounds);
    }

    @Override
    public String getWindowTitle(Object windowHandle) {
        if (!(windowHandle instanceof Window)) {
            throw new IllegalArgumentException("Window handle must be a Window for macOS platform");
        }
        Window window = (Window) windowHandle;
        if (window instanceof Frame) {
            return ((Frame) window).getTitle();
        }
        if (window instanceof Dialog) {
            return ((Dialog) window).getTitle();
        }
        return null;
    }

    @Override
    public boolean isWindowValid(Object windowHandle) {
        if (!(windowHandle instanceof Window)) {
            return false;
        }
        Window window = (Window) windowHandle;
        return window.isDisplayable() && window.isValid();
    }

    @Override
    public Object getForegroundWindow() {
        return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
    }

    @Override
    public void setWindowTitle(Object windowHandle, String title) {
        if (!(windowHandle instanceof Window)) {
            throw new IllegalArgumentException("Window handle must be a Window for macOS platform");
        }
        Window window = (Window) windowHandle;
        if (window instanceof Frame) {
            ((Frame) window).setTitle(title);
        } else if (window instanceof Dialog) {
            ((Dialog) window).setTitle(title);
        }
    }

    @Override
    public Rectangle getWindowBounds(Object windowHandle) {
        if (!(windowHandle instanceof Window)) {
            throw new IllegalArgumentException("Window handle must be a Window for macOS platform");
        }
        return ((Window) windowHandle).getBounds();
    }
}
