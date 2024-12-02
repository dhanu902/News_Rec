package com.example.oop_1;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class jsonToCSV {
    private static final Logger logger = LoggerFactory.getLogger(jsonToCSV.class);

    public static void main(String[] args) {
        // File paths
        String jsonFilePath = "News_Category_Dataset_v3.json"; // Input JSON file
        String csvFilePath = "Book1.csv";    // Output CSV file

        // Read JSON and write to CSV
        List<NewsArticle> articles = read_jsonFile(jsonFilePath);
        writeCSV_File(articles, csvFilePath);
    }

    // Method to read NDJSON file and parse it into a list of Article objects
    public static List<NewsArticle> read_jsonFile(String filePath) {
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
            logger.error(e.getMessage());
        }

        return articles;
    }

    // Method to write a list of Article objects to a CSV file
    public static void writeCSV_File(List<NewsArticle> articles, String csvFilePath) {
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
            logger.error(e.getMessage());
        }
    }
}