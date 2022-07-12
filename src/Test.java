import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        ArrayList<Event> events= new ArrayList<>();
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Event> jsonAdapter = moshi.adapter(Event.class);

        List<Path> eventPathList = FileLoader.listEventFiles(Path.of("src/events"));
        for (Path eventPath : eventPathList) {
            String json = Files.readString(eventPath);
            Event event = jsonAdapter.indent("  ").fromJson(json);
            events.add(event);
        }
        Gra gra = new Gra(events);
        gra.symuluj(20);
    }

}
