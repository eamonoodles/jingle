package xyz.duncanruns.jingle.win32;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.BaseTSD.ULONG_PTR;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;
import com.sun.jna.platform.win32.User32;

public interface User32Extended extends User32 {
    User32Extended INSTANCE = Native.load("user32", User32Extended.class, W32APIOptions.DEFAULT_OPTIONS);

    // Window management
    boolean GetWindowRect(HWND hWnd, RECT rect);
    boolean MoveWindow(HWND hWnd, int x, int y, int width, int height, boolean repaint);
    boolean SetWindowTextA(HWND hWnd, String text);
    int GetWindowTextA(HWND hWnd, char[] buffer, int maxCount);
    HWND GetForegroundWindow();
    boolean IsWindow(HWND hWnd);
    HWND FindWindowA(String className, String windowName);
    boolean IsWindowVisible(HWND hWnd);
    boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer userData);
    
    // System metrics
    int GetSystemMetrics(int index);
    
    // Input
    short GetAsyncKeyState(int vKey);
    UINT MapVirtualKeyA(UINT uCode, UINT uMapType);
    int GetKeyNameTextA(LONG lParam, char[] buffer, int size);
    DWORD SendInput(DWORD nInputs, WinUser.INPUT[] pInputs, int cbSize);

    // Constants for GetSystemMetrics
    int SM_CXSCREEN = 0;
    int SM_CYSCREEN = 1;

    // Mouse event flags
    int MOUSEEVENTF_MOVE = 0x0001;
    int MOUSEEVENTF_LEFTDOWN = 0x0002;
    int MOUSEEVENTF_LEFTUP = 0x0004;
    int MOUSEEVENTF_RIGHTDOWN = 0x0008;
    int MOUSEEVENTF_RIGHTUP = 0x0010;
    int MOUSEEVENTF_MIDDLEDOWN = 0x0020;
    int MOUSEEVENTF_MIDDLEUP = 0x0040;
    int MOUSEEVENTF_XDOWN = 0x0080;
    int MOUSEEVENTF_XUP = 0x0100;
    int MOUSEEVENTF_WHEEL = 0x0800;
    int MOUSEEVENTF_VIRTUALDESK = 0x4000;
    int MOUSEEVENTF_ABSOLUTE = 0x8000;
}