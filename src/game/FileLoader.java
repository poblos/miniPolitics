package game;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import media_classes.MediaGroup;

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
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.{json}")) {
            for (Path entry : stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            throw ex.getCause();
        }
        return result;
    }

    public static <class_> ArrayList<class_> loadFiles(Class class_, String dir, Moshi moshi) throws IOException {
        ArrayList<class_> list = new ArrayList<>();
        JsonAdapter<MediaGroup> jsonAdapter4 = moshi.adapter(class_);
        List<Path> pathList = FileLoader.listFiles(Path.of(dir));
        for (Path path : pathList) {
            String json = Files.readString(path);
            class_ object = (class_) jsonAdapter4.indent("  ").fromJson(json);
            list.add(object);
        }
        return(list);
    }

}
