package com.example.oop_1;


import org.bson.types.ObjectId;

public interface RecommendationEngine {
    void fetchRecommendations(ObjectId userID);
}