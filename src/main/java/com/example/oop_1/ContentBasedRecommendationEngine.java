package com.example.oop_1;

import com.mongodb.client.MongoCollection;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ContentBasedRecommendationEngine implements RecommendationEngine {
    private final MongoCollection<Document> ratingsCollection;
    private final MongoCollection<Document> articlesCollection;
    private final ExecutorService executorService;
    private final ListView<String> articlesListView;

    public ContentBasedRecommendationEngine(MongoCollection<Document> ratingsCollection,
                                            MongoCollection<Document> articlesCollection,
                                            ExecutorService executorService,
                                            ListView<String> articlesListView) {
        this.ratingsCollection = ratingsCollection;
        this.articlesCollection = articlesCollection;
        this.executorService = executorService;
        this.articlesListView = articlesListView;
    }

    @Override
    public void fetchRecommendations(ObjectId userId) {
        executorService.submit(() -> {
            System.out.println("Fetching recommendations for user: " + userId);

            // Find the user's document
            Document userRatings = ratingsCollection.find(new Document("_id", userId)).first();

            if (userRatings == null) {
                System.out.println("No ratings found for user: " + userId);
                Platform.runLater(() -> {
                    articlesListView.getItems().clear();
                    articlesListView.getItems().add("No recommendations available for this user.");
                });
                return;
            }

            // Extract the rated_articles array
            List<Document> ratedArticles = (List<Document>) userRatings.get("rated_articles");
            if (ratedArticles == null || ratedArticles.isEmpty()) {
                System.out.println("No rated articles found for user: " + userId);
                Platform.runLater(() -> {
                    articlesListView.getItems().clear();
                    articlesListView.getItems().add("No recommendations available for this user.");
                });
                return;
            }

            // Extract IDs of rated articles
            List<Integer> ratedArticleIds = ratedArticles.stream()
                    .map(article -> article.getInteger("Article_id"))
                    .toList();

            // Extract user's preferred categories
            Document categories = (Document) userRatings.get("categories");
            if (categories == null || categories.isEmpty()) {
                System.out.println("No categories found for user: " + userId);
                Platform.runLater(() -> {
                    articlesListView.getItems().clear();
                    articlesListView.getItems().add("No category preferences found for this user.");
                });
                return;
            }

            // Sort categories by ratings in descending order and filter top-rated ones
            List<String> preferredCategories = categories.entrySet().stream()
                    .sorted((entry1, entry2) -> Double.compare((Double) entry2.getValue(), (Double) entry1.getValue())) // Sort by value (descending)
                    .limit(3) // Limit to top 3 categories (adjustable)
                    .map(Map.Entry::getKey)
                    .toList();

            if (preferredCategories.isEmpty()) {
                System.out.println("No top-rated categories for user: " + userId);
                Platform.runLater(() -> {
                    articlesListView.getItems().clear();
                    articlesListView.getItems().add("No top-rated categories found for this user.");
                });
                return;
            }

            System.out.println("Preferred categories for user: " + preferredCategories);

            // Fetch articles from preferred categories, excluding rated ones
            List<Document> recommendedArticles = articlesCollection.find(
                    new Document("Category", new Document("$in", preferredCategories)) // Match categories
                            .append("Article_id", new Document("$nin", ratedArticleIds)) // Exclude rated articles
            ).limit(15).into(new ArrayList<>()); // Limit recommendations to 5

            if (recommendedArticles.isEmpty()) {
                System.out.println("No articles found for preferred categories.");
                Platform.runLater(() -> {
                    articlesListView.getItems().clear();
                    articlesListView.getItems().add("No recommendations available for the selected categories.");
                });
                return;
            }

            // Update the ListView with recommendations
            Platform.runLater(() -> {
                articlesListView.getItems().clear();
                for (Document article : recommendedArticles) {
                    articlesListView.getItems().add(article.getString("Headline")); // Display the headline
                }
            });
        });
    }
}