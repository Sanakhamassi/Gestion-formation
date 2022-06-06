module com.example.gestion_formation {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.base;

    requires de.jensd.fx.glyphs.fontawesome;

    opens com.example.gestion_formation to javafx.fxml;

    exports com.example.gestion_formation;
    opens com.example.gestion_formation.models to javafx.base;
}