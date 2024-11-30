package com.example.oop_1;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PreferencesService {
    private static final Logger logger = LoggerFactory.getLogger(PreferencesService.class);
    private final MongoDatabase database;

    public PreferencesService(MongoDatabase database) {
        this.database = database;
    }

    public void saveRatingForArticle(ObjectId userId, int articleId, int rating) {
        MongoCollection<Document> preferencesCollection = database.getCollection("User preferences");
        MongoCollection<Document> newsArticlesCollection = database.getCollection("News_Articles");

        try {
            // Find the article to get its category
            Document article = newsArticlesCollection.find(Filters.eq("Article_id", articleId)).first();
            if (article == null) {
                logger.warn("Article not found with ID: {}", articleId);
                alert("Article not found with ID: " + articleId);
                return;
            }

            String category = article.getString("Category");

            // Fetch or create the preferences document for the user
            Document preferences = preferencesCollection.find(Filters.eq("_id", userId)).first();
            if (preferences == null) {
                preferences = new Document("_id", userId);
                preferencesCollection.insertOne(preferences);
            }

            Document categories = preferences.get("categories", Document.class);
            if (categories == null) {
                categories = new Document();
            }

            // Update category rating
            Double currentRating = categories.getDouble(category);
            if (currentRating == null) {
                currentRating = 0.0;
            }
            double updatedRating = (currentRating + rating) / 2;

            categories.put(category, updatedRating);
            preferences.put("categories", categories);

            // Update rated articles
            List<Document> ratedArticles = preferences.getList("rated_articles", Document.class);
            if (ratedArticles == null) {
                ratedArticles = new ArrayList<>();
            }

            boolean articleAlreadyRated = false;
            for (Document ratedArticle : ratedArticles) {
                if (ratedArticle.getInteger("Article_id").equals(articleId)) {
                    ratedArticle.put("rating", rating); // Update rating
                    articleAlreadyRated = true;
                    break;
                }
            }

            if (!articleAlreadyRated) {
                ratedArticles.add(new Document("User_ID", userId).append("Article_id", articleId).append("rating", rating).append("category", category));
            }
            preferences.put("rated_articles", ratedArticles);

            // Update preferences in DB
            preferencesCollection.updateOne(
                    Filters.eq("_id", userId),
                    new Document("$set", preferences)
            );

            logger.info("Ratings were saved successfully for user ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error while saving ratings", e);
            alert("Error while saving ratings");
        }
    }

    private void alert(String message) {
        WindowChangeAction.showAlert(message);
    }
}

