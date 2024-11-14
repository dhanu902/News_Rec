/*package com.example.oop_1;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Connect_DB {
    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        // Connect to MongoDB at localhost, using the database 'ssc_manager'
        mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
        return mongoClient.getDatabase("ssc_manager");
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}*/