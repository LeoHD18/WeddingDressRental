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
            System.out.println("Status of dress " + dressId + " updated to " + newStatus);
        } catch (Exception e) {
            System.out.println("Error updating dress status: " + e.getMessage());
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
