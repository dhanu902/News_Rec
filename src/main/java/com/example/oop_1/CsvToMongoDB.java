package com.example.oop_1;
import com.mongodb.client.*;
import com.opencsv.CSVReader;
import org.bson.Document;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvToMongoDB {

    private static final System.Logger logger = System.getLogger(CsvToMongoDB.class.getName());

    public static void main(String[] args) {
        // MongoDB connection details
        String mongoUri = "mongodb://localhost:27017"; // Adjust as needed
        String databaseName = "UserDB";
        String collectionName = "News_Articles";

        // CSV file path
        String csvFilePath = "Book1.csv";

        try {
            // Step 1: Read CSV file
            List<Document> documents = readCsvToDocuments(csvFilePath);

            // Step 2: Save documents to MongoDB
            saveToMongoDB(mongoUri, databaseName, collectionName, documents);

            logger.log(System.Logger.Level.INFO, "CSV data successfully saved to MongoDB!");
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "An error occurred while processing the CSV file or saving to MongoDB.", e);
        }
    }

    // Read CSV and convert to a list of MongoDB Documents
    public static List<Document> readCsvToDocuments(String filePath) throws Exception {
        List<Document> documents = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext(); // Read the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                Document doc = new Document();
                for (int i = 0; i < headers.length; i++) {
                    doc.append(headers[i], line[i]); // Add key-value pairs to the document
                }
                documents.add(doc);
            }
        }

        return documents;
    }

    // Save a list of documents to MongoDB
    public static void saveToMongoDB(String uri, String dbName, String collectionName, List<Document> documents) {
        try (var mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Insert all documents into the collection
            collection.insertMany(documents);
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "An error occurred while connecting to MongoDB or inserting documents.", e);
        }
    }
}