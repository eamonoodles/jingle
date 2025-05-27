package xyz.duncanruns.jingle.platform.windows;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import xyz.duncanruns.jingle.platform.PlatformInputManager;
import xyz.duncanruns.jingle.win32.User32Extended;

import java.util.ArrayList;
import java.util.List;

public class WindowsInputManager implements PlatformInputManager {
    private final User32Extended user32 = User32Extended.INSTANCE;
    private static final int KEY_BUFFER_SIZE = 256;
    private final char[] keyNameBuffer = new char[KEY_BUFFER_SIZE];

    @Override
    public void keyDown(int keyCode) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.ki.wVk = new WinDef.WORD(keyCode);
        input.input.ki.dwFlags = new WinDef.DWORD(0);
        user32.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    @Override
    public void keyUp(int keyCode) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        input.input.setType("ki");
        input.input.ki.wScan = new WinDef.WORD(0);
        input.input.ki.time = new WinDef.DWORD(0);
        input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
        input.input.ki.wVk = new WinDef.WORD(keyCode);
        input.input.ki.dwFlags = new WinDef.DWORD(2); // KEYEVENTF_KEYUP
        user32.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return user32.GetAsyncKeyState(keyCode) < 0;
    }

    @Override
    public List<Integer> getPressedKeys() {
        List<Integer> pressedKeys = new ArrayList<>();
        for (int keyCode = 0; keyCode < 256; keyCode++) {
            if (isKeyPressed(keyCode)) {
                pressedKeys.add(keyCode);
            }
        }
        return pressedKeys;
    }

    @Override
    public void mouseDown(int button) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType("mi");
        input.input.mi.dx = new WinDef.LONG(0);
        input.input.mi.dy = new WinDef.LONG(0);
        input.input.mi.mouseData = new WinDef.DWORD(button == 4 || button == 5 ? button - 3 : 0);
        input.input.mi.dwFlags = new WinDef.DWORD(getMouseDownFlag(button));
        user32.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    @Override
    public void mouseUp(int button) {
        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType("mi");
        input.input.mi.dx = new WinDef.LONG(0);
        input.input.mi.dy = new WinDef.LONG(0);
        input.input.mi.mouseData = new WinDef.DWORD(button == 4 || button == 5 ? button - 3 : 0);
        input.input.mi.dwFlags = new WinDef.DWORD(getMouseUpFlag(button));
        user32.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    @Override
    public void moveMouse(int x, int y) {
        int screenWidth = user32.GetSystemMetrics(User32Extended.SM_CXSCREEN);
        int screenHeight = user32.GetSystemMetrics(User32Extended.SM_CYSCREEN);
        
        int normalizedX = (x * 65535) / screenWidth;
        int normalizedY = (y * 65535) / screenHeight;

        WinUser.INPUT input = new WinUser.INPUT();
        input.type = new WinDef.DWORD(WinUser.INPUT.INPUT_MOUSE);
        input.input.setType("mi");
        input.input.mi.dx = new WinDef.LONG(normalizedX);
        input.input.mi.dy = new WinDef.LONG(normalizedY);
        input.input.mi.mouseData = new WinDef.DWORD(0);
        input.input.mi.dwFlags = new WinDef.DWORD(
            User32Extended.MOUSEEVENTF_MOVE | 
            User32Extended.MOUSEEVENTF_ABSOLUTE
        );
        user32.SendInput(new WinDef.DWORD(1), (WinUser.INPUT[]) input.toArray(1), input.size());
    }

    @Override
    public String getKeyName(int keyCode) {
        WinDef.LONG lParam = createKeyLParam(keyCode);
        user32.GetKeyNameTextA(lParam, keyNameBuffer, KEY_BUFFER_SIZE);
        return new String(keyNameBuffer).trim();
    }

    @Override
    public void cleanup() {
        // No cleanup needed for Windows implementation
    }

    private WinDef.LONG createKeyLParam(int keyCode) {
        int scanCode = user32.MapVirtualKeyA(new WinDef.UINT(keyCode), new WinDef.UINT(0)).intValue();
        return new WinDef.LONG((scanCode << 16) | (1));
    }

    private int getMouseDownFlag(int button) {
        switch (button) {
            case 1: return User32Extended.MOUSEEVENTF_LEFTDOWN;
            case 2: return User32Extended.MOUSEEVENTF_RIGHTDOWN;
            case 3: return User32Extended.MOUSEEVENTF_MIDDLEDOWN;
            case 4: return User32Extended.MOUSEEVENTF_XDOWN;
            case 5: return User32Extended.MOUSEEVENTF_XDOWN;
            default: return 0;
        }
    }

    private int getMouseUpFlag(int button) {
        switch (button) {
            case 1: return User32Extended.MOUSEEVENTF_LEFTUP;
            case 2: return User32Extended.MOUSEEVENTF_RIGHTUP;
            case 3: return User32Extended.MOUSEEVENTF_MIDDLEUP;
            case 4: return User32Extended.MOUSEEVENTF_XUP;
            case 5: return User32Extended.MOUSEEVENTF_XUP;
            default: return 0;
        }
    }
}