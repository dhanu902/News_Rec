package com.example.oop_1;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.*;


public class Recommendation {

    User user;


//    public static void exportPreferencesToCSV(MongoCollection<Document> usersCollection, String outputPath) {
//        try (FileWriter writer = new FileWriter(outputPath)) {
//            // Write CSV header
//            writer.append("user_id,category,rating\n");
//
//            // Fetch all user documents
//            FindIterable<Document> users = usersCollection.find();
//
//            for (Document user : users) {
//                // Get user ID
//                String userId = user.getObjectId("_id").toString();
//
//                // Get user preferences (categories)
//                Document preferences = user.get("categories", Document.class);
//
//                if (preferences != null) {
//                    // Iterate over categories and write to CSV
//                    for (String category : preferences.keySet()) {
//                        Double rating = preferences.getDouble(category);
//                        writer.append(String.format("%s,%s,%.2f\n", userId, category, rating));
//                    }
//                }
//            }
//
//            System.out.println("Preferences exported successfully to " + outputPath);
//
//        } catch (IOException e) {
//            System.err.println("Error while writing CSV: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        // Connect to MongoDB and get the Users collection
//        MongoCollection<Document> usersCollection = Connect_DB.getDatabase().getCollection("User preferences");
//
//        // Specify output CSV file path
//        String outputPath = "user_preferences.csv";
//
//        // Export preferences to CSV
//        exportPreferencesToCSV(usersCollection, outputPath);
//    }
//
//    public static class CategoryRanking {
//
//        public static void rankCategories(String csvFilePath) {
//            Map<String, Double> categoryRatings = new HashMap<>();
//            Map<String, Integer> categoryCounts = new HashMap<>();
//
//            try (CSVParser parser = new CSVParser(new FileReader(csvFilePath), CSVFormat.DEFAULT
//                    .withHeader("user_id", "category", "rating").withSkipHeaderRecord())) {
//
//                // Read each record
//                for (CSVRecord record : parser) {
//                    String category = record.get("category");
//                    double rating = Double.parseDouble(record.get("rating"));
//
//                    // Aggregate ratings and counts
//                    categoryRatings.put(category, categoryRatings.getOrDefault(category, 0.0) + rating);
//                    categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
//                }
//
//                // Calculate average ratings
//                Map<String, Double> averageRatings = categoryRatings.entrySet()
//                        .stream()
//                        .collect(Collectors.toMap(
//                                Map.Entry::getKey,
//                                e -> e.getValue() / categoryCounts.get(e.getKey())
//                        ));
//
//                // Sort categories by average rating in descending order
//                averageRatings.entrySet().stream()
//                        .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
//                        .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
//
//            } catch (IOException e) {
//                System.err.println("Error reading the CSV file: " + e.getMessage());
//            }
//        }
//
//        public static void main(String[] args) {
//            // Path to the exported preferences CSV file
//            String csvFilePath = "user_preferences.csv";
//
//            // Rank categories
//            rankCategories(csvFilePath);
//        }
//    }
//
//    public class ArticleDisplay {
//
//        // Sample function to fetch articles by category (replace with DB or JSON fetch logic)
//        public static List<String> fetchArticlesByCategory(String category) {
//            // Replace this with actual database or JSON fetch logic
//            List<String> articles = new ArrayList<>();
//            switch (category) {
//                case "U.S. NEWS":
//                    articles.add("Breaking News in U.S.");
//                    articles.add("Economic Update in the U.S.");
//                    break;
//                case "COMEDY":
//                    articles.add("Top 10 Comedians of the Year");
//                    articles.add("Funniest Moments in 2024");
//                    break;
//                case "CULTURE & ARTS":
//                    articles.add("Modern Art Trends");
//                    articles.add("Cultural Highlights of the Month");
//                    break;
//                case "PARENTING":
//                    articles.add("Parenting Tips for New Moms");
//                    articles.add("Balancing Work and Parenting");
//                    break;
//                case "WORLD NEWS":
//                    articles.add("Global Economic Trends");
//                    articles.add("World Politics Overview");
//                    break;
//                default:
//                    break;
//            }
//            return articles;
//        }
//
//        // Function to update the ListView based on ranked categories
//        public static void displayArticles(List<String> rankedCategories, ListView<String> listView) {
//            ObservableList<String> articleTitles = FXCollections.observableArrayList();
//
//            for (String category : rankedCategories) {
//                List<String> articles = fetchArticlesByCategory(category);
//                articleTitles.addAll(articles); // Add articles from each category
//            }
//
//            listView.setItems(articleTitles); // Update ListView with articles
//        }
//
//        public static void main(String[] args) {
//            // Example ranked categories (replace this with your actual ranked list)
//            List<String> rankedCategories = List.of("U.S. NEWS", "CULTURE & ARTS", "PARENTING", "COMEDY", "WORLD NEWS");
//
//            // Create a ListView for demonstration
//            ListView<String> listView = new ListView<>();
//
//            // Display articles based on ranked categories
//            displayArticles(rankedCategories, listView);
//
//            // Print ListView items (for testing, this would display the titles in the JavaFX UI)
//            listView.getItems().forEach(System.out::println);
//        }
//    }
}