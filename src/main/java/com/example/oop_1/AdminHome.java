package com.example.oop_1;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AdminHome {
    private static final Logger logger = LoggerFactory.getLogger(AdminHome.class);
    MongoDatabase database;
    @FXML
    private Button btnBack_AdminHome;

    @FXML
    private SplitPane splitPane1;
    @FXML
    private SplitPane splitPane2;

    @FXML
    private Button btnMNG_Article;
    @FXML
    private Button btnHome;

    @FXML
    private Pane paneMenu;
    @FXML
    private Pane paneUpload;
    @FXML
    private Pane paneUpdate;
    @FXML
    private Pane paneDelete;
    @FXML
    private Pane paneTab;

    @FXML
    private Button btnUpload;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnTab;

    @FXML
    private Button btnUploadB;
    @FXML
    private Button btnUpdateB;
    @FXML
    private Button btnDeleteB;
    @FXML
    private Button btnTabB;

    @FXML
    private TableView<NewsArticle> A_NewsArticleData;
    @FXML
    private TableColumn<NewsArticle, Integer> A_NewsArticle_ID;
    @FXML
    private TableColumn<NewsArticle, String> A_NewsArticle_Url;
    @FXML
    private TableColumn<NewsArticle, String> A_NewsArticle_Headline;
    @FXML
    private TableColumn<NewsArticle, String> A_NewsArticle_Category;
    @FXML
    private TableColumn<NewsArticle, String> A_NewsArticle_Short_Description;
    @FXML
    private TableColumn<NewsArticle, String> A_NewsArticle_Author;
    @FXML
    private TableColumn<NewsArticle, String> A_NewsArticle_Date;

    @FXML
    private TableView<NewsArticle> D_NewsArticleData;
    @FXML
    private TableColumn<NewsArticle, Integer> D_NewsArticle_ID;
    @FXML
    private TableColumn<NewsArticle, String> D_NewsArticle_Url;
    @FXML
    private TableColumn<NewsArticle, String> D_NewsArticle_Headline;
    @FXML
    private TableColumn<NewsArticle, String> D_NewsArticle_Category;
    @FXML
    private TableColumn<NewsArticle, String> D_NewsArticle_Short_Description;
    @FXML
    private TableColumn<NewsArticle, String> D_NewsArticle_Author;
    @FXML
    private TableColumn<NewsArticle, String> D_NewsArticle_Date;

    @FXML
    private TextField text_D_search;

    @FXML
    private TextField text_urlA;
    @FXML
    private TextField text_titleA;
    @FXML
    private TextField text_categoryA;
    @FXML
    private TextField text_shortDescriptionA;
    @FXML
    private TextField text_authorA;
    @FXML
    private TextField text_dateA;

    @FXML
    private TextField text_idU;
    @FXML
    private TextField text_urlU;
    @FXML
    private TextField text_titleU;
    @FXML
    private TextField text_categoryU;
    @FXML
    private TextField text_shortDescriptionU;
    @FXML
    private TextField text_authorU;
    @FXML
    private TextField text_dateU;
    @FXML
    private TextField text_U_search;

    private final ObservableList<NewsArticle> newsArticleData = FXCollections.observableArrayList();

    public void initialize() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.database = mongoClient.getDatabase("UserDB");

        A_NewsArticle_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        A_NewsArticle_Url.setCellValueFactory(new PropertyValueFactory<>("Url"));
        A_NewsArticle_Headline.setCellValueFactory(new PropertyValueFactory<>("headline"));
        A_NewsArticle_Category.setCellValueFactory(new PropertyValueFactory<>("category"));
        A_NewsArticle_Short_Description.setCellValueFactory(new PropertyValueFactory<>("short_description"));
        A_NewsArticle_Author.setCellValueFactory(new PropertyValueFactory<>("authors"));
        A_NewsArticle_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        D_NewsArticle_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        D_NewsArticle_Url.setCellValueFactory(new PropertyValueFactory<>("Url"));
        D_NewsArticle_Headline.setCellValueFactory(new PropertyValueFactory<>("headline"));
        D_NewsArticle_Category.setCellValueFactory(new PropertyValueFactory<>("category"));
        D_NewsArticle_Short_Description.setCellValueFactory(new PropertyValueFactory<>("short_description"));
        D_NewsArticle_Author.setCellValueFactory(new PropertyValueFactory<>("authors"));
        D_NewsArticle_Date.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadArticles();
    }

    public void loadArticles(){
        ObservableList<NewsArticle> articles = FXCollections.observableArrayList();
        MongoCollection<Document> collection = database.getCollection("News_Articles");
        for (Document article : collection.find()) {

            System.out.println(article.toJson()); //debug

            int id = article.getInteger("Article_id", 0);
            articles.add(new NewsArticle(
                    id,
                    article.getString("Link"),
                    article.getString("Headline"),
                    article.getString("Category"),
                    article.getString("Description"),
                    article.getString("Author"),
                    article.getString("Date")
            ));
            //debug
            String h = article.getString("headline");
            String c = article.getString("category");
            String s = article.getString("short_description");
            System.out.println(h + " " + c + " " + s);
        }
        A_NewsArticleData.setItems(articles);
        D_NewsArticleData.setItems(articles);

        System.out.println(articles.size()); //debug
    }

    @FXML
    public void btnUpload(){
        String link = text_urlA.getText();
        String headline = text_titleA.getText();
        String category = text_categoryA.getText().toUpperCase();
        String short_description = text_shortDescriptionA.getText();
        String authors = text_authorA.getText();
        String date = text_dateA.getText();

        if (link.isEmpty() || headline.isEmpty() || category.isEmpty() || short_description.isEmpty() || authors.isEmpty() || date.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "All fields are Required..!");
            alert.showAndWait();
            return;
        }

        MongoCollection<Document> collection = database.getCollection("News_Articles");
        long articleCount = collection.countDocuments();
        int newArticleID = (int) articleCount + 1;
        Document newsArticle = new Document(
                "Article_id", newArticleID)
                .append("Link", link)
                .append("Headline", headline)
                .append("Category", category)
                .append("Description", short_description)
                .append("Author", authors)
                .append("Date", date);

        collection.insertOne(newsArticle);
        loadArticles();

        text_urlA.clear();
        text_titleA.clear();
        text_categoryA.clear();
        text_shortDescriptionA.clear();
        text_authorA.clear();
        text_dateA.clear();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Article added Successfully..!");
        alert.showAndWait();
    }

    @FXML
    private void btnUpdate() {
        try {
            // Validate if the ID field is not empty
            if (text_idU.getText() == null || text_idU.getText().isEmpty()) {
                System.out.println("ID field is empty. Cannot update article.");
                Alert message = new Alert(Alert.AlertType.WARNING, "ID field is empty. Cannot update article.");
                message.showAndWait();
                return;
            }

            // Parse the ID from the text field
            int articleId = Integer.parseInt(text_idU.getText());

            // Get updated values from text fields
            String updatedHeadline = text_titleU.getText();
            String updatedCategory = text_categoryU.getText();
            String updatedAuthor = text_authorU.getText();
            String updatedUrl = text_urlU.getText();
            String updatedDate = text_dateU.getText();
            String updatedShortDescription = text_shortDescriptionU.getText();

            // Validate input fields
            if (updatedHeadline.isEmpty() || updatedCategory.isEmpty() || updatedAuthor.isEmpty() ||
                    updatedUrl.isEmpty() || updatedDate.isEmpty() || updatedShortDescription.isEmpty()) {
                Alert message = new Alert(Alert.AlertType.INFORMATION, "All fields are Required..!");
                message.showAndWait();
                System.out.println("Please fill in all fields before updating!");
                return;
            }

            // Prepare MongoDB update
            MongoCollection<Document> collection = database.getCollection("News_Articles");
            Document query = new Document("Article_id", articleId); // Find article by ID
            Document update = new Document("$set", new Document()
                    .append("Headline", updatedHeadline)
                    .append("Category", updatedCategory)
                    .append("Author", updatedAuthor)
                    .append("Link", updatedUrl)
                    .append("Date", updatedDate)
                    .append("Description", updatedShortDescription));

            // Execute the update
            collection.updateOne(query, update);

            // Feedback and clear fields
            System.out.println("Article updated successfully!");
            Alert message = new Alert(Alert.AlertType.INFORMATION, "Article updated successfully..!");
            message.showAndWait();
            clearArticleFields();

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a numeric ID.");
        } catch (Exception e) {
            System.err.println("Error updating article: " + e.getMessage());
            Alert message = new Alert(Alert.AlertType.ERROR, "Error updating article: " + e.getMessage());
            message.showAndWait();
        }
    }

    @FXML
    public void btnDelete(ActionEvent actionEvent) {
        // Get the selected article
        NewsArticle selectedArticle = D_NewsArticleData.getSelectionModel().getSelectedItem();

        if (selectedArticle != null) {
            System.out.println("Deleting article: " + selectedArticle);
            // Remove the article from the database
            MongoCollection<Document> collection = database.getCollection("News_Articles");
            collection.deleteOne(new Document("Article_id", selectedArticle.getId())); // Delete by ID

            // Remove the article from the list
            newsArticleData.remove(selectedArticle);
            loadArticles();

            Alert message = new Alert(Alert.AlertType.INFORMATION, "Article deleted successfully..");
            message.showAndWait();
        } else {
            Alert message = new Alert(Alert.AlertType.ERROR, "No articles available to delete");
            message.showAndWait();
        }
    }

    @FXML
    public void searchArticleFromDB(ActionEvent actionEvent) {
        // Get the search text
        String searchText = text_U_search.getText().toLowerCase();
        System.out.println("Search Text: " + searchText);

        // Check if searchText is empty
        if (searchText.isEmpty()) {
            Alert message = new Alert(Alert.AlertType.INFORMATION, "Please enter a search term");
            message.showAndWait();
            // Clear the text fields if search is empty
            clearArticleFields();
            return;
        }

        // Query the MongoDB collection
        MongoCollection<Document> collection = database.getCollection("News_Articles");
        System.out.println("Database:" + database.getName() + "\nCollection:" + collection.getNamespace() );
        Bson query = Filters.or(Filters.eq("Article_id", Integer.parseInt(searchText)),
                Filters.regex("Headline", searchText, "i"),
                Filters.regex("Category", searchText, "i"));

        System.out.println("Query: " + query.toBsonDocument());

        Document articleDoc = collection.find(query).first();

        // Check if an article was found
        if (articleDoc != null) {
            // Fill the text fields with article details
            text_idU.setText(String.valueOf(articleDoc.getInteger("Article_id")));
            text_titleU.setText(articleDoc.getString("Headline"));
            text_categoryU.setText(articleDoc.getString("Category"));
            text_authorU.setText(articleDoc.getString("Author"));
            text_urlU.setText(articleDoc.getString("Link"));
            text_dateU.setText(articleDoc.getString("Date"));
            text_shortDescriptionU.setText(articleDoc.getString("Description"));
        } else {
            // Clear the text fields if no article is found
            clearArticleFields();
            System.out.println("No matching article found.");
        }
    }

    @FXML
    public void searchArticleByID(ActionEvent actionEvent) {
        try {
            String searchInput = text_D_search.getText();
            System.out.println("Search Input: " + searchInput);

            if (searchInput == null || searchInput.isEmpty()) {
                System.out.println("Search field is empty!");
                return;
            }

            int searchId = Integer.parseInt(searchInput); // Assuming 'id' is an integer
            MongoCollection<Document> collection = database.getCollection("News_Articles");
            System.out.println("Querying Database with ID: " + searchId);

            FindIterable<Document> result = collection.find(new Document("Article_id", searchId));
            ObservableList<NewsArticle> articles = FXCollections.observableArrayList();

            for (Document doc : result) {
                System.out.println("Found Document: " + doc.toJson());
                articles.add(new NewsArticle(
                        doc.getInteger("Article_id"),
                        doc.getString("Headline"),
                        doc.getString("Category"),
                        doc.getString("Author"),
                        doc.getString("Link"),
                        doc.getString("Date"),
                        doc.getString("Description")
                ));
            }

            System.out.println("Articles List Size: " + articles.size());
            articles.forEach(System.out::println);

            D_NewsArticleData.setItems(articles);
            System.out.println("TableView updated.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format: " + text_D_search.getText());
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    @FXML
    public void btnClicks1(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnMNG_Article){splitPane2.toFront();}
        if (actionEvent.getSource() == btnHome){splitPane1.toFront();}
    }

    @FXML
    public void btnClicks2(ActionEvent actionEvent){
        toPane(actionEvent, btnUpload, paneUpload, btnUpdate, paneUpdate, btnDelete, paneDelete, btnTab, paneTab);
    }

    @FXML
    public void btnClicks3(ActionEvent actionEvent){
        toPane(actionEvent, btnUploadB, paneMenu, btnUpdateB, paneMenu, btnDeleteB, paneMenu, btnTabB, paneMenu);
    }

    @FXML
    public void btnClicksBack(ActionEvent actionEvent){
        if (actionEvent.getSource() == btnBack_AdminHome){
            try {
                Node sourceNode = (Node) actionEvent.getSource();
                WindowChangeAction.closeCurrentWindow(sourceNode);
                WindowChangeAction.showNewStage("hello-view.fxml", "Home");
            } catch (IOException ex) {
                WindowChangeAction.showAlert("Loading Failure");
            }
        }
    }

    private void toPane(ActionEvent actionEvent,
                        Button btnUploadB, Pane paneMenu,
                        Button btnUpdateB, Pane paneMenu2,
                        Button btnDeleteB, Pane paneMenu3,
                        Button btnTabB, Pane paneMenu4) {
        if (actionEvent.getSource() == btnUploadB){
            paneMenu.toFront();}
        if (actionEvent.getSource() == btnUpdateB){
            paneMenu2.toFront();}
        if (actionEvent.getSource() == btnDeleteB){
            paneMenu3.toFront();}
        if (actionEvent.getSource() == btnTabB){
            paneMenu4.toFront();}
    }

    private void clearArticleFields() {
        text_idU.clear();
        text_titleU.clear();
        text_categoryU.clear();
        text_authorU.clear();
        text_urlU.clear();
        text_dateU.clear();
        text_shortDescriptionU.clear();
    }
}