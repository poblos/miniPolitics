package com.example.demo.modelFx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PersonModel {
    private ImageView photo;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty id;

    public PersonModel(String name, int id) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/com/example/demo/menu_icons/people/" +
                name + ".png")).toExternalForm(), 40, 40,false,false);
        this.photo = new ImageView(image);
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleIntegerProperty(id);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public ImageView getPhoto() {
        return photo;
    }
}
