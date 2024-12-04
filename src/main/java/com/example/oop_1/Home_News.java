package com.example.oop_1;

import com.mongodb.client.MongoDatabase;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Home_News {
    private User user_name;
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

    private List<NewsArticle> newsArticles;

    private UserPreferences preferencesService;

    public void setUser(User user) {
        this.user_name = user;
    }

    public void setDatabase(MongoDatabase database) {
        this.preferencesService = new UserPreferences(database);
    }

    public void initialize() {
        MongoDatabase database = Connect_DB.getDatabase();
        loadArticles();
    }

    private void loadArticles() {
        NewsFetch newsFetch = new NewsFetch();
        newsArticles = newsFetch.readArticle("C:\\Users\\HI\\IdeaProjects\\OOP_1\\Book1.csv");

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

                resetStarBox();
            }
        });
    }

    @FXML
    private void handleStarClick(javafx.event.ActionEvent event) {
        Button clickedStar = (Button) event.getSource();
        int rating = ratingBox.getChildren().indexOf(clickedStar) + 1;
        for (int i = 0; i < ratingBox.getChildren().size(); i++) {
            Button starButton = (Button) ratingBox.getChildren().get(i);
            starButton.setText(i < rating ? "★" : "☆");
        }

        System.out.println("Rating clicked: " + rating);

        //starButton.setOnAction(e -> SubmitRatingNumb(rating));

        if (articleListView.getSelectionModel().getSelectedIndex() >= 0) {
            int articleId = articleListView.getSelectionModel().getSelectedIndex();
            preferencesService.saveRatingForArticle(user_name.getId(), articleId, rating);
        }

    }

    private void resetStarBox(){
        for (Node node : ratingBox.getChildren()) {
            if (node instanceof Button btnStar) {
                btnStar.setText("☆");
            }
        }
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
            ((Node) event.getSource()).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Recommendation-view.fxml"));
            Parent root = loader.load();
            UserRecommend controller = loader.getController();
            controller.setUserFor_R(user_name);
            Stage ownerStage = new Stage();
            Scene scene = new Scene(root);
            ownerStage.setTitle(user_name.getUsername() + "'s Recommendation");
            ownerStage.setScene(scene);
            ownerStage.show();
        } catch (IOException ex) {
            Alert message = new Alert(Alert.AlertType.ERROR, "Loading Failure");
            message.showAndWait();
        }
    }

    private void SubmitRatingNumb(int rating)
    {
//        logger.debug("Rating: {}", rating);
//        logger.debug("Article ID: {}", articleListView.getSelectionModel().getSelectedIndex() + 1);
//        logger.debug("User: {}", user_name.getUsername());
//        saveRatingForArticle(articleListView.getSelectionModel().getSelectedIndex() + 1, rating);
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Rating submitted successfully");
//        alert.showAndWait();
//        resetStarBox();
//        articleListView.getSelectionModel().clearSelection();
//        titleLabel.setText("");
//        categoryLabel.setText("");
//        descriptionLabel.setText("");
//        authorLabel.setText("");
//        urlLabel.setText("");
//        publishedAtLabel.setText("");
//        ratingBox.getChildren().clear();
//        articleListView.getItems().clear();
//        loadArticles();

        // Print the rating
         // or use logger.info("Rating clicked: " + rating);

        // Save the rating for the currently selected article
//        if (articleListView.getSelectionModel().getSelectedIndex() >= 0) {
//            saveRatingForArticle(articleListView.getSelectionModel().getSelectedIndex() + 1, rating);
//        }
    }
}