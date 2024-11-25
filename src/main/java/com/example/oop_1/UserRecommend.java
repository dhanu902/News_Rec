package com.example.oop_1;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import org.bson.Document;

public class UserRecommend {
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> collection;

    private String U_catogery;
    private String U_star;

    User user;

    public void initialize() {}

    void btnRecommend(ActionEvent actionEvent) {
        String u_name = user.getId();
    }
}
