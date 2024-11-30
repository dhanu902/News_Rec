package com.example.oop_1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserRegister {
    private static final Logger logger = LoggerFactory.getLogger(UserRegister.class);
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> collection;

    @FXML
    private Button btnBack_R;

    @FXML
    private TextField text_nameR;
    @FXML
    private TextField text_emailR;
    @FXML
    private TextField text_passwordR;
    @FXML
    private TextField text_tel;

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
    void Register_F(ActionEvent event) {
        try {
            final User newUser = getUser(text_nameR, text_emailR, text_passwordR, radioB_M, radioB_F);

            // User object -> MongoDB Document
            Document USER_doc = new Document("U_Name", newUser.getUsername())
                    .append("U_Name", newUser.getUsername())
                    .append("U_Email", newUser.getEmail())
                    .append("U_Password", newUser.getPassword())
                    .append("U_Gender", newUser.getGender())
                    .append("U_Preferences", newUser.getPreferences());

            // Insert data into MongoDB
            collection.insertOne(USER_doc);

            ObjectId generateID = USER_doc.getObjectId("_id");
            newUser.setId(generateID);
            System.out.println("_id: "+ USER_doc.getObjectId("_id").toString());

            // Open login
            Alert message = new Alert(Alert.AlertType.INFORMATION, "Submitted Successfully");
            message.showAndWait();
            try {
                Node sourceNode = (Node) event.getSource();
                WindowChangeAction.closeCurrentWindow(sourceNode);
                WindowChangeAction.showNewStage("Log-view.fxml", "Login");
            } catch (IOException ex) {
                WindowChangeAction.showAlert("Loading Failure");
            }

        } catch (IllegalArgumentException e) {
            Alert message = new Alert(Alert.AlertType.ERROR, e.getMessage());
            message.showAndWait();
        } catch (Exception e) {
            System.err.println("Error occurred while inserting the document");
            logger.error("Error occurred while inserting the document", e);
            Alert message = new Alert(Alert.AlertType.ERROR, "Data entry failed: " + e.getMessage());
            message.showAndWait();
        }
//        if (!text_tel.getText().matches("\\d{10}")) {
//            Alert message = new Alert(Alert.AlertType.ERROR, "Telephone number must contain only numbers.");
//            message.showAndWait();
//        }
    }

    @FXML
    void btnBack_R_Clicked(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            WindowChangeAction.closeCurrentWindow(sourceNode);
            WindowChangeAction.showNewStage("hello-view.fxml", "Home");
        } catch (IOException ex) {
            WindowChangeAction.showAlert("Loading Failure");
        }
    }

    private static User getUser(TextField text_nameR, TextField text_emailR, TextField text_passwordR, RadioButton radioB_M, RadioButton radioB_F) {
        if (text_nameR.getText().isEmpty() ||
                text_emailR.getText().isEmpty() ||
                text_passwordR.getText().isEmpty() ) {
            throw new IllegalArgumentException("All fields must be filled.");
        }
        if (!text_nameR.getText().matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Name must contain only letters.");
        }
        if (!text_emailR.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (!text_passwordR.getText().matches(".{5,10}")) {
            throw new IllegalArgumentException("Password must be between 5 and 10 characters.");
        }

        // Parse inputs
        String Name = text_nameR.getText();
        String Email = text_emailR.getText();
        String Password = text_passwordR.getText();
        String Gender = radioB_M.isSelected() ? radioB_M.getText() : radioB_F.isSelected() ? radioB_F.getText() : null;

        if (Gender == null) {
            throw new IllegalArgumentException("Gender must be selected.");
        }

        // Create a User object
        return new ExternalUser(
                null, // ID as String (or you can use ObjectId if required)
                Name,
                Email,
                Password,
                Gender,
                new Document() // Preferences initially empty
        );
    }

    public void stop() throws Exception{
        Connect_DB.close();
    }
}