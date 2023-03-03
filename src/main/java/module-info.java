module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires moshi;

    exports com.example.demo;
    opens com.example.demo.event to moshi;
    opens com.example.demo.budget to moshi;
    opens com.example.demo.indicators to moshi;
    opens com.example.demo.jobs to moshi;
    opens com.example.demo.modifiers to moshi;
    opens com.example.demo.media to moshi;
    opens com.example.demo.party to moshi;
    opens com.example.demo.policy to moshi;
    opens com.example.demo.game to moshi;
    opens com.example.demo to javafx.fxml, moshi;
    opens com.example.demo.modelFx to javafx.base;
    exports com.example.demo.game;
    exports com.example.demo.jobs;
    exports com.example.demo.policy;
    exports com.example.demo.event;
    exports com.example.demo.media;
    exports com.example.demo.party;
    exports com.example.demo.budget;
    exports com.example.demo.indicators;
    exports com.example.demo.modifiers;
    opens com.example.demo.utils to javafx.fxml, moshi;
}