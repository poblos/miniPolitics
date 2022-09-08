package com.example.demo;

public abstract class BarController {
    protected MainController mainController;
    public void setMainController(MainController mainController) {
            this.mainController = mainController;
    }
    public abstract void update();
}
