package com.example.oop_1;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NdjsonToCsvConverter {

    public static void main(String[] args) {
        // File paths
        String jsonFilePath = "News_Category_Dataset_v3.json"; // Input NDJSON file
        String csvFilePath = "Book1.csv";    // Output CSV file

        // Read JSON and write to CSV
        List<NewsArticle> articles = readNdjsonFile(jsonFilePath);
        writeCsvFile(articles, csvFilePath);
    }

    // Method to read NDJSON file and parse it into a list of Article objects
    public static List<NewsArticle> readNdjsonFile(String filePath) {
        List<NewsArticle> articles = new ArrayList<>();
        Gson gson = new Gson();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse each line as a JSON object
                NewsArticle article = gson.fromJson(line, NewsArticle.class);
                articles.add(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return articles;
    }

    // Method to write a list of Article objects to a CSV file
    public static void writeCsvFile(List<NewsArticle> articles, String csvFilePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {

            // Write header row
            String[] header = {"Link", "Headline", "Category", "Description", "Author", "Date"};
            writer.writeNext(header);

            // Write article data
            for (NewsArticle article : articles) {
                String[] row = {
                        article.getUrl(),
                        article.getHeadline(),
                        article.getCategory(),
                        article.getShort_description(),
                        article.getAuthors(),
                        article.getDate()
                };
                writer.writeNext(row);
            }

            System.out.println("CSV file written successfully to: " + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}