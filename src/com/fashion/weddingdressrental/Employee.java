package com.fashion.weddingdressrental;

public class Employee {
    private String name;
    private String employeeId;

    public Employee(String name) {
        this.name = name;
        this.employeeId = "EMP-" + (int) (Math.random() * 10000); // Generating a random ID for simplicity
    }

    // Method to process an alteration request from a customer
    public void processAlterationRequest(Customer customer, AlterationRequest alterationRequest) {
        System.out.println("Employee " + name + " is processing alteration request for customer " + customer.getName());

        // Starting the alteration process
        alterationRequest.startAlteration();

        // For simplicity, assuming alteration can be completed
        alterationRequest.completeAlteration();

        // If the alteration could not be completed, use:
        // alterationRequest.cannotCompleteAlteration();
    }

    // Method to manage inventory status
    public void updateDressStatus(InventoryManager inventoryManager, String dressId, String newStatus) {
        try {
            inventoryManager.updateDressStatus(dressId, newStatus);
        } catch (Exception e) {
            System.out.println("Error updating dress status: " + e.getMessage());
            System.out.println("Please retry or contact IT support.");
        }
    }
    
    // Method for employee to initiate wash and prep
    public void performWashAndPrep(Dress dress, WashAndPrep washAndPrep) {
        if (!dress.isAvailable()) {
            System.out.println("Employee " + name + " is preparing the dress.");
            washAndPrep.prepareDress(dress);
            System.out.println("The dress is now ready for the next rental.");
        } else {
            System.out.println("The dress is already available and ready for rental.");
        }
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter for employeeId
    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public String toString() {
        return "Employee { " +
                "ID='" + employeeId + '\'' +
                ", Name='" + name + '\'' +
                " }";
    }
}
