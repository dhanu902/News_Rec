package com.example.oop_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class Home_News {
    @FXML
    private Button btnBack_H;
    @FXML
    private Button btnRec;

    @FXML
    private ListView<String> articleListView;
    @FXML
    private Label titleLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label urlLabel;
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
            titles.add(article.getHeadline());
        }

        articleListView.setItems(titles);

        // Add listener to update details when an item is selected
        articleListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            if (newIndex != null && newIndex.intValue() >= 0) {
                NewsArticle selectedArticle = newsArticles.get(newIndex.intValue());
                titleLabel.setText(selectedArticle.getHeadline());
                categoryLabel.setText(selectedArticle.getCategory());
                descriptionLabel.setText(selectedArticle.getShort_description());
                authorLabel.setText(selectedArticle.getAuthors());
                urlLabel.setText(selectedArticle.getUrl());
                publishedAtLabel.setText(selectedArticle.getDate());
            }
        });
    }
    @FXML
    void btnBack_H_Clicked(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            WindowChangeAction.closeCurrentWindow(sourceNode);
            WindowChangeAction.showNewStage("Log-view.fxml", "Login");
        } catch (IOException ex) {
            WindowChangeAction.showAlert("Loading Failure");
        }
    }

    @FXML
    void btnRec_Clicked(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            WindowChangeAction.closeCurrentWindow(sourceNode);
            WindowChangeAction.showNewStage("Recommendation-view.fxml", "Recommendations for x");
        } catch (IOException ex) {
            WindowChangeAction.showAlert("Loading Failure");
        }
    }


    @FXML
    private HBox ratingBox;
    @FXML
    private Button btnStar1;
    @FXML
    private Button btnStar2;
    @FXML
    private Button btnStar3;
    @FXML
    private Button btnStar4;
    @FXML
    private Button btnStar5;

    @FXML
    private void handleStarClick(javafx.event.ActionEvent event) {
        Button clickedStar = (Button) event.getSource();
        int rating = ratingBox.getChildren().indexOf(clickedStar) + 1;
        for (int i = 0; i < ratingBox.getChildren().size(); i++) {
            Button starButton = (Button) ratingBox.getChildren().get(i);
            starButton.setText(i < rating ? "★" : "☆");
        }
    }
}