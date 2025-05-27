package xyz.duncanruns.jingle.platform;

import java.util.List;

/**
 * Interface for platform-specific input management.
 */
public interface PlatformInputManager {
    /**
     * Simulates a key press
     */
    void keyDown(int keyCode);
    
    /**
     * Simulates a key release
     */
    void keyUp(int keyCode);
    
    /**
     * Checks if a key is currently pressed
     */
    boolean isKeyPressed(int keyCode);
    
    /**
     * Gets a list of all currently pressed keys
     */
    List<Integer> getPressedKeys();
    
    /**
     * Simulates a mouse button press
     */
    void mouseDown(int button);
    
    /**
     * Simulates a mouse button release
     */
    void mouseUp(int button);
    
    /**
     * Moves the mouse cursor to specified coordinates
     */
    void moveMouse(int x, int y);
    
    /**
     * Gets the name of a key from its code
     */
    String getKeyName(int keyCode);
    
    /**
     * Performs cleanup or releases any resources
     */
    void cleanup();
}