package com.example.homeserve;

public class BookingModel {

    private String id;
    private String providerName;
    private String category;
    private String date;
    private String time;
    private String status;
    private String userId;

    private String providerPhone;
    private double providerRating;

    private String address;
    private String details;

    public BookingModel() {
        // required for Firestore
    }

    // Getters
    public String getId() { return id; }
    public String getProviderName() { return providerName; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    public String getUserId() { return userId; }

    public String getProviderPhone() { return providerPhone; }
    public double getProviderRating() { return providerRating; }

    public String getAddress() { return address; }
    public String getDetails() { return details; }

    // Setters (Firestore can fill automatically, but setters help when you set manually)
    public void setId(String id) { this.id = id; }
    public void setProviderName(String providerName) { this.providerName = providerName; }
    public void setCategory(String category) { this.category = category; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setStatus(String status) { this.status = status; }
    public void setUserId(String userId) { this.userId = userId; }

    public void setProviderPhone(String providerPhone) { this.providerPhone = providerPhone; }
    public void setProviderRating(double providerRating) { this.providerRating = providerRating; }

    public void setAddress(String address) { this.address = address; }
    public void setDetails(String details) { this.details = details; }
}
