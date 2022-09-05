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
    exports com.example.demo.utils;
    exports com.example.demo.game;
    exports com.example.demo.jobs;
    exports com.example.demo.policy;
    opens com.example.demo.utils to javafx.fxml, moshi;
}