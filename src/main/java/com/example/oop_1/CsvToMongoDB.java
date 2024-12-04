package com.example.oop_1;
import com.mongodb.client.*;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import com.opencsv.CSVReader;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvToMongoDB {

    private static final System.Logger logger = System.getLogger(CsvToMongoDB.class.getName());

    public static void main(String[] args) {
        // MongoDB connection details
        String mongoConnection = "mongodb://localhost:27017";
        String databaseName = "UserDB";
        String collectionName = "News_Articles";

        // CSV file path
        String csvFilePath = "Book1.csv";

        try {
            // Read CSV file
            List<Document> documents = readCSV_ToDocuments(csvFilePath);

            // Save documents to MongoDB
            saveToMongoDB(mongoConnection, databaseName, collectionName, documents);

            logger.log(System.Logger.Level.INFO, "CSV data successfully saved to MongoDB!");
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "An error occurred while processing the CSV file or saving to MongoDB.", e);
        }
    }

    // Read CSV and convert to a list of MongoDB Documents
    public static List<Document> readCSV_ToDocuments(String filePath) throws Exception {
        List<Document> documents = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] headers = reader.readNext(); // Read the header row
            String[] line;
            int id = 1;

            while ((line = reader.readNext()) != null) {
                Document doc = new Document();
                doc.append("Article_id", id++);
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


class ToMongoDB {
    private static final System.Logger logger = System.getLogger(ToMongoDB.class.getName());
    public static void main(String[] args) {
        // MongoDB connection URI
        String uri = "mongodb://localhost:27017";
        String databaseName = "AdminDB";
        String collectionName = "Admins";

        // CSV file path
        String csvFilePath = "Book_A.csv";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);

            // Read CSV and insert data into MongoDB
            List<WriteModel<Document>> writes = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
                String line;
                String[] headers = br.readLine().split(","); // Read header line

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    Document doc = new Document();
                    for (int i = 0; i < headers.length; i++) {
                        doc.append(headers[i].trim(), values[i].trim());
                    }
                    writes.add(new InsertOneModel<>(doc));
                }
            }

            // Insert all documents at once
            if (!writes.isEmpty()) {
                collection.bulkWrite(writes);
                System.out.println("CSV data successfully inserted into MongoDB!");
            } else {
                System.out.println("No data to insert.");
            }
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, e);
        }
    }
}