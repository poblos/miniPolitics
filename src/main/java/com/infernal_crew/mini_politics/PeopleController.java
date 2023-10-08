package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.jobs.Job;
import com.infernal_crew.mini_politics.jobs.Person;
import com.infernal_crew.mini_politics.modelFx.PersonModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PeopleController {
    private Job job;
    @FXML
    private TableView<PersonModel> peopleTable;
    @FXML
    private TableColumn<PersonModel, Integer> idCol;
    @FXML
    private TableColumn<PersonModel, ImageView> photoCol;
    @FXML
    private TableColumn<PersonModel, String> nameCol;
    @FXML
    private TableColumn<PersonModel, Button> employCol;
    private MainController mainController;

    final ObservableList<PersonModel> data = FXCollections.observableArrayList();

    public void update() {
        for (Person person : mainController.getGame().getActivePeople().values()) {
            data.add(new PersonModel(person.getName(), person.getId()));
        }
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        photoCol.setCellValueFactory(new PropertyValueFactory<>("photo"));
        employCol.setCellFactory(param -> new TableCell<>() {
            final Button button = createButton("/com/infernal_crew/mini_politics/menu_icons/employ.png");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);

                    int id = getTableView().getItems().get(getIndex()).getId();
                    if (!mainController.getGame().isEmployed(id) && !mainController.getGame().getCooldown().containsKey(id)) {
                        button.setOnAction(event -> {
                            mainController.getGame().employ(job, id);
                            mainController.updateUpperBar();
                        });
                    } else {
                        button.setDisable(true);
                    }

                }
            }
        });

        peopleTable.setItems(data);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private Button createButton(String path) {
        Button button = new Button();
        button.getStyleClass().add("indicatorButton");
        Image image = new Image(Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm(),30, 30, false, false);
        button.setGraphic(new ImageView(image));

        return button;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
