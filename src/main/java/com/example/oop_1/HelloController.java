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