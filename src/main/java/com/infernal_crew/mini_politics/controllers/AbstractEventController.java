package com.infernal_crew.mini_politics.controllers;

import com.infernal_crew.mini_politics.event.AbstractEvent;
import com.infernal_crew.mini_politics.event.Dialogue;
import com.infernal_crew.mini_politics.event.Event;
import com.infernal_crew.mini_politics.game.Game;

public abstract class AbstractEventController {
    protected MainController mainController;
    public void setMainController(MainController mainController) {
            this.mainController = mainController;
    }

    public abstract void setEvent(AbstractEvent aEvent);
}
