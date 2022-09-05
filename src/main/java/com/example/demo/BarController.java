package com.example.demo;

public abstract class BarController {
    protected MainController mainController;
    public abstract void update();

    public void setMainController(MainController mainController) {

            this.mainController = mainController;
    }
}
