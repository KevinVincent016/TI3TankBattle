module com.example.ti3tankbattle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.ti3tankbattle to javafx.fxml;
    exports com.example.ti3tankbattle;
    exports com.example.ti3tankbattle.controller;
    opens com.example.ti3tankbattle.controller to javafx.fxml;
}