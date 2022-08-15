package com.example.demo;

import com.example.demo.budget.Budget;
import com.example.demo.budget.BudgetExpense;
import com.example.demo.budget.BudgetIncome;
import com.example.demo.policy.*;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import com.example.demo.event.Condition;
import com.example.demo.event.Effect;
import com.example.demo.event.Event;
import com.example.demo.game.Game;
import com.example.demo.game.RoundCondition;
import com.example.demo.indicators.IndicatorChange;
import com.example.demo.indicators.IndicatorCondition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import com.example.demo.jobs.*;
import com.example.demo.media.MediaCondition;
import com.example.demo.media.MediaGroup;
import com.example.demo.media.MediaTakeover;
import com.example.demo.modifiers.Modifier;
import com.example.demo.modifiers.ModifierCondition;
import com.example.demo.modifiers.ModifierInvocation;
import com.example.demo.modifiers.ModifierRemoval;
import com.example.demo.party.Ideology;
import com.example.demo.party.IdeologyChange;
import com.example.demo.party.IdeologyCondition;
import com.example.demo.party.Party;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static com.example.demo.JsonLoader.loadFiles;
import static com.example.demo.party.Ideology.*;

public class HelloController {
    @FXML
    private HBox startBox;
    @FXML
    private HBox jobBox;
    @FXML
    private HBox indicatorBox;
    @FXML
    private VBox eventBox;
    Game game;

    @FXML
    protected void onStartButtonClick() throws URISyntaxException, IOException {
        startBox.getChildren().clear();
        Moshi moshi = new Moshi.Builder().add(PolymorphicJsonAdapterFactory.of(Effect.class, "type").withSubtype(IndicatorChange.class, "indicator_change").withSubtype(RandomAdvisorEmployment.class, "random_advisor_employment").withSubtype(AdvisorEmployment.class, "advisor_employment").withSubtype(RandomAdvisorDismissal.class, "random_advisor_dismissal").withSubtype(AdvisorDismissal.class, "advisor_dismissal").withSubtype(ModifierInvocation.class, "modifier_invocation").withSubtype(ModifierRemoval.class, "modifier_removal").withSubtype(MediaTakeover.class, "media_takeover").withSubtype(IdeologyChange.class, "ideology_change").withSubtype(PolicyChange.class, "policy_change").withSubtype(BudgetExpense.class, "budget_expense").withSubtype(BudgetIncome.class, "budget_income")).add(PolymorphicJsonAdapterFactory.of(Condition.class, "type").withSubtype(ModifierCondition.class, "modifier_condition").withSubtype(AdvisorCondition.class, "advisor_condition").withSubtype(MediaCondition.class, "media_condition").withSubtype(AdvisorSkillCondition.class, "trait_condition").withSubtype(IndicatorCondition.class, "indicator_condition").withSubtype(SomeAdvisorCondition.class, "some_advisor_condition").withSubtype(IdeologyCondition.class, "ideology_condition").withSubtype(PolicyCondition.class, "policy_condition").withSubtype(RoundCondition.class, "round_condition")).build();

        //Adding default events collection
        ArrayList<Event> events = loadFiles(Event.class, "json/events/", moshi);

        //Adding default com.example.demo.resources.people collection
        ArrayList<Person> people = loadFiles(Person.class, "json/people/", moshi);

        //Adding default com.example.demo.resources.com.example.demo.modifiers collection
        ArrayList<Modifier> modifiers = loadFiles(Modifier.class, "json/modifiers/", moshi);

        //Adding default com.example.demo.resources.com.example.demo.media collection
        ArrayList<MediaGroup> medias = loadFiles(MediaGroup.class, "json/media/", moshi);

        //Loading Policy
        ArrayList<Policy> policies = loadFiles(Policy.class, "json/policies/", moshi);

        //Loading com.example.demo.budget
        ArrayList<Budget> budgets = loadFiles(Budget.class, "json/budget/", moshi);

        //Party
        ArrayList<Ideology> ideologies = new ArrayList<>();
        ideologies.add(BigTent);
        ideologies.add(Capitalist);
        ideologies.add(Centrist);
        Party party = new Party("Democratic Party of Kachakonia", "One of the oldest active political parties in Kachakonia, traditionally associated with the bourgeoisie. " + "Currently in power for past 4 years, but under new leadership everything might happen.", ideologies);

        game = new Game(events, people, policies, modifiers, medias, party, budgets.get(0));
        indicatorBox.getChildren().add(new IndicatorDisplay("PartyCohesion"));
        indicatorBox.getChildren().add(new IndicatorDisplay("StateStability"));
        indicatorBox.getChildren().add(new IndicatorDisplay("PartySupport"));

        jobBox.getChildren().add(new JobDisplay(Job.Whip));
        jobBox.getChildren().add(new JobDisplay(Job.Propagandist));
        jobBox.getChildren().add(new JobDisplay(Job.Strategist));

        for (Node display : indicatorBox.getChildren()) {
            ((IndicatorDisplay) display).update(game);
        }
        game.chooseEvent();
        eventBox.getChildren().add(new EventDisplay(game.getCurrentEvent(), this));
    }

    public void handleEvent(int click) {
        game.handleEvent(click);
        for (Node display : indicatorBox.getChildren()) {
            ((IndicatorDisplay) display).update(game);
        }
        for (Node display : jobBox.getChildren()) {
            ((JobDisplay) display).update(game);
        }
        if (game.displayNext()) {
            eventBox.getChildren().clear();
            game.chooseEvent();
            eventBox.getChildren().add(new EventDisplay(game.getCurrentEvent(), this));
        } else {
            eventBox.getChildren().clear();
            eventBox.getChildren().add(new JobChoiceDisplay(game.getCurrentPerson(), this));
        }
    }

    public void handleJobChoice(Job job) {
        game.chooseJob(job);
        for (Node display : jobBox.getChildren()) {
            if (job == ((JobDisplay) display).getJob()) {
                ((JobDisplay) display).add(game);
            }
        }
        eventBox.getChildren().clear();
        game.chooseEvent();
        eventBox.getChildren().add(new EventDisplay(game.getCurrentEvent(), this));

    }
}