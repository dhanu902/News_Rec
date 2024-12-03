package com.example.oop_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//
//class ConcurrencyTest {
//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//        for (int i = 0; i < 100; i++) { // Simulate 100 users
//            final int userId = i;
//            executorService.submit(() -> {
//                ContentBasedRecommendationEngine service = new ContentBasedRecommendationEngine();
//
//                service.fetchRecommendations("user" + userId);
//                System.out.println("Recommendations fetched for user: " + userId);
//            });
//        }
//
//        executorService.shutdown();
//    }
//}