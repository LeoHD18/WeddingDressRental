package com.fashion.weddingdressrental;

public class Store {
    private final String storeId;
    private String storeName;
    private String location;
    private String contactInfo;

    public Store(String storeId, String storeName, String location, String contactInfo) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.location = location;
        this.contactInfo = contactInfo;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getLocation() {
        return location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString() {
        return "Store ID: " + storeId + ", Name: " + storeName + 
               ", Location: " + location + ", Contact: " + contactInfo;
    }
}
