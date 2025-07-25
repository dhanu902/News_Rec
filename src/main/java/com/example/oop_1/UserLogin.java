package com.example.oop_1;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.scene.control.PasswordField;
import org.bson.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserLogin implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(UserLogin.class);
    private MongoDatabase database;

    @FXML
    private TextField text_nameL;

    @FXML
    private PasswordField text_passwordL;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("UserDB");
    }

    @FXML
    public void Login_F(ActionEvent event) {
        String pass_USERname = text_nameL.getText();
        String pass_PASSword = text_passwordL.getText();

        try {
            MongoCollection<Document> collection = database.getCollection("Users");

            Document result = collection.find(Filters.eq("U_Name", pass_USERname)).first();
            //Document result = collection.find(query).first();
            System.out.println(result);

            if (result != null) {
                User searchUser = new ExternalUser(
                        result.getObjectId("_id"),
                        result.getString("U_Name"),
                        result.getString("U_Email"),
                        result.getString("U_Password"),
                        result.getString("U_Gender"),
                        result.get("U_Preferences", Document.class)
                );

                if (pass_PASSword.equals(searchUser.getPassword())) {
                    // Successful login
                    try {
                        ((Node) event.getSource()).getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home-view.fxml"));
                        Parent root = loader.load();
                        Home_News controller = loader.getController();
                        controller.setUser(searchUser);
                        controller.setDatabase(Connect_DB.getDatabase());
                        Alert message = new Alert(Alert.AlertType.CONFIRMATION, "Successfully logged as: "+searchUser.getUsername());
                        message.showAndWait();
                        Stage ownerStage = new Stage();
                        Scene scene = new Scene(root);
                        ownerStage.setTitle(searchUser.getUsername() + "'s Home");
                        ownerStage.setScene(scene);
                        ownerStage.show();
                    } catch (IOException ex) {
                        Alert message = new Alert(Alert.AlertType.ERROR, "Loading Failure");
                        message.showAndWait();
                    }
                } else {
                    // Incorrect password
                    Alert message = new Alert(Alert.AlertType.WARNING, "Recheck credentials");
                    message.showAndWait();
                }
            } else {
                // User not found
                Alert message = new Alert(Alert.AlertType.ERROR, "User not found");
                message.showAndWait();
            }
        } catch (Exception e) {
            logger.error("Error occurred while logging in", e);
            Alert message = new Alert(Alert.AlertType.ERROR, "Error while logging in. Check database connection and fields.");
            message.showAndWait();
        }
    }

    @FXML
    void btnBack_L_Clicked(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            WindowChangeAction.closeCurrentWindow(sourceNode);
            WindowChangeAction.showNewStage("hello-view.fxml", "Home");
        } catch (IOException ex) {
            WindowChangeAction.showAlert("Loading Failure");
        }
    }
}