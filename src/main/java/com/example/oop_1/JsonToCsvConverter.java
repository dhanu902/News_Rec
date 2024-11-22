package com.example.oop_1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.List;

public class JsonToCsvConverter {

    public static void main(String[] args) {
        // File paths
        String jsonFilePath = "News_Category_Dataset_v3.json";
        String csvFilePath = "Book1.csv";

        // Read JSON and write to CSV
        try {
            List<NewsArticle> articles = readJsonFile(jsonFilePath);
            writeCsvFile(articles, csvFilePath);
            System.out.println("JSON file converted to CSV successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read JSON file and parse it into a list of Article objects
    public static List<NewsArticle> readJsonFile(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<List<NewsArticle>>() {}.getType());
        }
    }

    // Method to write a list of Article objects to a CSV file
    public static void writeCsvFile(List<NewsArticle> articles, String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {

            System.out.println("Writing CSV file to " + filePath);
            System.out.println("Number of articles: " + articles.size());
            // Write header row
            String[] header = {"Link", "Headline", "Category", "Short Description", "Authors", "Date"};
            writer.writeNext(header);

            // Write article data
            for (NewsArticle article : articles) {
                String[] row = {
                        article.getUrl(),
                        article.getTitle(),
                        article.getCategory(),
                        article.getDescription(),
                        article.getAuthor(),
                        article.getPublishedAt()
                };
                writer.writeNext(row);
            }
        }
    }
}
