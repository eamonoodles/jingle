package xyz.duncanruns.jingle.util;

import xyz.duncanruns.jingle.instance.OpenedInstance;
import xyz.duncanruns.jingle.platform.PlatformFactory;
import xyz.duncanruns.jingle.platform.PlatformInputManager;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Utility class for sending keystrokes to a Minecraft instance.
 */
public class KeyPresser {
    private final OpenedInstance instance;
    private final PlatformInputManager inputManager;

    public KeyPresser(OpenedInstance instance) {
        this.instance = instance;
        this.inputManager = PlatformFactory.getInputManager();
    }

    public void pressKey(int keyCode) {
        ensureFocused();
        inputManager.keyDown(keyCode);
        SleepUtil.sleep(50);
        inputManager.keyUp(keyCode);
        SleepUtil.sleep(50);
    }

    public void pressEsc() {
        pressKey(KeyEvent.VK_ESCAPE);
    }

    public void pressEnter() {
        pressKey(KeyEvent.VK_ENTER);
    }

    public void pressTab(int times) {
        for (int i = 0; i < times; i++) {
            pressKey(KeyEvent.VK_TAB);
        }
    }

    public void pressShiftTab(int times) {
        holdKey(KeyEvent.VK_SHIFT);
        pressTab(times);
        releaseKey(KeyEvent.VK_SHIFT);
    }

    public void holdKey(int keyCode) {
        ensureFocused();
        inputManager.keyDown(keyCode);
        SleepUtil.sleep(50);
    }

    public void releaseKey(int keyCode) {
        ensureFocused();
        inputManager.keyUp(keyCode);
        SleepUtil.sleep(50);
    }

    public void releaseAllModifiers() {
        List<Integer> pressedKeys = inputManager.getPressedKeys();
        for (int keyCode : pressedKeys) {
            if (isModifierKey(keyCode)) {
                releaseKey(keyCode);
            }
        }
    }

    private boolean isModifierKey(int keyCode) {
        return keyCode == KeyEvent.VK_SHIFT ||
               keyCode == KeyEvent.VK_CONTROL ||
               keyCode == KeyEvent.VK_ALT ||
               keyCode == KeyEvent.VK_META;
    }

    private void ensureFocused() {
        if (!instance.isForeground()) {
            SleepUtil.sleep(50);
            if (!instance.isForeground()) {
                throw new IllegalStateException("Cannot send keystrokes to unfocused window");
            }
        }
    }
}