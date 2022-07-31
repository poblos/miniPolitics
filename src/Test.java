import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import effects.*;
import events_classes.*;
import game.Game;
import jobs.Person;
import media_classes.MediaGroup;
import party.Ideology;
import party.Party;
import policy.Policy;

import java.io.IOException;
import java.util.ArrayList;

import static game.FileLoader.loadFiles;
import static party.Ideology.*;

public class Test {

    public static void main(String[] args) throws IOException {
        Moshi moshi = new Moshi.Builder()
                .add(PolymorphicJsonAdapterFactory.of(Effect.class, "type")
                        .withSubtype(IndicatorChange.class, "indicator_change")
                        .withSubtype(RandomAdvisorEmployment.class, "random_advisor_employment")
                        .withSubtype(AdvisorEmployment.class, "advisor_employment")
                        .withSubtype(RandomAdvisorDismissal.class, "random_advisor_dismissal")
                        .withSubtype(AdvisorDismissal.class, "advisor_dismissal")
                        .withSubtype(ModifierInvocation.class, "modifier_invocation")
                        .withSubtype(ModifierRemoval.class, "modifier_removal")
                        .withSubtype(MediaTakeover.class, "media_takeover")
                        .withSubtype(IdeologyChange.class, "ideology_change")
                        .withSubtype(PolicyChange.class, "policy_change")
                )
                .add(PolymorphicJsonAdapterFactory.of(Condition.class, "type")
                        .withSubtype(ModifierCondition.class, "modifier_condition")
                        .withSubtype(AdvisorCondition.class, "advisor_condition")
                        .withSubtype(MediaCondition.class, "media_condition")
                        .withSubtype(AdvisorSkillCondition.class, "trait_condition")
                        .withSubtype(IndicatorCondition.class, "indicator_condition")
                        .withSubtype(SomeAdvisorCondition.class, "some_advisor_condition")
                        .withSubtype(IdeologyCondition.class, "ideology_condition")
                        .withSubtype(PolicyCondition.class, "policy_condition")
                )
                .build();

        //Adding default events collection
        ArrayList<Event> events = loadFiles(Event.class,"src/events", moshi);
        System.out.println("Number of loaded events: " + events.size());

        //Adding default people collection
        ArrayList<Person> people = loadFiles(Person.class,"src/people", moshi);

        //Adding default modifiers collection
        ArrayList<Modifier> modifiers = loadFiles(Modifier.class,"src/modifiers", moshi);

        //Adding default media collection
        ArrayList<MediaGroup> medias = loadFiles(MediaGroup.class,"src/media", moshi);

        //Loading Policy
        ArrayList<Policy> policies = loadFiles(Policy.class,"src/policies", moshi);

        //Party
        ArrayList<Ideology> ideologies = new ArrayList<>();
        ideologies.add(BigTent);
        ideologies.add(Capitalist);
        ideologies.add(Centrist);

        Party party = new Party("Democratic Party of Kachakonia",
                "One of the oldest active political parties in Kachakonia, traditionally associated with the bourgeoisie. " +
                        "Currently in power for past 4 years, but under new leadership everything might happen.",
                ideologies);

        Game game = new Game(events, people, policies, modifiers, medias, party);
        game.windowSimulate(20);
    }



}
