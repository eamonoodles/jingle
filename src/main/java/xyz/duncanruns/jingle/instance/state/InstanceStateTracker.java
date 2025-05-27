package xyz.duncanruns.jingle.instance.state;

import xyz.duncanruns.jingle.instance.OpenedInstance;
import java.util.function.BiConsumer;

public class InstanceStateTracker {
    private final OpenedInstance instance;
    private final BiConsumer<InstanceState, InstanceState> onStateChange;
    private InstanceState currentState = InstanceState.UNKNOWN;

    public InstanceStateTracker(OpenedInstance instance, BiConsumer<InstanceState, InstanceState> onStateChange) {
        this.instance = instance;
        this.onStateChange = onStateChange;
    }

    public void tryUpdate() {
        InstanceState newState = determineState();
        if (newState != currentState) {
            InstanceState oldState = currentState;
            currentState = newState;
            onStateChange.accept(oldState, newState);
        }
    }

    public boolean isCurrentState(InstanceState state) {
        return currentState == state;
    }

    private InstanceState determineState() {
        String title = instance.getWindowTitle();
        
        if (title == null || !title.startsWith("Minecraft*")) {
            return InstanceState.UNLOADED;
        }

        // Title screen shows just "Minecraft*"
        if (title.equals("Minecraft*")) {
            return InstanceState.TITLE;
        }

        // Loading shows "Loading..." or "Logging in..."
        if (title.contains("Loading") || title.contains("Logging in")) {
            return InstanceState.LOADING;
        }

        // In-world shows "Minecraft* - Some World Name"
        if (title.startsWith("Minecraft* - ")) {
            return InstanceState.INWORLD;
        }

        return InstanceState.UNKNOWN;
    }
}