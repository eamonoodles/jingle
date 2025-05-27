package xyz.duncanruns.jingle.util;

import xyz.duncanruns.jingle.platform.PlatformFactory;
import xyz.duncanruns.jingle.platform.PlatformInputManager;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class KeyboardUtil {
    private static final PlatformInputManager inputManager = PlatformFactory.getInputManager();
    
    // Virtual key codes for modifier keys
    private static final int VK_CONTROL = 0x11;
    private static final int VK_SHIFT = 0x10;
    private static final int VK_ALT = 0x12;
    private static final int VK_LCONTROL = 0xA2;
    private static final int VK_RCONTROL = 0xA3;
    private static final int VK_LSHIFT = 0xA0;
    private static final int VK_RSHIFT = 0xA1;
    private static final int VK_LALT = 0xA4;
    private static final int VK_RALT = 0xA5;
    
    public static final Set<Integer> SINGLE_MODIFIERS = new HashSet<>(Arrays.asList(
        VK_CONTROL, VK_SHIFT, VK_ALT));
        
    public static final Set<Integer> ALL_MODIFIERS = new HashSet<>(Arrays.asList(
        VK_CONTROL, VK_LCONTROL, VK_RCONTROL,
        VK_SHIFT, VK_LSHIFT, VK_RSHIFT,
        VK_ALT, VK_LALT, VK_RALT));

    private KeyboardUtil() {
    }

    public static void keyDown(int vk) {
        inputManager.keyDown(vk);
    }

    public static void keyUp(int vk) {
        inputManager.keyUp(vk);
    }

    public static void pressKey(int vk) {
        keyDown(vk);
        keyUp(vk);
    }

    public static boolean isPressed(int vk) {
        return inputManager.isKeyPressed(vk);
    }

    public static List<Integer> getPressedKeys() {
        return inputManager.getPressedKeys();
    }

    public static List<Integer> getPressedKeys(List<Integer> excludeKeys) {
        List<Integer> pressedKeys = getPressedKeys();
        pressedKeys.removeAll(excludeKeys);
        return pressedKeys;
    }

    public static String getKeyName(int vk) {
        return inputManager.getKeyName(vk);
    }

    public static void copyToClipboard(String string) {
        Toolkit.getDefaultToolkit()
            .getSystemClipboard()
            .setContents(new StringSelection(string), null);
    }

    public static void cleanup() {
        inputManager.cleanup();
    }
}