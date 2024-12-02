package com.example.oop_1;

import com.mongodb.client.model.Filters;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.bson.Document;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRecommend {
    private User user;
    @FXML
    private Button btnBack_R;
    @FXML
    private Button btnFetch;
    @FXML
    private ListView<String> articleListView2;
    @FXML
    private TextArea TextAreaContent;

    private RecommendationEngine recommendationEngine;
    MongoDatabase database1;
    ExecutorService executorService1;

    public void setUserFor_R(User user) {
        this.user = user;
        System.out.println("setUserFor_R() is called. User ID: " + user.getId());
    }

    public void initialize() {
        database1 = MongoClients.create("mongodb://localhost:27017").getDatabase("UserDB");
        executorService1 = Executors.newFixedThreadPool(4);

        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("UserDB");
        MongoCollection<Document> ratingsCollection = database.getCollection("User preferences");
        MongoCollection<Document> articlesCollection = database.getCollection("News_Articles");
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        recommendationEngine = new ContentBasedRecommendationEngine(
                ratingsCollection,
                articlesCollection,
                executorService,
                articleListView2
        );
    }

    private void displayArticleDetails(String headline) {
        executorService1.submit(() -> {
            try {
                MongoCollection<Document> articlesCollection = database1.getCollection("News_Articles");
                Document article = articlesCollection.find(Filters.eq("Headline", headline)).first();
                if (article != null) {
                    String details = String.format("Link: %s\n Headline: %s\nCategory: %s\nDescription: %s\n Author: %s\n Date:%s",
                            article.getString("Link"),
                            article.getString("Headline"),
                            article.getString("Category"),
                            article.getString("Description"),
                            article.getString("Author"),
                            article.getString("Date"));
                    Platform.runLater(() -> TextAreaContent.setText(details));
                }
            } catch (Exception e) {
                Platform.runLater(() -> TextAreaContent.setText("Error loading article details: " + e.getMessage()));
            }
        });
    }

    @FXML
    void btnFetch_Clicked(ActionEvent actionEvent) {
        if (user == null){
            Alert message = new Alert(Alert.AlertType.ERROR, "User not found");
            message.showAndWait();
            return;
        }
        recommendationEngine.fetchRecommendations(user.getId());

        articleListView2.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                if (newValue != null) displayArticleDetails(newValue);
            });
    }

    @FXML
    void btnBack_R_Clicked(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            WindowChangeAction.closeCurrentWindow(sourceNode);
            WindowChangeAction.showNewStage("Home-view.fxml", user.getUsername() + "'s Home");
        } catch (IOException ex) {
            WindowChangeAction.showAlert("Loading Failure");
        }
    }
}



//
//    private void loadArticles() {
//        executorService.submit(() -> {
//            try {
//                MongoCollection<Document> articlesCollection = database.getCollection("News_Articles");
//                List<Document> articles = articlesCollection.find().into(new ArrayList<>());
//                Platform.runLater(() -> {
//                    articleListView2.getItems().clear();
//                    for (Document article : articles) {
//                        articleListView2.getItems().add(article.getString("Headline"));
//                    }
//                });
//            } catch (Exception e) {
//                Platform.runLater(() -> articleListView2.getItems().add("Error loading articles"));
//                Alert message = new Alert(Alert.AlertType.ERROR, "Error loading articles" + e.getMessage());
//                message.showAndWait();
//                throw new RuntimeException(e);
//            }
//        });
//    }
//

//
//


//    public void initialize() {
//        try {
//            database = MongoClients.create("mongodb://localhost:27017").getDatabase("UserDB");
//            executorService = Executors.newFixedThreadPool(4);
//            loadArticles();
//
//]           if (user != null) {
//                btnFetch.setOnAction(event -> fetchCollaborativeRecommendations(this.user.getId()));
//            }
//
//            articleListView2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//                if (newValue != null) displayArticleDetails(newValue);
//            });
//        } catch (Exception e) {
//            Alert message = new Alert(Alert.AlertType.ERROR, "Error initializing: " + e.getMessage());
//            message.showAndWait();
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//
//    private void fetchCollaborativeRecommendations(ObjectId userId) {
//        executorService.submit(() -> {
//            try {
//                MongoCollection<Document> preferencesCollection = database.getCollection("User preferences");
//                Document userDoc = preferencesCollection.find(Filters.eq("_id", userId)).first();
//
//                if (userDoc != null) {
//                    List<Integer> ratedArticleIds = userDoc.getList("rated_articles", Document.class).stream()
//                            .map(doc -> doc.getInteger("Article_id"))
//                            .toList();
//
//                    List<Document> recommendedArticles = preferencesCollection.aggregate(List.of(
//                            new Document("$unwind", "$rated_articles"),
//                            new Document("$match", new Document("rated_articles.Article_id", new Document("$nin", ratedArticleIds))),
//                            new Document("$group", new Document("_id", "$rated_articles.Article_id")
//                                    .append("avg_rating", new Document("$avg", "$rated_articles.rating"))),
//                            new Document("$sort", new Document("avg_rating", -1)),
//                            new Document("$limit", 5)
//                    )).into(new ArrayList<>());
//
//                    MongoCollection<Document> articlesCollection = database.getCollection("News_Articles");
//                    List<Document> articles = new ArrayList<>();
//                    for (Document rec : recommendedArticles) {
//                        Document article = articlesCollection.find(Filters.eq("Article_id", rec.get("_id"))).first();
//                        if (article != null) {
//                            articles.add(article.append("avg_rating", rec.get("avg_rating")));
//                        }
//                    }
//
//                    Platform.runLater(() -> {
//                        articleListView2.getItems().clear();
//                        for (Document article : articles) {
//                            String headline = String.format("%s (avg rating: %.2f)",
//                                    article.getString("Headline"),
//                                    article.getDouble("avg_rating"));
//                            articleListView2.getItems().add(headline);
//                        }
//                    });
//                }
//            } catch (Exception e) {
//                Platform.runLater(() -> articleListView2.getItems().add("Error fetching recommendations: " + e.getMessage()));
//            }
//        });
//    }