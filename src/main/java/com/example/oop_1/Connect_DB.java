package com.example.oop_1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.logging.Logger;
import com.mongodb.ConnectionString;
import org.bson.Document;

public class Connect_DB {
    private static volatile MongoClient mongoClient;
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "UserDB";

    // Singleton pattern for MongoClient
    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            synchronized (Connect_DB.class) {
                if (mongoClient == null) {
                    LOGGER.info("Connecting to MongoDB...");
                    mongoClient = MongoClients.create(CONNECTION_STRING);
                }
            }
        }
        mongoClient.getDatabase(DATABASE_NAME).runCommand(new Document("ping", 1));
        System.out.println("ok");

        LOGGER.info("Connected to MongoDB database: " + DATABASE_NAME);
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null; // Ensure re-initialization if needed
        }
    }

    private static final Logger LOGGER = Logger.getLogger(Connect_DB.class.getName());
}