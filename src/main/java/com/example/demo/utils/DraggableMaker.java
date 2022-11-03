package com.example.demo.utils;

import javafx.scene.Node;

public class DraggableMaker {
    private double mouseX;
    private double mouseY;

    public void makeDraggable(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            double newX = mouseEvent.getSceneX() - mouseX - node.getParent().getLayoutX();
            double newY = mouseEvent.getSceneY() - mouseY - node.getParent().getLayoutY();
            if (newX < node.getScene().getWidth() - 1200 && newX > 0) {
                node.setLayoutX(newX);
            }
            if (newY < node.getScene().getHeight() - 650 && newY > 0) {
                node.setLayoutY(newY);
            }
        });
    }

}
