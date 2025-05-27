package xyz.duncanruns.jingle.instance.options;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public final class OptionsFileUtil {
    private OptionsFileUtil() {}

    public static class StandardSettings {
        private final Path optionsPath;
        private final Properties properties = new Properties();
        private long lastModified = 0;

        public StandardSettings(Path instancePath) {
            this.optionsPath = instancePath.resolve("options.txt");
        }

        public void tryUpdate() {
            try {
                if (!Files.exists(optionsPath)) {
                    return;
                }

                long currentModified = Files.getLastModifiedTime(optionsPath).toMillis();
                if (currentModified == lastModified) {
                    return;
                }

                lastModified = currentModified;
                properties.clear();
                properties.load(Files.newBufferedReader(optionsPath));
            } catch (IOException e) {
                // Ignore read errors
            }
        }

        public String getValue(String key) {
            return properties.getProperty(key);
        }

        public void setValue(String key, String value) {
            try {
                properties.setProperty(key, value);
                properties.store(Files.newBufferedWriter(optionsPath), null);
                lastModified = Files.getLastModifiedTime(optionsPath).toMillis();
            } catch (IOException e) {
                // Ignore write errors
            }
        }
    }

    public static class OptionsText {
        private final Path optionsPath;
        private List<String> lines = new ArrayList<>();
        private long lastModified = 0;
        private boolean modified = false;

        public OptionsText(Path instancePath) {
            this.optionsPath = instancePath.resolve("options.txt");
        }

        public void tryUpdate() {
            if (modified) {
                return;
            }

            try {
                if (!Files.exists(optionsPath)) {
                    return;
                }

                long currentModified = Files.getLastModifiedTime(optionsPath).toMillis();
                if (currentModified == lastModified) {
                    return;
                }

                lastModified = currentModified;
                lines = Files.readAllLines(optionsPath);
            } catch (IOException e) {
                // Ignore read errors
            }
        }

        public List<String> getLines() {
            return new ArrayList<>(lines);
        }

        public void setLines(List<String> lines) {
            this.lines = new ArrayList<>(lines);
            this.modified = true;
            try {
                Files.write(optionsPath, lines);
                lastModified = Files.getLastModifiedTime(optionsPath).toMillis();
                modified = false;
            } catch (IOException e) {
                // Ignore write errors
            }
        }
    }
}