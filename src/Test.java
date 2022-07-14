import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import effects.AdvisorDismissal;
import effects.AdvisorEmployment;
import effects.Effect;
import effects.IndicatorChange;
import jobs.Person;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        Moshi moshi = new Moshi.Builder()
                .add(PolymorphicJsonAdapterFactory.of(Effect.class,"type")
                        .withSubtype(IndicatorChange.class, "indicator_change")
                        .withSubtype(AdvisorEmployment.class, "advisor_employment")
                        .withSubtype(AdvisorDismissal.class, "advisor_dismissal")
                )
                .build();

        //Adding default events collection
        ArrayList<Event> events= new ArrayList<>();
        JsonAdapter<Event> jsonAdapter = moshi.adapter(Event.class);
        List<Path> pathList = FileLoader.listFiles(Path.of("src/events"));
        for (Path eventPath : pathList) {
            String json = Files.readString(eventPath);
            Event event = jsonAdapter.indent("  ").fromJson(json);
            events.add(event);
        }

        //Adding default people collection
        ArrayList<Person> people= new ArrayList<>();
        JsonAdapter<Person> jsonAdapter2 = moshi.adapter(Person.class);
        pathList = FileLoader.listFiles(Path.of("src/people"));
        for (Path path : pathList) {
            String json = Files.readString(path);
            Person person = jsonAdapter2.indent("  ").fromJson(json);
            people.add(person);
        }
        Gra gra = new Gra(events, people);
        gra.symuluj(20);
    }

}
