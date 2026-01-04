package com.example.homeserve;

public class ProviderModel {

    private String id;
    private String name;
    private String category;
    private String phone;
    private double rating;

    // EMPTY constructor (Firebase needs this)
    public ProviderModel() {}

    // Getters (to read data)
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getPhone() { return phone; }
    public double getRating() { return rating; }

    // Setters (Firebase uses these)
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRating(double rating) { this.rating = rating; }
}
