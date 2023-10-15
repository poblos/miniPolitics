package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.game.Game;
import com.infernal_crew.mini_politics.indicators.Indicator;
import com.infernal_crew.mini_politics.utils.DraggableMaker;
import com.infernal_crew.mini_politics.jobs.Job;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import com.infernal_crew.mini_politics.jobs.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController {
    @FXML
    private VBox peopleBox;
    @FXML
    private ToggleGroup leftBar;
    private ToggleButton lastSelected;
    @FXML
    private Label roundLabel;
    @FXML
    private BarController barController;

    @FXML
    private VBox infoBox;
    @FXML
    private HBox jobBox;
    @FXML
    private HBox indicatorBox;
    @FXML
    private VBox eventBox;
    private Game game;
    private boolean peopleListShowed = false;


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        indicatorBox.getChildren().add(new IndicatorDisplay("PartyCohesion"));
        indicatorBox.getChildren().add(new IndicatorDisplay("StateStability"));
        indicatorBox.getChildren().add(new IndicatorDisplay("PartySupport"));

        jobBox.getChildren().add(new JobDisplay(Job.Whip, this));
        jobBox.getChildren().add(new JobDisplay(Job.Propagandist, this));
        jobBox.getChildren().add(new JobDisplay(Job.Strategist, this));

        updateUpperBar();
        game.chooseEvent();
        DraggableMaker maker = new DraggableMaker();
        maker.makeDraggable(eventBox);
        eventBox.getChildren().add(new EventDisplay(game.getCurrentEvent(), this));
    }

    public void handleEvent(int click) {
        game.handleEvent(click);
        updateUpperBar();

        eventBox.requestFocus();

        if (barController != null) {
            barController.update();
        }

        // Game is lost
        for(Indicator ind : Indicator.values()) {
            if (game.getIndicatorValue(ind) < 0 && ind != Indicator.InfrastructureCorruption) {
                eventBox.getChildren().clear();
                eventBox.getChildren().add(new EventDisplay(game.getLoseEvent(ind), this));
                return;
            }
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

    public void updateUpperBar() {
        for (Node display : indicatorBox.getChildren()) {
            ((IndicatorDisplay) display).update(game);
        }
        for (Node display : jobBox.getChildren()) {
            ((JobDisplay) display).update(game);
        }
    }

    public void handleJobChoice(Job job) {
        game.employ(job);
        for (Node display : jobBox.getChildren()) {
            if (job == ((JobDisplay) display).getJob()) {
                ((JobDisplay) display).update(game);
            }
        }
        updateUpperBar();
        eventBox.getChildren().clear();
        game.chooseEvent();
        eventBox.getChildren().add(new EventDisplay(game.getCurrentEvent(), this));

    }

    private void setInfoBox(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlPath));
        Node node;
        try {
            node = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        infoBox.getChildren().clear();
        infoBox.getChildren().add(node);
        barController = loader.getController();
        barController.setMainController(this);
        barController.update();
    }

    @FXML
    public void onPartyButtonClick(ActionEvent actionEvent) {
        if (ifRequiresCleaning(actionEvent)) {
            return;
        }
        setInfoBox("party-view.fxml");
    }

    @FXML
    public void onMediaButtonClick(ActionEvent actionEvent) {
        if (ifRequiresCleaning(actionEvent)) {
            return;
        }
        setInfoBox("media-view.fxml");
    }

    @FXML
    public void onBudgetButtonClick(ActionEvent actionEvent) {
        if (ifRequiresCleaning(actionEvent)) {
            return;
        }
        setInfoBox("budget-view.fxml");
    }

    @FXML
    public void onPolicyButtonClick(ActionEvent actionEvent) {
        if (ifRequiresCleaning(actionEvent)) {
            return;
        }
        setInfoBox("policy-view.fxml");

    }

    private boolean ifRequiresCleaning(ActionEvent actionEvent) {
        if (actionEvent.getSource() == lastSelected) {
            infoBox.getChildren().clear();
            infoBox.getChildren().add(new AnchorPane());
            lastSelected = null;
            return true;
        }
        lastSelected = (ToggleButton) actionEvent.getSource();
        return false;
    }

    public void onStoryButtonClick(ActionEvent actionEvent) {
        if (ifRequiresCleaning(actionEvent)) {
            return;
        }
        setInfoBox("story-view.fxml");
    }

    private void setPeopleBox(String fxmlPath, Job job) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlPath));
        Node node;
        try {
            node = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        peopleBox.getChildren().clear();
        peopleBox.getChildren().add(node);
        PeopleController peopleBoxController = loader.getController();
        peopleBoxController.setMainController(this);
        peopleBoxController.setJob(job);
        peopleBoxController.update();
    }

    public void showPeopleList(Job job) {
        if (peopleListShowed) {
            peopleBox.getChildren().clear();
            peopleListShowed = false;
        } else {
            setPeopleBox("people-view.fxml", job);
            peopleListShowed = true;
        }

    }
}