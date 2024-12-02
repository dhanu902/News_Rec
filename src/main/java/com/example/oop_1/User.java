package com.example.oop_1;

import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class User {
    private ObjectId id;              // User ID (could be MongoDB ObjectId)
    private String username;          // User's name
    private String email;             // User's email
    private String password;          // User's password (store securely, e.g., hashed)
    private String gender;            // User's gender
    private Document preferences; // User preferences (e.g., categories, topics)

    // Constructor for User
    public User(ObjectId id, String username, String email, String password, String gender, Document preferences) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.preferences = preferences;
    }

    // Constructor for Admin
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public ObjectId getId() {return id;}
    public void setId(ObjectId id) {this.id = id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}

    public Document getPreferences() {return preferences;}
    public void setPreferences(Document preferences) {this.preferences = preferences;}

    // Abstract method
    public abstract void displayInfo();
}

class ExternalUser extends User {

    public ExternalUser(ObjectId id, String username, String email, String password, String gender, Document preferences) {
        super(id, username, email, password, gender, preferences);
    }

    @Override
    public void setPreferences(Document preferences) {super.setPreferences(preferences);}
    @Override
    public void setId(ObjectId id) {super.setId(id);}
    @Override
    public void setUsername(String username) {super.setUsername(username);}
    @Override
    public void setEmail(String email) {super.setEmail(email);}
    @Override
    public void setPassword(String password) {super.setPassword(password);}
    @Override
    public void setGender(String gender) {super.setGender(gender);}

    // for debug
    @Override
    public void displayInfo() {
        System.out.println("User{" +
                "id='" + getId() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", gender='" + getGender() + '\'' +
                ", preferences=" + getPreferences() +
                '}');
    }
}

class Admin extends User {
    String admin_id;

    public Admin(String admin_id, String admin_name, String email, String password) {
        super(admin_name, email, password);
        this.admin_id = admin_id;
    }

    public String getAdmin_id() {return admin_id;}
    public void setAdmin_id(String admin_id) {this.admin_id = admin_id;}

    @Override
    public void setEmail(String email) {super.setEmail(email);}
    @Override
    public void setPassword(String password) {super.setPassword(password);}
    @Override
    public void setUsername(String admin_name) {super.setUsername(admin_name);}

    // for debug
    @Override
    public void displayInfo() {
        System.out.println("Admin{" +
                "id='" + getId() + '\'' +
                ", a.name='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", gender='" + getGender() + '\'' +
                ", preferences=" + getPreferences() +
                '}');
    }
}