package game;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    public static List<Path> listFiles(Path directory) throws IOException {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*")) {
            for (Path entry : stream) {
                if ((new File(String.valueOf(entry))).isDirectory()) {
                    result.addAll(listFiles(entry));
                }
            }
        } catch (DirectoryIteratorException ex) {
            throw ex.getCause();
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.{json}")) {
            for (Path entry : stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            throw ex.getCause();
        }
        return result;
    }

    public static <class_> ArrayList<class_> loadFiles(Class<class_> class_, String dir, Moshi moshi) throws IOException {
        ArrayList<class_> list = new ArrayList<>();
        JsonAdapter<class_> jsonAdapter = moshi.adapter(class_);
        List<Path> pathList = FileLoader.listFiles(Path.of(dir));
        for (Path path : pathList) {
            String json = Files.readString(path);
            class_ object = jsonAdapter.indent("  ").fromJson(json);
            list.add(object);
        }
        return(list);
    }

}
