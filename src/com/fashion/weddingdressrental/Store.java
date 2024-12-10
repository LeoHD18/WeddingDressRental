package com.fashion.weddingdressrental;

/**
 * Represents a store in the wedding dress rental system.
 * This class stores essential information about a retail location including
 * its unique identifier, name, physical location, and contact details.
 */
public class Store {
    private final String storeId;
    private String storeName;
    private String location;
    private String contactInfo;

    /**
     * Constructs a new Store with the specified details.
     *
     * @param storeId     The unique identifier for the store
     * @param storeName   The name of the store
     * @param location    The physical location of the store
     * @param contactInfo The contact information for the store
     */
    public Store(String storeId, String storeName, String location, String contactInfo) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.location = location;
        this.contactInfo = contactInfo;
    }

    /**
     * Returns the unique identifier of the store.
     *
     * @return The store's ID
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * Returns the name of the store.
     *
     * @return The store's name
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * Returns the physical location of the store.
     *
     * @return The store's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the contact information of the store.
     *
     * @return The store's contact information
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * Updates the name of the store.
     *
     * @param storeName The new name for the store
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * Updates the physical location of the store.
     *
     * @param location The new location for the store
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Updates the contact information of the store.
     *
     * @param contactInfo The new contact information for the store
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Returns a string representation of the store.
     * Includes the store's ID, name, location, and contact information.
     *
     * @return A formatted string containing all store details
     */
    @Override
    public String toString() {
        return "Store ID: " + storeId + ", Name: " + storeName +
                ", Location: " + location + ", Contact: " + contactInfo;
    }
}