package com.example.oop_1;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.scene.layout.Pane;
import org.bson.Document;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserLogin implements Initializable {
    private MongoDatabase database;
    @FXML
    private Pane paneLogin;
    @FXML
    private Button btnLogin_F;
    @FXML
    private Button btnBack_L;

    @FXML
    private TextField text_nameL;
    @FXML
    private TextField text_passwordL;

    String username;
    String password;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("UserDB");
    }
    @FXML
    public void Login_F(ActionEvent event) {
        String USERname = text_nameL.getText();
        String PASSword = text_passwordL.getText();

        try {
            MongoCollection<Document> collection = database.getCollection("Users");

            Document result = collection.find(Filters.eq("U_Name", USERname)).first();
            //Document result = collection.find(query).first();
            System.out.println(result);

            if (result != null) {
                username = result.getString("U_Name");
                password = result.getString("U_Password");

                if (PASSword.equals(password)) {
                    // Successful login
                    try {
                        ((Node) event.getSource()).getScene().getWindow().hide();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home-view.fxml"));
                        Parent root = loader.load();
                        //HelloController controller = loader.getController();
                        //controller.setName(username);
                        Stage ownerStage = new Stage();
                        Scene scene = new Scene(root);
                        ownerStage.setTitle("Home");
                        ownerStage.setScene(scene);
                        ownerStage.show();
                    } catch (IOException ex) {
                        Alert message = new Alert(Alert.AlertType.ERROR, "Loading Failure");
                        message.showAndWait();
                    }
                } else {
                    // Incorrect password
                    Alert message = new Alert(Alert.AlertType.ERROR, "Recheck credentials");
                    message.showAndWait();
                }
            } else {
                // User not found
                Alert message = new Alert(Alert.AlertType.ERROR, "User not found");
                message.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert message = new Alert(Alert.AlertType.ERROR, "Error while logging in. Check database connection and fields.");
            message.showAndWait();
        }
    }

    @FXML
    void btnBack_L_Clicked(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage ownerStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Scene scene = new Scene(root);
            ownerStage.setTitle("Home");
            ownerStage.setScene(scene);
            ownerStage.show();
        } catch (IOException ex) {
            Alert message = new Alert(Alert.AlertType.ERROR, "Loading Failure");
            message.showAndWait();
        }
    }
}