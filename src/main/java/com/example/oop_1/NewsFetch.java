package com.example.oop_1;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class NewsFetch {
    private static final Logger logger = LoggerFactory.getLogger(NewsFetch.class);

    public List<NewsArticle> readArticle(String filePath) {
        List<NewsArticle> articles = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // Skip header row
            while ((line = reader.readNext()) != null) {
                String link = line[0];
                String headline = line[1];
                String category = line[2];
                String shortDescription = line[3];
                String authors = line[4];
                String date = line[5];

                articles.add(new NewsArticle(link, headline, category, shortDescription, authors, date));
            }
        } catch (Exception e) {
            logger.error("Error while reading file: {}", String.valueOf(e));
        }
        return articles;
    }
}