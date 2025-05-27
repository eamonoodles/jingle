package xyz.duncanruns.jingle.instance.mods;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FabricModFolder {
    private final Path modsPath;
    private final List<FabricJarInfo> jarInfos = new ArrayList<>();
    private long lastCheck = 0;

    public FabricModFolder(Path instancePath) {
        this.modsPath = instancePath.resolve("mods");
    }

    public List<FabricJarInfo> getInfos() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCheck > 1000) {
            updateJarInfos();
            lastCheck = currentTime;
        }
        return new ArrayList<>(jarInfos);
    }

    private void updateJarInfos() {
        jarInfos.clear();
        if (!Files.isDirectory(modsPath)) {
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(modsPath, "*.jar")) {
            for (Path jarPath : stream) {
                try {
                    FabricJarInfo info = readJarInfo(jarPath);
                    if (info != null) {
                        jarInfos.add(info);
                    }
                } catch (IOException | ParseException e) {
                    // Skip this jar if we can't read it
                }
            }
        } catch (IOException e) {
            // Failed to read mods directory
        }
    }

    private FabricJarInfo readJarInfo(Path jarPath) throws IOException, ParseException {
        try (JarFile jarFile = new JarFile(jarPath.toFile())) {
            JarEntry fabricModJson = jarFile.getJarEntry("fabric.mod.json");
            if (fabricModJson == null) {
                return null;
            }

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(
                new InputStreamReader(jarFile.getInputStream(fabricModJson))
            );

            String id = (String) json.get("id");
            String name = json.containsKey("name") ? (String) json.get("name") : id;
            String version = json.containsKey("version") ? (String) json.get("version") : "0.0.0";
            String description = json.containsKey("description") ? (String) json.get("description") : "";

            List<String> authors = new ArrayList<>();
            if (json.containsKey("authors")) {
                Object authorsObj = json.get("authors");
                if (authorsObj instanceof JSONArray) {
                    JSONArray authorsArray = (JSONArray) authorsObj;
                    authorsArray.forEach(author -> authors.add(author.toString()));
                } else {
                    authors.add(authorsObj.toString());
                }
            }

            return new FabricJarInfo(jarPath, id, name, version, description, authors);
        }
    }

    public static class FabricJarInfo {
        public final Path path;
        public final String id;
        public final String name;
        public final String version;
        public final String description;
        public final List<String> authors;

        public FabricJarInfo(Path path, String id, String name, String version, String description, List<String> authors) {
            this.path = path;
            this.id = id;
            this.name = name;
            this.version = version;
            this.description = description;
            this.authors = Collections.unmodifiableList(authors);
        }
    }
}