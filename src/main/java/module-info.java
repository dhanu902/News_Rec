module com.example.oop_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires java.logging;
    requires org.mongodb.driver.core;
    requires com.opencsv;


    opens com.example.oop_1 to javafx.fxml;
    exports com.example.oop_1;
}