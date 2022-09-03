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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
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

import static com.example.demo.utils.JsonLoader.loadFiles;
import static com.example.demo.party.Ideology.*;

public class MainController {
    private ToggleButton lastSelected;
    @FXML
    private ToggleGroup leftBar;
    @FXML
    private Label roundLabel;
    @FXML
    private PartyController partyController;

    @FXML
    private BudgetController budgetController;

    @FXML
    private MediaController mediaController;
    @FXML
    private PolicyController policyController;
    @FXML
    private VBox infoBar;

    @FXML
    private VBox infoBox;
    @FXML
    private HBox jobBox;
    @FXML
    private HBox indicatorBox;
    @FXML
    private VBox eventBox;
    private Game game;


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
        if (partyController != null) {
            partyController.update();
        }
        if (mediaController != null) {
            mediaController.update();
        }
        if (budgetController != null) {
            budgetController.update();
        }
        if (policyController != null) {
            policyController.update();
        }
        if (game.displayNext()) {
            eventBox.getChildren().clear();
            game.chooseEvent();
            eventBox.getChildren().add(new EventDisplay(game.getCurrentEvent(), this));
        } else {
            eventBox.getChildren().clear();
            eventBox.getChildren().add(new JobChoiceDisplay(game.getCurrentPerson(), this));
        }
        roundLabel.setText(String.valueOf(game.getRound()));
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

    private void setInfoBox(String fxmlPath, InfoBoxController controller) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlPath));
        AnchorPane anchor;
        try {
            anchor = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        infoBox.getChildren().clear();
        infoBox.getChildren().add(anchor);
        switch (controller) {
            case Party -> partyController = loader.getController();
            case Budget -> budgetController = loader.getController();
            case Media -> mediaController = loader.getController();
            case Policy -> policyController = loader.getController();
        }


    }

    @FXML
    public void onPartyButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == lastSelected) {
            infoBox.getChildren().clear();
            infoBox.getChildren().add(new AnchorPane());
            lastSelected = null;
            return;
        }
        setInfoBox("party-view.fxml", InfoBoxController.Party);
        partyController.setMainController(this);
        partyController.update();
        lastSelected = (ToggleButton) actionEvent.getSource();
    }

    @FXML
    public void onMediaButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == lastSelected) {
            infoBox.getChildren().clear();
            infoBox.getChildren().add(new AnchorPane());
            lastSelected = null;
            return;
        }
        setInfoBox("media-view.fxml", InfoBoxController.Media);
        mediaController.setMainController(this);
        mediaController.update();
        lastSelected = (ToggleButton) actionEvent.getSource();
    }

    @FXML
    public void onBudgetButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == lastSelected) {
            infoBox.getChildren().clear();
            infoBox.getChildren().add(new AnchorPane());
            lastSelected = null;
            return;
        }
        setInfoBox("budget-view.fxml", InfoBoxController.Budget);
        budgetController.setMainController(this);
        budgetController.update();
        lastSelected = (ToggleButton) actionEvent.getSource();
    }

    @FXML
    public void onPolicyButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource() == lastSelected) {
            infoBox.getChildren().clear();
            infoBox.getChildren().add(new AnchorPane());
            lastSelected = null;
            return;
        }
        setInfoBox("policy-view.fxml", InfoBoxController.Policy);
        policyController.setMainController(this);
        policyController.update();
        lastSelected = (ToggleButton) actionEvent.getSource();
    }
}