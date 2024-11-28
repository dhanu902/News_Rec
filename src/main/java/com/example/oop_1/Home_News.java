package com.example.oop_1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Home_News {
    private static final Logger logger = LoggerFactory.getLogger(Home_News.class);

    private MongoDatabase database;
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

    private List<NewsArticle> newsArticles;

    public void initialize() {
        database = Connect_DB.getDatabase();
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

    private PreferencesService preferencesService;

    public void setUser(User user) {
        this.user_name = user;
    }

    public void setDatabase(MongoDatabase database) {
        this.preferencesService = new PreferencesService(database);
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

//    void saveRatingForArticle(int articleId, int rating) {
//        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
//        MongoDatabase database = mongoClient.getDatabase("UserDB");
//
//        logger.debug("Database connection: {}", "Initialized");
//
//        MongoCollection<Document> Users_Collection = database.getCollection("Users");
//        MongoCollection<Document> NewsArticles_Collection = database.getCollection("News_Articles");
//
//        try {
//            // Find the article to get its category
//            Document article = NewsArticles_Collection.find(Filters.eq("id", articleId)).first();
//            if (article == null) {
//                logger.warn("Article not found with ID: {}", articleId);
//                Alert alert = new Alert(Alert.AlertType.ERROR, "Article not found");
//                alert.showAndWait();
//                return;
//            }
//
//            logger.info("Found article with ID: {}", articleId);
//
//            String category = article.getString("Category");
//
//            // Update user preferences
//            Document user = Users_Collection.find(Filters.eq("U_Name", user_name.getUsername())).first();
//
//            //logger.debug("Saving rating for article ID: {}, User: {}", articleId, user_name.getUsername());
//
//            if (user != null) {
//                Document preferences = user.get("U_Preferences", Document.class);
//
//                if (preferences == null){
//                    preferences = new Document();
//                }
//
//                Document categories = preferences.get("categories", Document.class);
//                if (categories == null) {
//                    categories = new Document();
//                }
//
//                // Update category rating (simple average calculation)
//                Double currentRating = categories.getDouble(category);
//                if (currentRating == null) {
//                    currentRating = 0.0;
//                }
//                double updatedRating = (currentRating + rating) / 2;
//
//                categories.put(category, updatedRating);
//                preferences.put("categories", categories);
//
//                // Update rated articles
//                List<Document> ratedArticles = preferences.getList("rated_articles", Document.class);
//                if (ratedArticles == null) {
//                    ratedArticles = new ArrayList<>();
//                }
//
//                boolean articleAlreadyRated = false;
//                for (Document ratedArticle : ratedArticles) {
//                    if (ratedArticle.getInteger("article_id").equals(articleId)) {
//                        ratedArticle.put("rating", rating); // Update rating
//                        articleAlreadyRated = true;
//                        break;
//                    }
//                }
//
//                if (!articleAlreadyRated) {
//                    ratedArticles.add(new Document("article_id", articleId).append("rating", rating));
//                }
//                preferences.put("rated_articles", ratedArticles);
//
//                // Update user in DB
//                Users_Collection.updateOne(
//                        Filters.eq("id", user.getObjectId("_id")),
//                        Updates.set("U_Preferences", preferences)
//                );
//
//                logger.info("Ratings were saved successfully");
//                System.out.println("Pr"+preferences.toJson());
//            }
//        } catch (Exception e) {
//            logger.error("Error", e);
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Error while saving ratings");
//            alert.showAndWait();
//        }
//    }

    private void SubmitRatingNumb(int rating) {
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
            Node sourceNode = (Node) event.getSource();
            WindowChangeAction.closeCurrentWindow(sourceNode);
            WindowChangeAction.showNewStage("Recommendation-view.fxml", "Recommendations for x");
        } catch (IOException ex) {
            WindowChangeAction.showAlert("Loading Failure");
        }
    }
}