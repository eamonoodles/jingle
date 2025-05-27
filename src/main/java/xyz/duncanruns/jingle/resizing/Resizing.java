package xyz.duncanruns.jingle.resizing;

import java.awt.Rectangle;
import xyz.duncanruns.jingle.Jingle;
import xyz.duncanruns.jingle.instance.OpenedInstanceInfo;
import xyz.duncanruns.jingle.platform.PlatformFactory;
import xyz.duncanruns.jingle.platform.PlatformWindow;
import xyz.duncanruns.jingle.platform.PlatformWindowManager;

/**
 * Cross-platform implementation of resizing functionality.
 * This replaces the Windows-specific implementation with a platform-agnostic approach.
 */
public final class Resizing {
    private static boolean currentlyResized = false;
    private static int previousWidth = 0;
    private static int previousHeight = 0;
    private static Rectangle previousBounds = null;
    
    private static final PlatformWindowManager windowManager = PlatformFactory.createWindowManager();
    
    private Resizing() {
    }
    
    /**
     * @return true if the width/height was applied to the instance, false if the width/height is undone
     */
    public static boolean toggleResize(int width, int height) {
        synchronized (Jingle.class) {
            return toggleResizeInternal(width, height);
        }
    }
    
    private static boolean toggleResizeInternal(int width, int height) {
        assert Jingle.getMainInstance().isPresent();
        OpenedInstanceInfo instance = Jingle.getMainInstance().get();
        
        // Get platform window from instance
        PlatformWindow window = windowManager.createWindowFromId(instance.hwnd);
        
        Rectangle previousRectangle = window.getBounds();
        int centerX = (int) previousRectangle.getCenterX();
        int centerY = (int) previousRectangle.getCenterY();
        
        boolean resizing = !currentlyResized || previousRectangle.width != width || previousRectangle.height != height;
        
        if (resizing) {
            // Store previous size and state if not already resized
            if (!currentlyResized) {
                previousWidth = previousRectangle.width;
                previousHeight = previousRectangle.height;
                previousBounds = previousRectangle;
            }
            
            // Calculate new bounds centered on the same point
            Rectangle newRectangle = new Rectangle(
                    centerX - width / 2,
                    centerY - height / 2,
                    width,
                    height
            );
            
            // Set window to borderless and apply new bounds
            window.setBorderless();
            window.setBounds(newRectangle);
            
            return (currentlyResized = true);
        } else {
            undoResize();
            return false;
        }
    }
    
    public static void undoResize() {
        assert Jingle.getMainInstance().isPresent();
        if (!currentlyResized) return;
        
        OpenedInstanceInfo instance = Jingle.getMainInstance().get();
        
        // Get platform window from instance
        PlatformWindow window = windowManager.createWindowFromId(instance.hwnd);
        
        Rectangle currentRectangle = window.getBounds();
        int centerX = (int) currentRectangle.getCenterX();
        int centerY = (int) currentRectangle.getCenterY();
        
        // Calculate new bounds centered on the same point
        Rectangle newRectangle = new Rectangle(
                centerX - previousWidth / 2,
                centerY - previousHeight / 2,
                previousWidth,
                previousHeight
        );
        
        // Restore border and apply original bounds
        window.restoreBorder();
        window.setBounds(newRectangle);
        
        currentlyResized = false;
    }
}
