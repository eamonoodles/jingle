package xyz.duncanruns.jingle.platform.macos;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * JNA interface for macOS CoreFoundation functions.
 * This provides access to additional macOS APIs needed for window management.
 */
public interface CoreFoundationLibrary extends Library {
    CoreFoundationLibrary INSTANCE = Native.load("CoreFoundation", CoreFoundationLibrary.class);
    
    // CFArray functions
    long CFArrayGetCount(Pointer cfArray);
    Pointer CFArrayGetValueAtIndex(Pointer cfArray, long index);
    
    // CFDictionary functions
    Pointer CFDictionaryGetValue(Pointer cfDict, Pointer key);
    
    // CFString functions
    Pointer CFStringCreateWithCString(Pointer allocator, String cStr, int encoding);
    boolean CFStringGetCString(Pointer cfString, byte[] buffer, long bufferSize, int encoding);
    
    // CFNumber functions
    boolean CFNumberGetValue(Pointer cfNumber, int type, Pointer valuePtr);
    
    // Constants
    int kCFStringEncodingUTF8 = 0x08000100;
    int kCFNumberIntType = 9;
}
