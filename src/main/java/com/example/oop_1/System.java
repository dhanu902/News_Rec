/*package com.example.oop_1;

import com.google.gson.Gson;

import java.util.List;

public class System {

    public static class NewsResponse {
        private String status;
        private int total;
        private List<Article> articles;

        public List<Article> getArticles() {return articles;}
    }

    public static class GetNews {
        private NewsFetchAPI newsFetch = new NewsFetchAPI();

        public List<Article> getArticles() throws Exception {
            String jsonResponse = newsFetch.fetchNews();
            Gson gson = new Gson();
            NewsResponse newsResponse = gson.fromJson(jsonResponse, NewsResponse.class);
            return newsResponse.getArticles();
        }
    }
}
*/