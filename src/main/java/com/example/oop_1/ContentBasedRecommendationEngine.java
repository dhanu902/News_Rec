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

            // Extract IDs of rated articles
            List<Integer> ratedArticleIds = ratedArticles.stream()
                    .map(article -> article.getInteger("Article_id"))
                    .toList() ; new ArrayList<>();

            if (ratedArticles == null || ratedArticles.isEmpty()) {
                System.out.println("No rated articles found for user: " + userId);
                Platform.runLater(() -> {
                    articlesListView.getItems().clear();
                    articlesListView.getItems().add("No recommendations available for this user.");
                });
                return;
            }

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

            List<Map.Entry<String, Double>> sortedCategories = categories.entrySet().stream()
                    .map(entry -> Map.entry(entry.getKey(), (Double) entry.getValue()))
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Sort by preference score
                    .toList();

            System.out.println("Sorted Categories by Preference: " + sortedCategories);

            List<Document> recommendedArticles = new ArrayList<>();
            int maxRecommendations = 15; // Total number of recommendations
            int articlesPerCategory = 5; // Max articles per category

            for (Map.Entry<String, Double> categoryEntry : sortedCategories) {
                if (recommendedArticles.size() >= maxRecommendations) break;

                String category = categoryEntry.getKey();
                List<Document> articles = articlesCollection.find(
                        new Document("Category", category)
                                .append("Article_id", new Document("$nin", ratedArticleIds))
                ).limit(articlesPerCategory).into(new ArrayList<>());

                recommendedArticles.addAll(articles);
            }

            // Display recommendations
            if (recommendedArticles.isEmpty()) {
                System.out.println("No articles found for preferred categories.");
                Platform.runLater(() -> {
                    articlesListView.getItems().clear();
                    articlesListView.getItems().add("No recommendations available for the selected categories.");
                });
                return;
            }

            System.out.println("Final Recommended Articles: " + recommendedArticles);

            Platform.runLater(() -> {
                articlesListView.getItems().clear();
                for (Document article : recommendedArticles) {
                    articlesListView.getItems().add(article.getString("Headline")); // Adjust based on your schema
                }
            });
        });
    }
}