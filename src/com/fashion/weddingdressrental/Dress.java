package com.fashion.weddingdressrental;

public class Dress {
    private String dressID;
    private boolean isAvailable;

    public Dress(String dressID) {
        this.dressID = dressID;
        this.isAvailable = true;
    }

    public String getDressID() {
        return dressID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    
}