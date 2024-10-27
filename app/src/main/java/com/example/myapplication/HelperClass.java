package com.example.myapplication;

public class HelperClass {
    private String name;
    private String email;
    private String username;
    private String password;

    public HelperClass() {
        // Default constructor required for calls to DataSnapshot.getValue(HelperClass.class)
    }

    public HelperClass(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
