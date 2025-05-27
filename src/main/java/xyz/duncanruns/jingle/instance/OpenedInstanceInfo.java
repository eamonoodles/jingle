package xyz.duncanruns.jingle.instance;

import java.nio.file.Path;

/**
 * Platform-agnostic information about a Minecraft instance.
 */
public class OpenedInstanceInfo {
    public final Path instancePath;
    public final Object windowHandle;
    public final String versionString;

    public OpenedInstanceInfo(Path instancePath, Object windowHandle, String versionString) {
        this.instancePath = instancePath;
        this.windowHandle = windowHandle;
        this.versionString = versionString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenedInstanceInfo that = (OpenedInstanceInfo) o;
        return instancePath.equals(that.instancePath) &&
               windowHandle.equals(that.windowHandle) &&
               versionString.equals(that.versionString);
    }

    @Override
    public int hashCode() {
        int result = instancePath.hashCode();
        result = 31 * result + windowHandle.hashCode();
        result = 31 * result + versionString.hashCode();
        return result;
    }
}
