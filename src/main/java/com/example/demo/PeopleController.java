package com.example.demo;

import com.example.demo.jobs.Person;
import com.example.demo.modelFx.PersonModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class PeopleController {
    @FXML
    private TableColumn<PersonModel, ImageView> photoCol;
    @FXML
    private TableColumn<PersonModel, Integer> idCol;
    @FXML
    private TableView<PersonModel> peopleTable;
    @FXML
    private TableColumn<PersonModel, String> nameCol;
    private MainController mainController;

    final ObservableList<PersonModel> data = FXCollections.observableArrayList();

    public void update() {
        for (Person person : mainController.getGame().getActivePeople().values()) {
            data.add(new PersonModel(person.getName(), person.getId()));
        }
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        photoCol.setCellValueFactory(new PropertyValueFactory<>("photo"));
        peopleTable.setItems(data);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
