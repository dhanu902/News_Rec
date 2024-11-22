package com.example.oop_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Home {
    @FXML
    private Button btnBack_H;
    @FXML
    private Button btnBack_Rc;
    @FXML
    private Button btnRec;
    @FXML
    private Pane paneHome;

    @FXML
    private ListView<String> articleListView;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label publishedAtLabel;

    private List<NewsArticle> newsArticles;

    public void initialize() {
        loadArticles();
    }

    private void loadArticles() {
        NewsFetch newsFetch = new NewsFetch();
        newsArticles = newsFetch.readArticle("C:\\Users\\HI\\IdeaProjects\\OOP_1\\Book1.csv"); // Replace with your JSON file path

        ObservableList<String> titles = FXCollections.observableArrayList();
        for (NewsArticle article : newsArticles) {
            titles.add(article.getTitle());
        }

        articleListView.setItems(titles);

        // Add listener to update details when an item is selected
        articleListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            if (newIndex != null && newIndex.intValue() >= 0) {
                NewsArticle selectedArticle = newsArticles.get(newIndex.intValue());
                titleLabel.setText(selectedArticle.getTitle());
                descriptionLabel.setText(selectedArticle.getDescription());
                publishedAtLabel.setText(selectedArticle.getCategory());
            }
        });
    }
    @FXML
    void btnBack_H_Clicked(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage ownerStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Scene scene = new Scene(root);
            ownerStage.setTitle("Home");
            ownerStage.setScene(scene);
            ownerStage.show();
        } catch (IOException ex) {
            Alert message = new Alert(Alert.AlertType.ERROR, "Loading Failure");
            message.showAndWait();
        }
    }
}
