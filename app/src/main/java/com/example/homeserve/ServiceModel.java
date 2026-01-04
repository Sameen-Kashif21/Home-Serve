package com.example.homeserve;

public class ServiceModel {

    private String name;
    private String icon;

    public ServiceModel(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
