package com.example.oop_1;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WindowChangeAction {

    public static void closeCurrentWindow(Node sourceNode) {
        sourceNode.getScene().getWindow().hide();
    }

    public static void showNewStage(String fxmlPath, String title) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(WindowChangeAction.class.getResource(fxmlPath)));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}