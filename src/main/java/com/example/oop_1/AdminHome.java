package com.example.oop_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AdminHome {
    @FXML
    private Button btnBack_AdminHome;

    @FXML
    private SplitPane splitPane1;
    @FXML
    private SplitPane splitPane2;

    @FXML
    private Button btnMNG_Article;
    @FXML
    private Button btnHome;

    @FXML
    private Pane paneMenu;
    @FXML
    private Pane paneUpload;
    @FXML
    private Pane paneUpdate;
    @FXML
    private Pane paneDelete;

    @FXML
    private Button btnUpload;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUploadB;
    @FXML
    private Button btnUpdateB;
    @FXML
    private Button btnDeleteB;

    @FXML
    public void btnClicks1(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnMNG_Article){splitPane2.toFront();}
        if (actionEvent.getSource() == btnHome){splitPane1.toFront();}
    }

    @FXML
    public void btnClicks2(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnUpload){paneUpload.toFront();}
        if (actionEvent.getSource() == btnUpdate){paneUpdate.toFront();}
        if (actionEvent.getSource() == btnDelete){paneDelete.toFront();}
    }

    @FXML
    public void btnClicks3(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnUploadB){paneMenu.toFront();}
        if (actionEvent.getSource() == btnUpdateB){paneMenu.toFront();}
        if (actionEvent.getSource() == btnDeleteB){paneMenu.toFront();}
    }

    @FXML
    public void btnClicksBack(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnBack_AdminHome){
            try {
                Node sourceNode = (Node) actionEvent.getSource();
                WindowChangeAction.closeCurrentWindow(sourceNode);
                WindowChangeAction.showNewStage("hello-view.fxml", "Home");
            } catch (IOException ex) {
                WindowChangeAction.showAlert("Loading Failure");
            }
        }
    }
}