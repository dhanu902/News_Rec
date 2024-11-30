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

    // Constructors
    public User(ObjectId id, String username, String email, String password, String gender, Document preferences) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.preferences = preferences;
    }

    public User(String username, String email, String password, String gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    // Getters and setters
    public ObjectId getId() {return id;}
    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}

    public Document getPreferences() {return preferences;}
    public void setPreferences(Document preferences) {this.preferences = preferences;}

    // toString method (for debugging)
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", preferences=" + preferences +
                '}';
    }

    // Abstract method: must be implemented by subclasses
    public abstract void displayInfo();

    // Concrete method: shared functionality
    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password updated successfully.");
    }
}

class ConcreteUser extends User {
        public ConcreteUser(ObjectId id, String username, String email, String password, String gender, Document preferences) {
            super(id, username, email, password, gender, preferences);
        }
        @Override
        public void setPreferences(Document preferences) {
            super.setPreferences(preferences);
        }
        @Override
        public void displayInfo() {}
}

class admin extends User {
    public admin(int admin_id, String username, String email, String password, String gender) {
        super(username, email, password, gender);
    }
    @Override
    public void setId(ObjectId id) {
        super.setId(id);
    }
    public void displayInfo() {}
}
