package com.fashion.weddingdressrental;

/**
 * Represents a supplier in the wedding dress rental system.
 * This class stores essential information about suppliers including their
 * unique identifier, company details, contact information, and product categories.
 */
public class Supplier {
    private final String supplierId;
    private String companyName;
    private String email;
    private String phone;
    private String categories;

    /**
     * Constructs a new Supplier with the specified details.
     *
     * @param supplierId   The unique identifier for the supplier
     * @param companyName  The name of the supplier's company
     * @param email       The contact email address of the supplier
     * @param phone       The contact phone number of the supplier
     * @param categories  The categories of products offered by the supplier
     */
    public Supplier(String supplierId, String companyName, String email, String phone, String categories) {
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.categories = categories;
    }

    /**
     * Returns the unique identifier of the supplier.
     *
     * @return The supplier's ID
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * Returns the name of the supplier's company.
     *
     * @return The company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Returns the email address of the supplier.
     *
     * @return The supplier's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the phone number of the supplier.
     *
     * @return The supplier's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the categories of products offered by the supplier.
     *
     * @return The product categories
     */
    public String getCategories() {
        return categories;
    }
}