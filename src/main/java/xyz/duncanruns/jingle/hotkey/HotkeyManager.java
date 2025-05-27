package xyz.duncanruns.jingle.hotkey;

import com.google.gson.JsonObject;
import com.sun.jna.platform.win32.Win32VK;
import org.apache.commons.lang3.tuple.Pair;
import xyz.duncanruns.jingle.Jingle;
import xyz.duncanruns.jingle.platform.PlatformFactory;
import xyz.duncanruns.jingle.platform.PlatformFactory.Platform;
import xyz.duncanruns.jingle.plugin.PluginHotkeys;
import xyz.duncanruns.jingle.script.ScriptStuff;
import xyz.duncanruns.jingle.util.KeyboardUtil;

import java.awt.event.KeyEvent;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static xyz.duncanruns.jingle.util.SleepUtil.sleep;

public final class HotkeyManager {
    private static final boolean IS_WINDOWS = PlatformFactory.detectPlatform() == Platform.WINDOWS;

    public static final CopyOnWriteArrayList<Pair<Hotkey, Runnable>> HOTKEYS = new CopyOnWriteArrayList<>();

    private static final Set<Integer> F3_INCOMPATIBLES = new HashSet<>(Arrays.asList(
            IS_WINDOWS ? Win32VK.VK_F4.code : KeyEvent.VK_F4,
            65, 66, 67, 68, 70, 71, 72, 73, 76, 78, 80, 81, 83, 84, // A, B, C, D, F, G, H, I, L, N, P, Q, S, T
            49, 50, 51 // 1, 2, 3
    ));


    private HotkeyManager() {
    }

    public static void start() {
        new Thread(() -> {
            while (true) {
                run();
                if (!Jingle.isRunning()) break;
            }
        }, "hotkey-checker").start();
    }

    private static void run() {
        while (Jingle.isRunning()) {
            sleep(1);
            boolean f3IsPressed = IS_WINDOWS ?
                    KeyboardUtil.isPressed(Win32VK.VK_F3.code) :
                    KeyboardUtil.isPressed(KeyEvent.VK_F3);

            for (Pair<Hotkey, Runnable> hotkeyAction : HOTKEYS) {
                if (hotkeyAction.getLeft().wasPressed()) {
                    if (f3IsPressed && F3_INCOMPATIBLES.contains(hotkeyAction.getLeft().getMainKey())) continue;
                    try {
                        hotkeyAction.getRight().run();
                    } catch (Throwable t) {
                        Jingle.logError("Error while running hotkey!", t);
                    }
                }
            }
        }
    }

    public static void reload() {
        HOTKEYS.clear();

        List<SavedHotkey> savedHotkeys = Jingle.options.copySavedHotkeys();

        for (SavedHotkey savedHotkey : savedHotkeys) {
            Optional<Runnable> action = getAction(savedHotkey.type, savedHotkey.action);
            if (!action.isPresent()) continue;

            HOTKEYS.add(Pair.of(Hotkey.of(savedHotkey.keys, savedHotkey.ignoreModifiers), action.get()));
        }
    }

    private static Optional<Runnable> getAction(String type, String name) {
        switch (type.toLowerCase()) {
            case "builtin":
                return Jingle.getBuiltinHotkeyAction(name);
            case "plugin":
                return PluginHotkeys.getAction(name);
            case "script":
                return ScriptStuff.getAction(name);
            default:
                return Optional.empty();
        }
    }
}