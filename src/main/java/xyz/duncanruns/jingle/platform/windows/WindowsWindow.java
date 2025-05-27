package xyz.duncanruns.jingle.platform.windows;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.RECT;
import xyz.duncanruns.jingle.platform.PlatformWindow;
import xyz.duncanruns.jingle.win32.User32Extended;

import java.awt.Rectangle;

/**
 * Windows implementation of the PlatformWindow interface.
 * This class wraps a Windows HWND and provides platform-specific window management.
 */
public class WindowsWindow implements PlatformWindow {
    private final WinDef.HWND hwnd;
    private final User32Extended user32 = User32.INSTANCE;

    /**
     * Creates a new WindowsWindow for the specified HWND.
     * 
     * @param hwnd The Windows HWND to wrap
     */
    public WindowsWindow(WinDef.HWND hwnd) {
        this.hwnd = hwnd;
    }

    @Override
    public Object getNativeWindow() {
        return hwnd;
    }

    @Override
    public String getTitle() {
        char[] buffer = new char[1024];
        user32.GetWindowTextA(hwnd, buffer, buffer.length);
        return new String(buffer).trim();
    }

    @Override
    public void setTitle(String title) {
        user32.SetWindowTextA(hwnd, title);
    }

    @Override
    public Rectangle getBounds() {
        RECT rect = new RECT();
        user32.GetWindowRect(hwnd, rect);
        return new Rectangle(rect.left, rect.top, 
                           rect.right - rect.left, 
                           rect.bottom - rect.top);
    }

    @Override
    public void setBounds(Rectangle bounds) {
        user32.MoveWindow(hwnd, bounds.x, bounds.y, 
                         bounds.width, bounds.height, true);
    }

    @Override
    public boolean isForeground() {
        return hwnd.equals(user32.GetForegroundWindow());
    }

    @Override
    public boolean isValid() {
        return user32.IsWindow(hwnd);
    }
}
