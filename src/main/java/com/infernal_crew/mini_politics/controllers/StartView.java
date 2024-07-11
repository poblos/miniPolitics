package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.budget.Budget;
import com.infernal_crew.mini_politics.budget.BudgetExpense;
import com.infernal_crew.mini_politics.budget.BudgetIncome;
import com.infernal_crew.mini_politics.event.*;
import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.game.RoundCondition;
import com.infernal_crew.mini_politics.indicators.IndicatorChange;
import com.infernal_crew.mini_politics.indicators.IndicatorCondition;
import com.infernal_crew.mini_politics.indicators.TraitIndicatorEffect;
import com.infernal_crew.mini_politics.media.MediaCondition;
import com.infernal_crew.mini_politics.media.MediaGroup;
import com.infernal_crew.mini_politics.media.MediaIdCondition;
import com.infernal_crew.mini_politics.media.MediaTakeover;
import com.infernal_crew.mini_politics.modifiers.Modifier;
import com.infernal_crew.mini_politics.modifiers.ModifierCondition;
import com.infernal_crew.mini_politics.modifiers.ModifierInvocation;
import com.infernal_crew.mini_politics.modifiers.ModifierRemoval;
import com.infernal_crew.mini_politics.party.IdeologyChange;
import com.infernal_crew.mini_politics.party.IdeologyCondition;
import com.infernal_crew.mini_politics.party.Party;
import com.infernal_crew.mini_politics.policy.Policy;
import com.infernal_crew.mini_politics.policy.PolicyChange;
import com.infernal_crew.mini_politics.policy.PolicyCondition;
import com.infernal_crew.mini_politics.jobs.*;
import com.infernal_crew.mini_politics.story.StoryNote;
import com.infernal_crew.mini_politics.utils.ColorAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

import static com.infernal_crew.mini_politics.utils.JsonLoader.loadFiles;

public class StartView {
    @FXML
    private Button kaButton, grButton;

    @FXML
    private VBox leftFilmRoll;

    @FXML
    private VBox rightFilmRoll;

    @FXML
    private MainController mainController;

    private void startFilmRoll(VBox roll, int from, int to) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), roll);
        transition.setFromY(from);
        transition.setToY(to);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setByY(-100);
        transition.play();
    }

    public void initialize() {
        startFilmRoll(leftFilmRoll, -100, 0);
        startFilmRoll(rightFilmRoll, 0, -100);
    }

    private Game constructGame(String nationTag) throws URISyntaxException, IOException {
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
                        .withSubtype(AdvisorPositionEmployment.class, "advisor_position_employment")
                        .withSubtype(DialogueInvocation.class, "dialogue_invocation"))
                .add(PolymorphicJsonAdapterFactory.of(Condition.class, "type")
                        .withSubtype(ModifierCondition.class, "modifier_condition")
                        .withSubtype(AdvisorCondition.class, "advisor_condition")
                        .withSubtype(MediaCondition.class, "media_condition")
                        .withSubtype(MediaIdCondition.class, "media_id_condition")
                        .withSubtype(AdvisorSkillCondition.class, "trait_condition")
                        .withSubtype(IndicatorCondition.class, "indicator_condition")
                        .withSubtype(SomeAdvisorCondition.class, "some_advisor_condition")
                        .withSubtype(IdeologyCondition.class, "ideology_condition")
                        .withSubtype(PolicyCondition.class, "policy_condition")
                        .withSubtype(RoundCondition.class, "round_condition")
                        .withSubtype(PersonCondition.class, "person_condition"))
                .add(PolymorphicJsonAdapterFactory.of(TraitEffect.class, "type")
                        .withSubtype(TraitIndicatorEffect.class, "indicator_effect"))
                .add(PolymorphicJsonAdapterFactory.of(Part.class, "type")
                        .withSubtype(DescriptionPart.class, "description")
                        .withSubtype(ChoicePart.class, "choice")
                        .withSubtype(PersonPart.class, "person"))
                .add(new ColorAdapter()).build();

        ArrayList<Event> events = loadFiles(Event.class, "json/" + nationTag + "/events/", moshi);
        events.addAll(loadFiles(Event.class, "json/DT/events/", moshi));

        ArrayList<Dialogue> dialogues = loadFiles(Dialogue.class, "json/" + nationTag + "/dialogues/", moshi);

        ArrayList<Person> people = loadFiles(Person.class, "json/" + nationTag + "/people/inactive", moshi);

        ArrayList<Person> activePeople = loadFiles(Person.class, "json/" + nationTag + "/people/active/", moshi);

        ArrayList<Modifier> modifiers = loadFiles(Modifier.class, "json/" + nationTag + "/modifiers/", moshi);
        modifiers.addAll(loadFiles(Modifier.class, "json/DT/modifiers/", moshi));

        ArrayList<MediaGroup> medias = loadFiles(MediaGroup.class, "json/" + nationTag + "/media/", moshi);

        ArrayList<Policy> policies = loadFiles(Policy.class, "json/" + nationTag + "/policies/", moshi);
        policies.addAll(loadFiles(Policy.class, "json/DT/policies/", moshi));

        ArrayList<Budget> budgets = loadFiles(Budget.class, "json/" + nationTag + "/budget/", moshi);

        ArrayList<StoryNote> notes = loadFiles(StoryNote.class, "json/" + nationTag + "/story/", moshi);

        ArrayList<Trait> traits = loadFiles(Trait.class, "json/" + nationTag + "/traits/", moshi);

        ArrayList<Party> parties = loadFiles(Party.class, "json/" + nationTag + "/parties/", moshi);

        printNumberOf("events", events.size());
        printNumberOf("dialogues", dialogues.size());
        return new Game(events, dialogues, people, activePeople, policies, modifiers, medias, budgets.get(0), notes, traits, parties);
    }

    private void startGame(Game game) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/infernal_crew/mini_politics/templates/main-view.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/infernal_crew/mini_politics/styles/main_view.css")).toExternalForm());
        mainController = loader.getController();
        mainController.setGame(game);
        Stage window = (Stage) kaButton.getScene().getWindow();
        Scene scene = new Scene(root, 1280, 720);
        scene.getRoot().requestFocus();
        window.setScene(scene);

        window.setFullScreen(true);
    }

    public void onKachakonyaButtonClick() throws URISyntaxException, IOException {
        Game game = constructGame("KA");
        startGame(game);
    }

    public void onGraoniaButtonClick() throws URISyntaxException, IOException {
        Game game = constructGame("GR");
        startGame(game);
    }

    private void printNumberOf(String what, int number) {
        System.out.println("Number of loaded " + what + ": " + number);
    }

}