package com.example.demo;

import com.example.demo.budget.Budget;
import com.example.demo.budget.BudgetExpense;
import com.example.demo.budget.BudgetIncome;
import com.example.demo.event.Condition;
import com.example.demo.event.Effect;
import com.example.demo.event.Event;
import com.example.demo.game.Game;
import com.example.demo.game.RoundCondition;
import com.example.demo.indicators.IndicatorChange;
import com.example.demo.indicators.IndicatorCondition;
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
import com.example.demo.policy.Policy;
import com.example.demo.policy.PolicyChange;
import com.example.demo.policy.PolicyCondition;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.demo.party.Ideology.*;
import static com.example.demo.utils.JsonLoader.loadFiles;

public class StartView {
    private Game game;

    @FXML
    private Button kaButton, grButton;

    @FXML
    private MainController mainController;

    public void onKachakonyaButtonClick() throws URISyntaxException, IOException {
        Moshi moshi = new Moshi.Builder().add(PolymorphicJsonAdapterFactory.of(Effect.class, "type").withSubtype(IndicatorChange.class, "indicator_change").withSubtype(RandomAdvisorEmployment.class, "random_advisor_employment").withSubtype(AdvisorEmployment.class, "advisor_employment").withSubtype(RandomAdvisorDismissal.class, "random_advisor_dismissal").withSubtype(AdvisorDismissal.class, "advisor_dismissal").withSubtype(ModifierInvocation.class, "modifier_invocation").withSubtype(ModifierRemoval.class, "modifier_removal").withSubtype(MediaTakeover.class, "media_takeover").withSubtype(IdeologyChange.class, "ideology_change").withSubtype(PolicyChange.class, "policy_change").withSubtype(BudgetExpense.class, "budget_expense").withSubtype(BudgetIncome.class, "budget_income")).add(PolymorphicJsonAdapterFactory.of(Condition.class, "type").withSubtype(ModifierCondition.class, "modifier_condition").withSubtype(AdvisorCondition.class, "advisor_condition").withSubtype(MediaCondition.class, "media_condition").withSubtype(AdvisorSkillCondition.class, "trait_condition").withSubtype(IndicatorCondition.class, "indicator_condition").withSubtype(SomeAdvisorCondition.class, "some_advisor_condition").withSubtype(IdeologyCondition.class, "ideology_condition").withSubtype(PolicyCondition.class, "policy_condition").withSubtype(RoundCondition.class, "round_condition")).build();

        //Adding default and Kachakonyan events collection
        ArrayList<Event> events = loadFiles(Event.class, "json/KA/events/", moshi);
        events.addAll(loadFiles(Event.class, "json/DT/events/", moshi));

        //Adding default com.example.demo.resources.people collection
        ArrayList<Person> people = loadFiles(Person.class, "json/KA/people/", moshi);

        //Adding default and Kachakonyan modifiers collection
        ArrayList<Modifier> modifiers = loadFiles(Modifier.class, "json/KA/modifiers/", moshi);
        modifiers.addAll(loadFiles(Modifier.class, "json/DT/modifiers/", moshi));

        //Adding default com.example.demo.resources.com.example.demo.media collection
        ArrayList<MediaGroup> medias = loadFiles(MediaGroup.class, "json/KA/media/", moshi);

        //Loading Policy
        ArrayList<Policy> policies = loadFiles(Policy.class, "json/KA/policies/", moshi);
        policies.addAll(loadFiles(Policy.class, "json/DT/policies/", moshi));

        //Loading com.example.demo.budget
        ArrayList<Budget> budgets = loadFiles(Budget.class, "json/KA/budget/", moshi);

        //Party
        ArrayList<Ideology> ideologies = new ArrayList<>();
        ideologies.add(BigTent);
        ideologies.add(Capitalist);
        ideologies.add(Centrist);
        Party party = new Party("Democratic Party of Kachakonia", "One of the oldest active political parties in Kachakonia, traditionally associated with the bourgeoisie. " + "Currently in power for past 4 years, but under new leadership suffer from internal turmoil.", ideologies);

        game = new Game(events, people, policies, modifiers, medias, party, budgets.get(0));
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("main-view.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("event.css")).toExternalForm());

        mainController = loader.getController();
        mainController.setGame(game);
        Stage window = (Stage) kaButton.getScene().getWindow();
        window.setScene(new Scene(root, 1280, 720));
        window.setFullScreen(true);
    }

    public void onGraoniaButtonClick() throws URISyntaxException, IOException {
        Moshi moshi = new Moshi.Builder().add(PolymorphicJsonAdapterFactory.of(Effect.class, "type").withSubtype(IndicatorChange.class, "indicator_change").withSubtype(RandomAdvisorEmployment.class, "random_advisor_employment").withSubtype(AdvisorEmployment.class, "advisor_employment").withSubtype(RandomAdvisorDismissal.class, "random_advisor_dismissal").withSubtype(AdvisorDismissal.class, "advisor_dismissal").withSubtype(ModifierInvocation.class, "modifier_invocation").withSubtype(ModifierRemoval.class, "modifier_removal").withSubtype(MediaTakeover.class, "media_takeover").withSubtype(IdeologyChange.class, "ideology_change").withSubtype(PolicyChange.class, "policy_change").withSubtype(BudgetExpense.class, "budget_expense").withSubtype(BudgetIncome.class, "budget_income")).add(PolymorphicJsonAdapterFactory.of(Condition.class, "type").withSubtype(ModifierCondition.class, "modifier_condition").withSubtype(AdvisorCondition.class, "advisor_condition").withSubtype(MediaCondition.class, "media_condition").withSubtype(AdvisorSkillCondition.class, "trait_condition").withSubtype(IndicatorCondition.class, "indicator_condition").withSubtype(SomeAdvisorCondition.class, "some_advisor_condition").withSubtype(IdeologyCondition.class, "ideology_condition").withSubtype(PolicyCondition.class, "policy_condition").withSubtype(RoundCondition.class, "round_condition")).build();

        //Adding graonian and default events collection
        ArrayList<Event> events = loadFiles(Event.class, "json/GR/events/", moshi);
        events.addAll(loadFiles(Event.class, "json/DT/events/", moshi));

        //Adding default com.example.demo.resources.people collection
        ArrayList<Person> people = loadFiles(Person.class, "json/GR/people/", moshi);

        //Adding default modifiers collection
        ArrayList<Modifier> modifiers = loadFiles(Modifier.class, "json/GR/modifiers/", moshi);
        modifiers.addAll(loadFiles(Modifier.class, "json/DT/modifiers/", moshi));

        //Adding default com.example.demo.resources.com.example.demo.media collection
        ArrayList<MediaGroup> medias = loadFiles(MediaGroup.class, "json/GR/media/", moshi);

        //Loading Policy
        ArrayList<Policy> policies = loadFiles(Policy.class, "json/GR/policies/", moshi);
        policies.addAll(loadFiles(Policy.class, "json/DT/policies/", moshi));

        //Loading com.example.demo.budget
        ArrayList<Budget> budgets = loadFiles(Budget.class, "json/GR/budget/", moshi);

        //Party
        ArrayList<Ideology> ideologies = new ArrayList<>();
        ideologies.add(BigTent);
        ideologies.add(Capitalist);
        ideologies.add(Centrist);
        Party party = new Party("Democratic Party of Kachakonia", "One of the oldest active political parties in Kachakonia, traditionally associated with the bourgeoisie. " + "Currently in power for past 4 years, but under new leadership suffer from internal turmoil.", ideologies);

        game = new Game(events, people, policies, modifiers, medias, party, budgets.get(0));

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("main-view.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("event.css")).toExternalForm());
        mainController = loader.getController();
        mainController.setGame(game);
        Stage window = (Stage) kaButton.getScene().getWindow();
        window.setScene(new Scene(root, 1280, 720));
        window.setFullScreen(true);

    }

}