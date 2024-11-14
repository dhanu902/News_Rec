package com.example.oop_1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*public class NewsFetchAPI {
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY;

    public static String fetchNews() throws Exception {
        URL url = new URL(NEWS_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return content.toString();
    }
}
// NewsFetcher.java
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NewsFetcher {
    public List<Article> fetchArticles() throws IOException {
        String apiUrl = "YOUR_NEWS_API_ENDPOINT";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Parse JSON response
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        List<Article> articles = new Gson().fromJson(reader, new TypeToken<List<Article>>(){}.getType());
        reader.close();

        return articles;
    }
}*/