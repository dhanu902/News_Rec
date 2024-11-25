module com.example.oop_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires java.logging;
    requires org.mongodb.driver.core;
    requires com.opencsv;
    requires java.desktop;
    requires org.slf4j;

    opens com.example.oop_1 to javafx.fxml, com.google.gson;
    exports com.example.oop_1;
}