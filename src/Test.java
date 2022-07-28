import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import effects.*;
import events_classes.*;
import game.FileLoader;
import game.Game;
import jobs.Person;
import media_classes.MediaGroup;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        Moshi moshi = new Moshi.Builder()
                .add(PolymorphicJsonAdapterFactory.of(Effect.class, "type")
                        .withSubtype(IndicatorChange.class, "indicator_change")
                        .withSubtype(AdvisorEmployment.class, "advisor_employment")
                        .withSubtype(AdvisorDismissal.class, "advisor_dismissal")
                        .withSubtype(ModifierInvocation.class, "modifier_invocation")
                        .withSubtype(ModifierRemoval.class, "modifier_removal")
                        .withSubtype(MediaTakeover.class, "media_takeover")
                )
                .add(PolymorphicJsonAdapterFactory.of(Condition.class, "type")
                        .withSubtype(ModifierCondition.class, "modifier_condition")
                        .withSubtype(AdvisorCondition.class, "advisor_condition")
                        .withSubtype(MediaCondition.class, "media_condition")
                        .withSubtype(AdvisorSkillCondition.class, "trait_condition")
                        .withSubtype(IndicatorCondition.class, "indicator_condition")
                )
                .build();

        //Adding default events collection
        ArrayList<Event> events = new ArrayList<>();
        JsonAdapter<Event> jsonAdapter = moshi.adapter(Event.class);
        List<Path> pathList = FileLoader.listFiles(Path.of("src/events"));
        for (Path eventPath : pathList) {
            String json = Files.readString(eventPath);
            Event event = jsonAdapter.indent("  ").fromJson(json);
            events.add(event);
        }

        //Adding default people collection
        ArrayList<Person> people = new ArrayList<>();
        JsonAdapter<Person> jsonAdapter2 = moshi.adapter(Person.class);
        pathList = FileLoader.listFiles(Path.of("src/people"));
        for (Path path : pathList) {
            String json = Files.readString(path);
            Person person = jsonAdapter2.indent("  ").fromJson(json);
            people.add(person);
        }

        //Adding default modifiers collection
        ArrayList<events_classes.Modifier> modifiers = new ArrayList<>();
        JsonAdapter<events_classes.Modifier> jsonAdapter3 = moshi.adapter(events_classes.Modifier.class);
        pathList = FileLoader.listFiles(Path.of("src/modifiers"));
        for (Path path : pathList) {
            String json = Files.readString(path);
            events_classes.Modifier modifier = jsonAdapter3.indent("  ").fromJson(json);
            modifiers.add(modifier);
        }

        //Adding default media collection
        ArrayList<MediaGroup> medias = new ArrayList<>();
        JsonAdapter<MediaGroup> jsonAdapter4 = moshi.adapter(MediaGroup.class);
        pathList = FileLoader.listFiles(Path.of("src/media"));
        for (Path path : pathList) {
            String json = Files.readString(path);
            MediaGroup media = jsonAdapter4.indent("  ").fromJson(json);
            medias.add(media);
        }

        Game game = new Game(events, people, modifiers, medias);
        game.windowSimulate(20);
    }

}
