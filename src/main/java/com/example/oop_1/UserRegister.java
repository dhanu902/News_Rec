package com.example.oop_1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;

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
    void Register_F(ActionEvent event) {
        try {
            // Validate input fields
            final User newUser = getUser(text_nameR, text_emailR, text_passwordR, radioB_M, radioB_F);

            // Convert User object to MongoDB Document
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
            
            Alert message = new Alert(Alert.AlertType.INFORMATION, "Submitted Successfully");
            message.showAndWait();
            try {
                Node sourceNode = (Node) event.getSource();
                WindowChangeAction.closeCurrentWindow(sourceNode);
                WindowChangeAction.showNewStage("Log-view.fxml", "Login");
            } catch (IOException ex) {
                WindowChangeAction.showAlert("Loading Failure");
            }

        } catch (NumberFormatException e) {
            Alert message = new Alert(Alert.AlertType.ERROR, "Invalid number format in ID or Phone Number.");
            message.showAndWait();
        } catch (IllegalArgumentException e) {
            Alert message = new Alert(Alert.AlertType.ERROR, e.getMessage());
            message.showAndWait();
        } catch (Exception e) {
            System.err.println("Error occurred while inserting the document:");
            e.printStackTrace();
            Alert message = new Alert(Alert.AlertType.ERROR, "Data entry failed: " + e.getMessage());
            message.showAndWait();
        }
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
        if (text_nameR.getText().isEmpty() || text_emailR.getText().isEmpty() ||
                text_passwordR.getText().isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled.");
        }
        if (text_nameR.getText().matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Name must contain only letters.");
        }
        if (text_emailR.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Email must contain only letters.");
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
        // ID as String (or you can use ObjectId if required)
        // Preferences initially empty
        return new ConcreteUser(
                null, // ID as String (or you can use ObjectId if required)
                Name,
                Email,
                Password,
                Gender,
                new Document() // Preferences initially empty
        );
    }

//    @FXML
//    void Register_F1(ActionEvent event){
//        String name = text_nameR.getText();
//        String email = text_emailR.getText();
//        String password = text_passwordR.getText();
//        String sex = radioB_M.isSelected() ? radioB_M.getText() :
//                radioB_F.isSelected() ? radioB_F.getText() : null;
//
//        try{
//            Document document = new Document("User_Name", name)
//                    .append("User_Email", email)
//                    .append("User_Password", password)
//                    .append("User_Sex", sex);
//
//            if (name.isEmpty() || email.isEmpty() || Objects.requireNonNull(sex).isEmpty() || password.isEmpty()){
//                throw new IllegalArgumentException("All fields must be filled");
//            }
//
//            collection.insertOne(document);
//            Alert msg_R1 = new Alert(Alert.AlertType.CONFIRMATION, "Submitted successfully");
//            msg_R1.showAndWait();
//
//            text_nameR.setText("");
//            text_emailR.setText("");
//            text_passwordR.setText("");
//
//        } catch (Exception e){
//            System.err.println("in");
//            e.printStackTrace();
//            Alert msg_R2 = new Alert(Alert.AlertType.ERROR, "Fail" + e.getMessage());
//            msg_R2.showAndWait();
//        }
//    }
//
//    public void stop() throws Exception{
//        Connect_DB.close();
//    }
}