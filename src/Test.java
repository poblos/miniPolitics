import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import effects.*;
import events_classes.Condition;
import events_classes.Event;
import events_classes.Modifier;
import events_classes.ModifierCondition;
import game.FileLoader;
import game.Game;
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
                        .withSubtype(ModifierInvocation.class, "modifier_invocation")
                )
                .add(PolymorphicJsonAdapterFactory.of(Condition.class,"type")
                        .withSubtype(ModifierCondition.class, "modifier_condition")
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

        //Adding default modifiers collection
        ArrayList<Modifier> modifiers= new ArrayList<>();
        JsonAdapter<Modifier> jsonAdapter3 = moshi.adapter(Modifier.class);
        pathList = FileLoader.listFiles(Path.of("src/modifiers"));
        for (Path path : pathList) {
            String json = Files.readString(path);
            Modifier modifier = jsonAdapter3.indent("  ").fromJson(json);
            modifiers.add(modifier);
        }

        Game game = new Game(events, people, modifiers);
        game.symuluj(20);
    }

}
