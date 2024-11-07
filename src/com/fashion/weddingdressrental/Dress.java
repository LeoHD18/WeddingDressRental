package com.fashion.weddingdressrental;

public class Dress extends InventoryItem {
//    private String dressID;
//    private boolean isAvailable;
//
//    public Dress(String dressID) {
//        this.dressID = dressID;
//        this.isAvailable = false; // Assume it's unavailable until cleaned
//    }
//
//    public String getDressID() {
//        return dressID;
//    }
//
//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    public void setAvailable(boolean isAvailable) {
//        this.isAvailable = isAvailable;
//    }


	    public Dress(String dressID) {
	        super(dressID, "Available"); // Initialize the status to "Available"
	    }

	    public String getDressID() {
	        return getDressId(); // Use getId() from InventoryItem instead of getDressID()
	    }

	    public boolean isAvailable() {
	        return getStatus().equalsIgnoreCase("Available");
	    }

	    public void setAvailable(boolean isAvailable) {
	        setStatus(isAvailable ? "Available" : "Unavailable");
	    }
}