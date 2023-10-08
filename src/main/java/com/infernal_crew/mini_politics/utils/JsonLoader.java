package com.infernal_crew.mini_politics.utils;

import com.infernal_crew.mini_politics.Main;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonLoader {
    public static <class_> ArrayList<class_> loadFiles(Class<class_> class_, String path, Moshi moshi) throws URISyntaxException, IOException {
        ArrayList<class_> list = new ArrayList<>();
        JsonAdapter<class_> jsonAdapter = moshi.adapter(class_);

        URL dir = Main.class.getResource(path);
        List<File> collect = Files.walk(Paths.get(dir.toURI()))
                .filter(Files::isRegularFile)
                .map(Path::toFile).toList();

        for (File f : collect) {
            list.add(jsonAdapter.indent("  ").fromJson(Files.readString(f.toPath())));
        }
        return list;
    }
}
