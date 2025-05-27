package xyz.duncanruns.jingle.platform.macos;

import xyz.duncanruns.jingle.platform.PlatformInputManager;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MacOSInputManager implements PlatformInputManager {
    private final Robot robot;
    private final Map<Integer, Boolean> keyStates;
    private static final Map<Integer, String> KEY_NAMES;

    static {
        KEY_NAMES = new HashMap<>();
        // Initialize common key names
        KEY_NAMES.put(KeyEvent.VK_ESCAPE, "Escape");
        KEY_NAMES.put(KeyEvent.VK_F1, "F1");
        KEY_NAMES.put(KeyEvent.VK_F2, "F2");
        KEY_NAMES.put(KeyEvent.VK_F3, "F3");
        KEY_NAMES.put(KeyEvent.VK_F4, "F4");
        KEY_NAMES.put(KeyEvent.VK_F5, "F5");
        KEY_NAMES.put(KeyEvent.VK_F6, "F6");
        KEY_NAMES.put(KeyEvent.VK_F7, "F7");
        KEY_NAMES.put(KeyEvent.VK_F8, "F8");
        KEY_NAMES.put(KeyEvent.VK_F9, "F9");
        KEY_NAMES.put(KeyEvent.VK_F10, "F10");
        KEY_NAMES.put(KeyEvent.VK_F11, "F11");
        KEY_NAMES.put(KeyEvent.VK_F12, "F12");
        KEY_NAMES.put(KeyEvent.VK_CONTROL, "Control");
        KEY_NAMES.put(KeyEvent.VK_ALT, "Alt");
        KEY_NAMES.put(KeyEvent.VK_SHIFT, "Shift");
        KEY_NAMES.put(KeyEvent.VK_META, "Command");
        KEY_NAMES.put(KeyEvent.VK_SPACE, "Space");
        KEY_NAMES.put(KeyEvent.VK_ENTER, "Enter");
        KEY_NAMES.put(KeyEvent.VK_TAB, "Tab");
        KEY_NAMES.put(KeyEvent.VK_BACK_SPACE, "Backspace");
        KEY_NAMES.put(KeyEvent.VK_DELETE, "Delete");
        KEY_NAMES.put(KeyEvent.VK_HOME, "Home");
        KEY_NAMES.put(KeyEvent.VK_END, "End");
        KEY_NAMES.put(KeyEvent.VK_PAGE_UP, "Page Up");
        KEY_NAMES.put(KeyEvent.VK_PAGE_DOWN, "Page Down");
        KEY_NAMES.put(KeyEvent.VK_UP, "Up");
        KEY_NAMES.put(KeyEvent.VK_DOWN, "Down");
        KEY_NAMES.put(KeyEvent.VK_LEFT, "Left");
        KEY_NAMES.put(KeyEvent.VK_RIGHT, "Right");
    }

    public MacOSInputManager() {
        try {
            this.robot = new Robot();
            this.keyStates = new HashMap<>();
        } catch (AWTException e) {
            throw new RuntimeException("Failed to initialize Robot for input simulation", e);
        }
    }

    @Override
    public void keyDown(int keyCode) {
        robot.keyPress(keyCode);
        keyStates.put(keyCode, true);
    }

    @Override
    public void keyUp(int keyCode) {
        robot.keyRelease(keyCode);
        keyStates.put(keyCode, false);
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyStates.getOrDefault(keyCode, false);
    }

    @Override
    public List<Integer> getPressedKeys() {
        List<Integer> pressedKeys = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> entry : keyStates.entrySet()) {
            if (entry.getValue()) {
                pressedKeys.add(entry.getKey());
            }
        }
        return pressedKeys;
    }

    @Override
    public void mouseDown(int button) {
        robot.mousePress(getMouseButton(button));
    }

    @Override
    public void mouseUp(int button) {
        robot.mouseRelease(getMouseButton(button));
    }

    @Override
    public void moveMouse(int x, int y) {
        robot.mouseMove(x, y);
    }

    @Override
    public String getKeyName(int keyCode) {
        String name = KEY_NAMES.get(keyCode);
        if (name != null) {
            return name;
        }
        return KeyEvent.getKeyText(keyCode);
    }

    @Override
    public void cleanup() {
        keyStates.clear();
    }

    private int getMouseButton(int button) {
        switch (button) {
            case 1: return InputEvent.BUTTON1_DOWN_MASK;
            case 2: return InputEvent.BUTTON2_DOWN_MASK;
            case 3: return InputEvent.BUTTON3_DOWN_MASK;
            default: return InputEvent.BUTTON1_DOWN_MASK;
        }
    }
}