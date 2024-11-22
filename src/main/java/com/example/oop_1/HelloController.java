package com.example.oop_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    @FXML
    private Button btnLogin_I;
    @FXML
    private Button btnRegister_I;

    @FXML
    private Pane paneLnR;
    @FXML
    private Pane paneRec;

    @FXML
    public void btnClicks1(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnLogin_I) {
            try{
                ((Node)actionEvent.getSource()).getScene().getWindow().hide();
                Stage ownerStage = new Stage();
                Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Log-view.fxml")));
                Scene scene = new Scene(root);
                ownerStage.setTitle("Register");
                ownerStage.setScene(scene);
                ownerStage.show();
            }catch (IOException ex){
                Alert message = new Alert(Alert.AlertType.ERROR, "Loading Failure");
                message.showAndWait();
            }
        }
        if (actionEvent.getSource() == btnRegister_I){
            try{
                ((Node)actionEvent.getSource()).getScene().getWindow().hide();
                Stage ownerStage = new Stage();
                Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Reg-view.fxml")));
                Scene scene = new Scene(root);
                ownerStage.setTitle("Register");
                ownerStage.setScene(scene);
                ownerStage.show();
            }catch (IOException ex){
                Alert message = new Alert(Alert.AlertType.ERROR, "Loading Failure");
                message.showAndWait();
            }
        }
    }
}

//...................................................................................................
/*
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> collection;

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
            Document document = new Document("U_Name", name)
                                     .append("U_Email", email)
                                     .append("U_Password", password)
                                     .append("U_Sex", sex);

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

//......................................................................................................................
*/
