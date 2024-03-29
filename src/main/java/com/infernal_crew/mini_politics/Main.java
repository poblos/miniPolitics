package com.infernal_crew.mini_politics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("templates/start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles/start-view.css")).toExternalForm());
        stage.setTitle("miniPolitics");
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setFullScreen(true);
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/com/infernal_crew/mini_politics/menu_icons/planet.png"))));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}