package com.example.oop_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;

public class Recommendation {
    @FXML
    private Button btnHome;

    @FXML
    void btnHome_Clicked(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            WindowChangeAction.closeCurrentWindow(sourceNode);
            WindowChangeAction.showNewStage("Home-view.fxml", "user_name");
        } catch (IOException ex) {
            WindowChangeAction.showAlert("Loading Failure");
        }
    }
}
