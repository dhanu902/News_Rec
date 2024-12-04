package com.example.oop_1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLogin implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(AdminLogin.class);
    private MongoDatabase database;

    @FXML
    private TextField text_nameL;
    @FXML
    private PasswordField text_passwordL;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("AdminDB");
    }

    @FXML
    public void Login_Admin(ActionEvent event) {
        String pass_ADMINname = text_nameL.getText().toLowerCase();
        String pass_PASSword = text_passwordL.getText();

        try {
            MongoCollection<Document> collection = database.getCollection("Admins");

            Document adminDoc = collection.find(new Document("A_Name", pass_ADMINname)).first();
            //Document result = collection.find(query).first();

            if (adminDoc != null) {
                User searchUser = new Admin(
                        adminDoc.getString("A_ID"),
                        adminDoc.getString("A_Name"),
                        adminDoc.getString("A_Email"),
                        adminDoc.getString("A_Password")

                );


                System.out.println(pass_PASSword + searchUser.getPassword() + "\n" + adminDoc.toJson());

                if (pass_PASSword.equals(searchUser.getPassword())) {
                    // Successful login
                    try {
                        Node sourceNode = (Node) event.getSource();
                        WindowChangeAction.closeCurrentWindow(sourceNode);
                        WindowChangeAction.showNewStage("AdminHome-view.fxml", "Admin Home");
                    } catch (IOException ex) {
                        WindowChangeAction.showAlert("Loading Failure");
                    }
                } else {
                    // Incorrect password
                    Alert message = new Alert(Alert.AlertType.ERROR, "Recheck credentials");
                    message.showAndWait();
                }
            } else {
                // User not found
                Alert message = new Alert(Alert.AlertType.ERROR, "Admin not found");
                message.showAndWait();
            }
        } catch (Exception e) {
            logger.error("Error occurred while logging in", e);
            Alert message = new Alert(Alert.AlertType.ERROR, "Error while logging in. Check database connection and fields.");
            message.showAndWait();
        }
    }

    @FXML
    void btnBack_LA_Clicked(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            WindowChangeAction.closeCurrentWindow(sourceNode);
            WindowChangeAction.showNewStage("hello-view.fxml", "Home");
        } catch (IOException ex) {
            WindowChangeAction.showAlert("Loading Failure");
        }
    }
}