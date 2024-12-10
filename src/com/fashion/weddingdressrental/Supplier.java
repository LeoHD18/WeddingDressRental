package com.fashion.weddingdressrental;

public class Supplier {
    private final String supplierId;
    private String companyName;
    private String email;
    private String phone;
    private String categories;

    public Supplier(String supplierId, String companyName, String email, String phone, String categories) {
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.email = email;
        this.phone = phone;
        this.categories = categories;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCategories() {
        return categories;
    }
}
