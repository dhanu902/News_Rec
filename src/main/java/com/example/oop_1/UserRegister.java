package com.example.oop_1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.util.Objects;

public class UserRegister {
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> collection;

    @FXML
    private Button btnBack_R;
    @FXML
    private Pane paneRegister;

    @FXML
    private TextField text_nameR;
    @FXML
    private TextField text_emailR;
    @FXML
    private TextField text_passwordR;

    @FXML
    private RadioButton radioB_M;
    @FXML
    private RadioButton radioB_F;

    @FXML
    private DatePicker datePicker;

    @FXML
    void initialize(){
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        mongoDatabase = mongoClient.getDatabase("UserDB");
        collection = mongoDatabase.getCollection("Users");
    }
    @FXML
    void Register_F(ActionEvent event){
        String name = text_nameR.getText();
        String email = text_emailR.getText();
        String password = text_passwordR.getText();
        String sex = radioB_M.isSelected() ? radioB_M.getText() :
                radioB_F.isSelected() ? radioB_F.getText() : null;

        try{
            Document document = new Document("User_Name", name)
                    .append("User_Email", email)
                    .append("User_Password", password)
                    .append("User_Sex", sex);

            if (name.isEmpty() || email.isEmpty() || Objects.requireNonNull(sex).isEmpty() || password.isEmpty()){
                throw new IllegalArgumentException("All fields must be filled");
            }

            collection.insertOne(document);
            Alert msg_R1 = new Alert(Alert.AlertType.CONFIRMATION, "Submitted successfully");
            msg_R1.showAndWait();

            text_nameR.setText("");
            text_emailR.setText("");
            text_passwordR.setText("");

        } catch (Exception e){
            System.err.println("in");
            e.printStackTrace();
            Alert msg_R2 = new Alert(Alert.AlertType.ERROR, "Fail" + e.getMessage());
            msg_R2.showAndWait();
        }
    }

    public void stop() throws Exception{
        Connect_DB.close();
    }

    @FXML
    void btnBack_R_Clicked(ActionEvent event) {
        try{
            ((Node)event.getSource()).getScene().getWindow().hide();
            Stage ownerStage = new Stage();
            Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Scene scene = new Scene(root);
            ownerStage.setTitle("Home");
            ownerStage.setScene(scene);
            ownerStage.show();
        }catch (IOException ex){
            Alert message = new Alert(Alert.AlertType.ERROR, "Loading Failure");
            message.showAndWait();
        }
    }
}