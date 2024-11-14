package com.example.oop_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class HelloController {
    @FXML
    private Button btnLogin_I;
    @FXML
    private Button btnRegister_I;
    @FXML
    private Button btnLogin_F;
    @FXML
    private Button btnRegister_F;

    @FXML
    private Pane paneLnR;
    @FXML
    private Pane paneLogin;
    @FXML
    private Pane paneRegister;
    @FXML
    private Pane paneHome;


    @FXML
    public void btnClicks1(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnLogin_I) {
            paneLogin.toFront();
        }
        if (actionEvent.getSource() == btnRegister_I) {
            paneRegister.toFront();
        }
        if (actionEvent.getSource() == btnRegister_F) {
            paneHome.toFront();
        }
        if (actionEvent.getSource() == btnLogin_F) {
            paneHome.toFront();
        }
    }



}