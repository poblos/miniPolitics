import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class WritingExample {
    public static void main(String[] args) throws IOException {
        ArrayList<Option> options = new ArrayList<>();

        ArrayList<Effect> effects = new ArrayList<>();
        Effect minusCoh = new Effect(Indicator.PartyCohesion,-2);
        Effect  plusStab = new Effect(Indicator.StateStability, 2);
        effects.add(minusCoh);
        effects.add(plusStab);
        Option option1 = new Option("Fire him instantly!",  effects);
        options.add(option1);

        ArrayList<Effect> effects2 = new ArrayList<>();
        Effect plusCoh = new Effect(Indicator.PartyCohesion,2);
        Effect minusStab = new Effect(Indicator.StateStability, -1);
        Effect minusSup = new Effect(Indicator.PartySupport, -1);
        effects2.add(plusCoh);
        effects2.add(minusStab);
        effects2.add(minusSup);
        Option option2 = new Option("And what?",  effects2);
        options.add(option2);

        Event event1 = new Event("Corrupted official","The major newspaper revealed in today's article that one of our MP's had taken multiple bribes. What shall we do?",options);
        ArrayList<Event> events= new ArrayList<>();
        events.add(event1);
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Event> jsonAdapter = moshi.adapter(Event.class);
        String json = jsonAdapter.indent("  ").toJson(event1);
        FileWriter writer = new FileWriter("src/events/thoughtless_words.json");
        writer.append(json);
        writer.close();
        Path filePath = Path.of("src/events/corrupted_official.json");
        String json2 = Files.readString(filePath);
        Event event2 = jsonAdapter.indent("  ").fromJson(json2);
        events.add(event2);
        Gra gra = new Gra(events);
        gra.symuluj(20);
    }
}