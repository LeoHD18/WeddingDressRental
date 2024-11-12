package com.fashion.weddingdressrental;

public class Payment {
    private double amount;
    private Customer customer;
    private Employee employee;
    private PaymentType paymentType;
    private boolean isSuccessful;

    public Payment(double amount, Customer customer, Employee employee, PaymentType paymentType) {
        this.amount = amount;
        this.customer = customer;
        this.employee = employee;
        this.paymentType = paymentType;
        this.isSuccessful = false;
    }

    // Process payment based on payment type
    public boolean processPayment(String accountNumber) {
        if (paymentType == PaymentType.DEBIT_CARD) {
            if (authenticateAccount(accountNumber)) {
                // Simulate payment processing for a successful transaction
                isSuccessful = true;
                System.out.println("Payment processed successfully for amount: $" + amount);
            } else {
                System.out.println("Authentication failed.");
                isSuccessful = false;
            }
        } else {
            System.out.println("Payment type not supported.");
        }
        return isSuccessful;
    }
    
    

    public double getAmount() { return amount; }
    public boolean isSuccessful() { return isSuccessful; }
    // Authenticate the account by checking if customer ID and account number match
      private boolean authenticateAccount(String accountNumber) {
        return customer.getAccount() != null && customer.getAccount().getAccountNumber().equals(accountNumber);
    }

    @Override
    public String toString() {
        return "Payment of $" + amount + " by " + paymentType + " for customer: " + 
               (customer != null ? customer.getName() : "Gift Card Purchase") +
               ". Processed by Employee: " + employee.getName();
    }
}
