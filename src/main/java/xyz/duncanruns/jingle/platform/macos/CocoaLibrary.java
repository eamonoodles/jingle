package xyz.duncanruns.jingle.platform.macos;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

/**
 * JNA interface for macOS Cocoa/AppKit functions.
 * This provides access to the necessary macOS window management APIs.
 */
public interface CocoaLibrary extends Library {
    CocoaLibrary INSTANCE = Native.load("Cocoa", CocoaLibrary.class);
    
    // NSApplication methods
    Pointer NSApp_sharedApplication();
    
    // NSWindow methods
    Pointer NSWindow_alloc();
    Pointer NSWindow_initWithContentRect_styleMask_backing_defer(
            Pointer nsWindow, 
            NSRect contentRect, 
            long styleMask, 
            int backing, 
            boolean defer);
    void NSWindow_setTitle(Pointer nsWindow, Pointer nsString);
    Pointer NSWindow_title(Pointer nsWindow);
    void NSWindow_setFrame_display_animate(Pointer nsWindow, NSRect frame, boolean display, boolean animate);
    NSRect NSWindow_frame(Pointer nsWindow);
    void NSWindow_setStyleMask(Pointer nsWindow, long styleMask);
    long NSWindow_styleMask(Pointer nsWindow);
    void NSWindow_orderFront(Pointer nsWindow, Pointer sender);
    void NSWindow_miniaturize(Pointer nsWindow, Pointer sender);
    void NSWindow_deminiaturize(Pointer nsWindow, Pointer sender);
    boolean NSWindow_isMiniaturized(Pointer nsWindow);
    boolean NSWindow_isZoomed(Pointer nsWindow);
    void NSWindow_zoom(Pointer nsWindow, Pointer sender);
    
    // NSString methods
    Pointer NSString_stringWithUTF8String(String utf8String);
    void NSString_getCString_maxLength_encoding(Pointer nsString, byte[] buffer, long maxLength, int encoding);
    
    // NSWorkspace methods
    Pointer NSWorkspace_sharedWorkspace();
    Pointer NSWorkspace_runningApplications(Pointer nsWorkspace);
    
    // NSArray methods
    long NSArray_count(Pointer nsArray);
    Pointer NSArray_objectAtIndex(Pointer nsArray, long index);
    
    // NSRunningApplication methods
    Pointer NSRunningApplication_localizedName(Pointer nsRunningApplication);
    int NSRunningApplication_processIdentifier(Pointer nsRunningApplication);
    
    // CGWindow methods
    Pointer CGWindowListCopyWindowInfo(int option, int relativeToWindow);
    
    // Constants
    int NSUTF8StringEncoding = 4;
    
    // Window style masks
    long NSWindowStyleMaskBorderless = 0;
    long NSWindowStyleMaskTitled = 1 << 0;
    long NSWindowStyleMaskClosable = 1 << 1;
    long NSWindowStyleMaskMiniaturizable = 1 << 2;
    long NSWindowStyleMaskResizable = 1 << 3;
    
    // CGWindowList options
    int kCGWindowListOptionAll = 0;
    int kCGWindowListOptionOnScreenOnly = 1 << 0;
    int kCGWindowListOptionOnScreenAboveWindow = 1 << 1;
    int kCGWindowListOptionOnScreenBelowWindow = 1 << 2;
    int kCGWindowListOptionIncludingWindow = 1 << 3;
    int kCGWindowListExcludeDesktopElements = 1 << 4;
    
    /**
     * Structure representing an NSRect in Cocoa.
     */
    @Structure.FieldOrder({"origin", "size"})
    class NSRect extends Structure {
        public NSPoint origin;
        public NSSize size;
        
        public NSRect() {
            super();
        }
        
        public NSRect(NSPoint origin, NSSize size) {
            super();
            this.origin = origin;
            this.size = size;
        }
    }
    
    /**
     * Structure representing an NSPoint in Cocoa.
     */
    @Structure.FieldOrder({"x", "y"})
    class NSPoint extends Structure {
        public double x;
        public double y;
        
        public NSPoint() {
            super();
        }
        
        public NSPoint(double x, double y) {
            super();
            this.x = x;
            this.y = y;
        }
    }
    
    /**
     * Structure representing an NSSize in Cocoa.
     */
    @Structure.FieldOrder({"width", "height"})
    class NSSize extends Structure {
        public double width;
        public double height;
        
        public NSSize() {
            super();
        }
        
        public NSSize(double width, double height) {
            super();
            this.width = width;
            this.height = height;
        }
    }
}
