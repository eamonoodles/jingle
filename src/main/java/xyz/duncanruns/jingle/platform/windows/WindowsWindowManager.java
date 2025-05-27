package xyz.duncanruns.jingle.platform.windows;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import xyz.duncanruns.jingle.platform.PlatformWindow;
import xyz.duncanruns.jingle.platform.PlatformWindowManager;
import xyz.duncanruns.jingle.win32.User32Extended;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Windows implementation of the PlatformWindowManager interface.
 * This class provides Windows-specific window management functionality.
 */
public class WindowsWindowManager implements PlatformWindowManager {
    private final User32Extended user32 = User32.INSTANCE;

    @Override
    public List<PlatformWindow> findWindowsByApplication(String applicationName) {
        List<PlatformWindow> windows = new ArrayList<>();
        user32.EnumWindows((hwnd, pointer) -> {
            if (user32.IsWindowVisible(hwnd)) {
                String title = getWindowTitle(hwnd);
                if (title != null && title.contains(applicationName)) {
                    windows.add(new WindowsWindow(hwnd));
                }
            }
            return true;
        }, null);
        return windows;
    }

    @Override
    public PlatformWindow findWindowByTitle(String windowTitle) {
        WinDef.HWND hwnd = user32.FindWindowA(null, windowTitle);
        return hwnd != null ? new WindowsWindow(hwnd) : null;
    }

    @Override
    public PlatformWindow findMinecraftWindow() {
        return findWindowByTitle("Minecraft*");
    }

    @Override
    public PlatformWindow createWindowFromId(Object windowId) {
        if (!(windowId instanceof WinDef.HWND)) {
            throw new IllegalArgumentException("Window ID must be a HWND for Windows platform");
        }
        return new WindowsWindow((WinDef.HWND) windowId);
    }

    @Override
    public int[] getScreenDimensions() {
        return new int[]{
            user32.GetSystemMetrics(0), // SM_CXSCREEN
            user32.GetSystemMetrics(1)  // SM_CYSCREEN
        };
    }

    @Override
    public Object getWindowHandle(String windowTitle) {
        return user32.FindWindowA(null, windowTitle);
    }

    @Override
    public void setWindowBounds(Object windowHandle, Rectangle bounds) {
        if (!(windowHandle instanceof WinDef.HWND)) {
            throw new IllegalArgumentException("Window handle must be a HWND for Windows platform");
        }
        user32.MoveWindow(
            (WinDef.HWND) windowHandle,
            bounds.x,
            bounds.y,
            bounds.width,
            bounds.height,
            true
        );
    }

    @Override
    public String getWindowTitle(Object windowHandle) {
        if (!(windowHandle instanceof WinDef.HWND)) {
            throw new IllegalArgumentException("Window handle must be a HWND for Windows platform");
        }
        char[] buffer = new char[1024];
        user32.GetWindowTextA((WinDef.HWND) windowHandle, buffer, buffer.length);
        return new String(buffer).trim();
    }

    @Override
    public boolean isWindowValid(Object windowHandle) {
        if (!(windowHandle instanceof WinDef.HWND)) {
            return false;
        }
        return user32.IsWindow((WinDef.HWND) windowHandle);
    }

    @Override
    public Object getForegroundWindow() {
        return user32.GetForegroundWindow();
    }

    @Override
    public void setWindowTitle(Object windowHandle, String title) {
        if (!(windowHandle instanceof WinDef.HWND)) {
            throw new IllegalArgumentException("Window handle must be a HWND for Windows platform");
        }
        user32.SetWindowTextA((WinDef.HWND) windowHandle, title);
    }

    @Override
    public Rectangle getWindowBounds(Object windowHandle) {
        if (!(windowHandle instanceof WinDef.HWND)) {
            throw new IllegalArgumentException("Window handle must be a HWND for Windows platform");
        }
        WinDef.RECT rect = new WinDef.RECT();
        user32.GetWindowRect((WinDef.HWND) windowHandle, rect);
        return new Rectangle(rect.left, rect.top, 
                           rect.right - rect.left, 
                           rect.bottom - rect.top);
    }
}
