package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.Main;
import com.infernal_crew.mini_politics.components.*;
import com.infernal_crew.mini_politics.event.AbstractEvent;
import com.infernal_crew.mini_politics.event.Event;
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
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainController {
    MediaPlayer mediaPlayer;
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
    private AbstractEventController eventController;

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
        setEventBox("event-view.fxml", game.getCurrentEvent());

        playMusic();
    }

    private void playMusic() {
        String musicFile = "/com/infernal_crew/mini_politics/music/GR-Hymn_of_The_Pilgrims.mp3";

        String path = Objects.requireNonNull(Main.class.getResource(musicFile)).toString();
        Media sound = new Media(path);

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
    }

    public void handleEvent(int click) {
        game.handleEvent(click);
        updateUpperBar();

        eventBox.requestFocus();

        if (barController != null) {
            barController.update(game);
        }

        // Game is lost
        for(Indicator ind : Indicator.values()) {
            if (game.getIndicatorValue(ind) < 0 && ind != Indicator.InfrastructureCorruption && ind != Indicator.NarongWarBalance) {
                eventBox.getChildren().clear();
                setEventBox("event-view.fxml", game.getLoseEvent(ind));
                return;
            }
        }

        if(game.getDialogueId() != null) {
            eventBox.getChildren().clear();
            setEventBox("dialogue-view.fxml", game.getDialogue(game.getDialogueId()));
        } else if (game.getCurrentPerson() != null) {
            eventBox.getChildren().clear();
            eventBox.getChildren().add(new JobChoiceDisplay(game.getCurrentPerson(), this));
        } else {
            eventBox.getChildren().clear();
            game.chooseEvent();
            setEventBox("event-view.fxml", game.getCurrentEvent());
        }
        roundLabel.setText(String.valueOf(game.getRound()));
    }

    public void handleDialogue(int click) {
        game.setDialogueId(null);
        eventBox.getChildren().clear();
        game.chooseEvent();
        setEventBox("event-view.fxml", game.getCurrentEvent());
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
        game.setCurrentPerson(null);
        eventBox.getChildren().clear();
        game.chooseEvent();
        setEventBox("event-view.fxml", game.getCurrentEvent());

    }

    private void setEventBox(String fxmlPath, AbstractEvent event) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/infernaL_crew/mini_politics/templates/" + fxmlPath));
        Node node;
        try {
            node = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        eventBox.getChildren().clear();
        eventBox.getChildren().add(node);
        eventController = loader.getController();
        eventController.setMainController(this);
        eventController.setEvent(event);
    }

    private void setInfoBox(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/infernaL_crew/mini_politics/templates/" + fxmlPath));
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
        barController.update(game);
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

    @FXML
    public void onWarButtonClick(ActionEvent actionEvent) {
        if (ifRequiresCleaning(actionEvent)) {
            return;
        }
        setInfoBox("war-view.fxml");
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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/infernal_crew/mini_politics/templates/"+fxmlPath));
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