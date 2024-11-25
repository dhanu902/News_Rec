package com.example.oop_1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.bson.Document;

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

            starButton.setOnAction(e -> SubmitRating(rating));
        }
    }

    private void SubmitRating(int rating) {
        return;
    }

    private String U_catogery;
    private String U_star;

    User user;

    void btnRecommend(ActionEvent actionEvent) {
        String u_name = user.getUsername();


    }

//    private void displayArticleDetails(String selectedHeadline) {
//        MongoCollection<Document> articlesCollection = database.getCollection("articles");
//        Document article = articlesCollection.find(new Document("headline", selectedHeadline)).first();
//
//        if (article != null) {
//            selectedArticleId = article.getString("_id"); // Save article ID for rating submission
//            String details = String.format("Headline: %s\nCategory: %s\nDate: %s\nDescription: %s",
//                    article.getString("headline"),
//                    article.getString("category"),
//                    article.getString("date"),
//
//                    article.getString("short_description"));
//
//            articleDetailsLabel.setText(details);
//        }
//
//        // Reset stars for the new article
//        resetStars();
//    }
//
//    private void submitRating(int rating) {
//        if (selectedArticleId == null) {
//            System.out.println("No article selected for rating.");
//            return;
//        }
//
//        // Save the rating in MongoDB
//        MongoCollection<Document> ratingsCollection = database.getCollection("user_ratings");
//        Document ratingDoc = new Document("user_id", "user123")
//                .append("article_id", selectedArticleId)
//                .append("rating", rating)
//                .append("rated_on", new java.util.Date());
//
//        ratingsCollection.insertOne(ratingDoc);
//        System.out.println("Rating submitted: " + rating);
//    }
//
//    private void resetStars() {
//        // Reset stars to unselected state
//        for (Button star : stars) {
//            star.setText("☆");
//        }
//    }



//    User user;
//    public void setName(String name){
//        user.getUsername() =  name;
//    }
}