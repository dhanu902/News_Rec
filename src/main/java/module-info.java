module com.example.oop_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.oop_1 to javafx.fxml;
    exports com.example.oop_1;
}