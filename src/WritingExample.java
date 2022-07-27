import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import jobs.Person;
import jobs.Trait;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class WritingExample {
    public static void main(String[] args) throws IOException {
        Trait trait = Trait.PropagandaMaster;
        ArrayList<Trait> traits = new ArrayList<>();
        traits.add(trait);
        Moshi moshi = new Moshi.Builder().build();
        Person person = new Person("James Mouton",traits);
        JsonAdapter<Person> jsonAdapter = moshi.adapter(Person.class);
        String json = jsonAdapter.indent("  ").toJson(person);
        FileWriter writer = new FileWriter("src/people/good_propagandist2.json");
        writer.append(json);
        writer.close();
        Path filePath = Path.of("src/people/good_propagandist2.json");
        String json2 = Files.readString(filePath);
        Person person2 = jsonAdapter.indent("  ").fromJson(json2);
    }
}