module com.infernal_crew.mini_politics {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires moshi;
    opens com.infernal_crew.mini_politics.event to moshi;
    opens com.infernal_crew.mini_politics.budget to moshi;
    opens com.infernal_crew.mini_politics.indicators to moshi;
    opens com.infernal_crew.mini_politics.jobs to moshi;
    opens com.infernal_crew.mini_politics.modifiers to moshi;
    opens com.infernal_crew.mini_politics.media to moshi;
    opens com.infernal_crew.mini_politics.party to moshi;
    opens com.infernal_crew.mini_politics.policy to moshi;
    opens com.infernal_crew.mini_politics.game to moshi;
    opens com.infernal_crew.mini_politics to javafx.fxml, moshi;
    opens com.infernal_crew.mini_politics.modelFx to javafx.base;
    exports com.infernal_crew.mini_politics.game;
    exports com.infernal_crew.mini_politics.jobs;
    exports com.infernal_crew.mini_politics.policy;
    exports com.infernal_crew.mini_politics.event;
    exports com.infernal_crew.mini_politics.media;
    exports com.infernal_crew.mini_politics.party;
    exports com.infernal_crew.mini_politics.budget;
    exports com.infernal_crew.mini_politics.indicators;
    exports com.infernal_crew.mini_politics.modifiers;
    exports com.infernal_crew.mini_politics to javafx.graphics;
    opens com.infernal_crew.mini_politics.utils to javafx.fxml, moshi;
}