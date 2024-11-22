package com.example.oop_1;

/*
    private void loadArticles() {
        try {
            List<NewsArticle> articles = newsFetcher.fetchArticles();
            ObservableList<String> titles = FXCollections.observableArrayList();
            for (NewsArticle article : articles) {
                titles.add(article.getTitle());
            }
            articleListView.setItems(titles);

            // Event listener to show details when an article is clicked
            articleListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                int selectedIndex = articleListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    NewsArticle selectedArticle = articles.get(selectedIndex);
                    titleLabel.setText(selectedArticle.getTitle());
                    descriptionLabel.setText(selectedArticle.getDescription());
                    publishedAtLabel.setText(selectedArticle.getPublishedAt());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
