module com.example.ti3tankbattle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ti3tankbattle to javafx.fxml;
    exports com.example.ti3tankbattle;
}