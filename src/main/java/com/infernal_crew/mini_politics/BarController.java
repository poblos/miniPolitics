package com.infernal_crew.mini_politics;

import com.infernal_crew.mini_politics.game.Game;

public abstract class BarController {
    protected MainController mainController;
    public void setMainController(MainController mainController) {
            this.mainController = mainController;
    }
    public abstract void update(Game game);
}
