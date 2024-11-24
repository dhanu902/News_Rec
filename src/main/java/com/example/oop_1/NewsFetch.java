package com.example.oop_1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class NewsFetch {
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
            e.printStackTrace();
        }
        return articles;
    }
}




/*public class NewsFetchAPI {
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY;

    public static String fetchNews() throws Exception {
        URL = new URL(NEWS_API_URL);
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



public class NewsFetchAPI {
    private static final String API_KEY = "a7cb34177ea243f6b005d4321cb942fb";
    private static final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=" + API_KEY;
    //private static final java.net.URL URL = ;

    public List<NewsArticle> fetchArticles() throws IOException {
        //String apiUrl = "YOUR_NEWS_API_ENDPOINT";
        URL = new URL(NEWS_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Parse JSON response
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        List<NewsArticle> articles = new Gson().fromJson(reader, new TypeToken<List<NewsArticle>>(){}.getType());
        reader.close();

        return articles;
    }
}*/
