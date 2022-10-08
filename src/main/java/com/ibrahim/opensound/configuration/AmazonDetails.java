package com.ibrahim.opensound.configuration;

public enum AmazonDetails {
    ACCESS_KEY("YOUR_ACCESS_KEY"),
    PRIVATE_KEY("YOUR_PRIVATE_KEY"),
    BUCKET_NAME("YOUR_BUCKET_NAME"),
    REGION("YOUR_REGION");

    private String value;

    AmazonDetails(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
