package com.example.oop_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private Button btnStart;
    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogin_I;
    @FXML
    private Button btnRegister_I;
    @FXML
    private Button btnLogin_U;
    @FXML
    private Button btnLogin_A;
    @FXML
    private Button btnBack_L_AU;

    @FXML
    private Pane paneStart;
    @FXML
    private Pane paneLogin_I;
    @FXML
    private Pane paneLnR;

    @FXML
    public void btnClickStart(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnStart){
            paneLnR.toFront();
        }
    }

    @FXML
    public void btnClicks1(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnLogin_I) {
            paneLogin_I.toFront();
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

    @FXML
    public void btnClicks2(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnLogin_U){
            try{
                ((Node)actionEvent.getSource()).getScene().getWindow().hide();
                Stage ownerStage = new Stage();
                Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Log-view.fxml")));
                Scene scene = new Scene(root);
                ownerStage.setTitle("Login");
                ownerStage.setScene(scene);
                ownerStage.show();
            }catch (IOException ex){
                Alert message = new Alert(Alert.AlertType.ERROR, "Loading Failure");
                message.showAndWait();
            }
        }
        if (actionEvent.getSource() == btnLogin_A){
            try {
                Node sourceNode = (Node) actionEvent.getSource();
                WindowChangeAction.closeCurrentWindow(sourceNode);
                WindowChangeAction.showNewStage("LogAdmin-view.fxml", "Admin Login");
            } catch (IOException ex) {
                WindowChangeAction.showAlert("Loading Failure");
                logger.error(ex.getMessage());
            }
        }
        if (actionEvent.getSource() == btnBack_L_AU){
            paneStart.toFront();
        }
    }

    @FXML
    public void btnClickExit(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnExit){
            System.exit(0);
        }
    }
}