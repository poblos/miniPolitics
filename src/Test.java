import budget.Budget;
import budget.BudgetExpense;
import budget.BudgetIncome;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import events.Condition;
import events.Effect;
import events.Event;
import game.Game;
import game.RoundCondition;
import indicators.IndicatorChange;
import indicators.IndicatorCondition;
import jobs.*;
import media.MediaCondition;
import media.MediaGroup;
import media.MediaTakeover;
import modifiers.Modifier;
import modifiers.ModifierCondition;
import modifiers.ModifierInvocation;
import modifiers.ModifierRemoval;
import party.Ideology;
import party.IdeologyChange;
import party.IdeologyCondition;
import party.Party;
import policy.Policy;
import policy.PolicyChange;
import policy.PolicyCondition;

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
                        .withSubtype(BudgetExpense.class, "budget_expense")
                        .withSubtype(BudgetIncome.class, "budget_income")
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
                        .withSubtype(RoundCondition.class, "round_condition")
                )
                .build();

        //Adding default resources.events collection
        ArrayList<Event> events = loadFiles(Event.class,"src/resources/events", moshi);
        System.out.println("Number of loaded events: " + events.size());

        //Adding default resources.people collection
        ArrayList<Person> people = loadFiles(Person.class,"src/resources/people", moshi);

        //Adding default resources.modifiers collection
        ArrayList<Modifier> modifiers = loadFiles(Modifier.class,"src/resources/modifiers", moshi);

        //Adding default resources.media collection
        ArrayList<MediaGroup> medias = loadFiles(MediaGroup.class,"src/resources/media", moshi);

        //Loading Policy
        ArrayList<Policy> policies = loadFiles(Policy.class,"src/resources/policies", moshi);

        //Loading budget
        ArrayList<Budget> budgets = loadFiles(Budget.class, "src/resources/budget", moshi);

        //Party
        ArrayList<Ideology> ideologies = new ArrayList<>();
        ideologies.add(BigTent);
        ideologies.add(Capitalist);
        ideologies.add(Centrist);
        Party party = new Party("Democratic Party of Kachakonia",
                "One of the oldest active political parties in Kachakonia, traditionally associated with the bourgeoisie. " +
                        "Currently in power for past 4 years, but under new leadership everything might happen.",
                ideologies);

        Game game = new Game(events, people, policies, modifiers, medias, party, budgets.get(0));
        game.windowSimulate();
    }



}
