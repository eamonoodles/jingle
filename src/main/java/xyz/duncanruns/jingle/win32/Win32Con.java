package xyz.duncanruns.jingle.win32;

/**
 * Windows API constants used throughout the application.
 * Consolidates constants from various Windows header files.
 */
public interface Win32Con {
    // Virtual Key codes (winuser.h)
    int VK_LBUTTON = 0x01;
    int VK_RBUTTON = 0x02;
    int VK_CANCEL = 0x03;
    int VK_MBUTTON = 0x04;
    int VK_XBUTTON1 = 0x05;
    int VK_XBUTTON2 = 0x06;
    int VK_BACK = 0x08;
    int VK_TAB = 0x09;
    int VK_CLEAR = 0x0C;
    int VK_RETURN = 0x0D;
    int VK_SHIFT = 0x10;
    int VK_CONTROL = 0x11;
    int VK_MENU = 0x12;
    int VK_PAUSE = 0x13;
    int VK_CAPITAL = 0x14;
    int VK_ESCAPE = 0x1B;
    int VK_SPACE = 0x20;
    int VK_PRIOR = 0x21;
    int VK_NEXT = 0x22;
    int VK_END = 0x23;
    int VK_HOME = 0x24;
    int VK_LEFT = 0x25;
    int VK_UP = 0x26;
    int VK_RIGHT = 0x27;
    int VK_DOWN = 0x28;
    int VK_SELECT = 0x29;
    int VK_PRINT = 0x2A;
    int VK_EXECUTE = 0x2B;
    int VK_SNAPSHOT = 0x2C;
    int VK_INSERT = 0x2D;
    int VK_DELETE = 0x2E;
    int VK_HELP = 0x2F;
    // VK_0 - VK_9 are the same as ASCII '0' - '9' (0x30 - 0x39)
    // VK_A - VK_Z are the same as ASCII 'A' - 'Z' (0x41 - 0x5A)
    int VK_LWIN = 0x5B;
    int VK_RWIN = 0x5C;
    int VK_APPS = 0x5D;
    int VK_NUMPAD0 = 0x60;
    int VK_NUMPAD1 = 0x61;
    int VK_NUMPAD2 = 0x62;
    int VK_NUMPAD3 = 0x63;
    int VK_NUMPAD4 = 0x64;
    int VK_NUMPAD5 = 0x65;
    int VK_NUMPAD6 = 0x66;
    int VK_NUMPAD7 = 0x67;
    int VK_NUMPAD8 = 0x68;
    int VK_NUMPAD9 = 0x69;
    int VK_MULTIPLY = 0x6A;
    int VK_ADD = 0x6B;
    int VK_SEPARATOR = 0x6C;
    int VK_SUBTRACT = 0x6D;
    int VK_DECIMAL = 0x6E;
    int VK_DIVIDE = 0x6F;
    int VK_F1 = 0x70;
    int VK_F2 = 0x71;
    int VK_F3 = 0x72;
    int VK_F4 = 0x73;
    int VK_F5 = 0x74;
    int VK_F6 = 0x75;
    int VK_F7 = 0x76;
    int VK_F8 = 0x77;
    int VK_F9 = 0x78;
    int VK_F10 = 0x79;
    int VK_F11 = 0x7A;
    int VK_F12 = 0x7B;
    int VK_NUMLOCK = 0x90;
    int VK_SCROLL = 0x91;
    int VK_LSHIFT = 0xA0;
    int VK_RSHIFT = 0xA1;
    int VK_LCONTROL = 0xA2;
    int VK_RCONTROL = 0xA3;
    int VK_LMENU = 0xA4;
    int VK_RMENU = 0xA5;

    // Window Styles (winuser.h)
    int GWL_STYLE = -16;
    int WS_OVERLAPPED = 0x00000000;
    int WS_POPUP = 0x80000000;
    int WS_CHILD = 0x40000000;
    int WS_MINIMIZE = 0x20000000;
    int WS_VISIBLE = 0x10000000;
    int WS_DISABLED = 0x08000000;
    int WS_CLIPSIBLINGS = 0x04000000;
    int WS_CLIPCHILDREN = 0x02000000;
    int WS_MAXIMIZE = 0x01000000;
    int WS_CAPTION = 0x00C00000;
    int WS_BORDER = 0x00800000;
    int WS_DLGFRAME = 0x00400000;
    int WS_VSCROLL = 0x00200000;
    int WS_HSCROLL = 0x00100000;
    int WS_SYSMENU = 0x00080000;
    int WS_THICKFRAME = 0x00040000;
    int WS_GROUP = 0x00020000;
    int WS_TABSTOP = 0x00010000;
    int WS_MINIMIZEBOX = 0x00020000;
    int WS_MAXIMIZEBOX = 0x00010000;

    // Window Style Combinations
    int WS_OVERLAPPEDWINDOW = (WS_OVERLAPPED | WS_CAPTION | WS_SYSMENU |
            WS_THICKFRAME | WS_MINIMIZEBOX | WS_MAXIMIZEBOX);
    int WS_POPUPWINDOW = (WS_POPUP | WS_BORDER | WS_SYSMENU);
    int WS_CHILDWINDOW = WS_CHILD;

    // ShowWindow Commands
    int SW_HIDE = 0;
    int SW_SHOWNORMAL = 1;
    int SW_SHOWMINIMIZED = 2;
    int SW_SHOWMAXIMIZED = 3;
    int SW_SHOWNOACTIVATE = 4;
    int SW_SHOW = 5;
    int SW_MINIMIZE = 6;
    int SW_SHOWMINNOACTIVE = 7;
    int SW_SHOWNA = 8;
    int SW_RESTORE = 9;
    int SW_SHOWDEFAULT = 10;

    // Window Messages
    int WM_SYSCOMMAND = 0x0112;
    int SC_CLOSE = 0xF060;

    // Mouse Event Flags - Defined in User32Extended to avoid duplication
}
