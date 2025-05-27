package xyz.duncanruns.jingle.util;

import xyz.duncanruns.jingle.platform.PlatformFactory;
import xyz.duncanruns.jingle.platform.PlatformInputManager;

public final class MouseUtil {
    private static final PlatformInputManager inputManager = PlatformFactory.getInputManager();

    private MouseUtil() {
    }

    public static void mouseDown(int button) {
        inputManager.mouseDown(button);
    }

    public static void mouseUp(int button) {
        inputManager.mouseUp(button);
    }

    public static void mouseClick(int button) {
        mouseDown(button);
        mouseUp(button);
    }

    public static void moveMouse(int x, int y) {
        inputManager.moveMouse(x, y);
    }

    public static void cleanup() {
        inputManager.cleanup();
    }
}
