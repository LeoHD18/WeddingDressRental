// package com.fashion.weddingdressrental;

// import java.util.Date;

// public class Transaction {

//     // Attributes
//     private String transactionID;
//     private Date startDate;
//     private Date endDate;
//     private int rentalDuration;
//     private Dress dress;
//     private Customer customer;
//     private Payment payment;
//     private Employee employee;
//     private double amount;

//     // Constructor
//     public Transaction(String transactionID, Date startDate, Date endDate, Dress dress, Customer customer, Employee employee) {
//         this.transactionID = transactionID;
//         this.startDate = startDate;
//         this.endDate = endDate;
//         this.rentalDuration = calculateDuration();
//         this.dress = dress;
//         this.customer = customer;
//         this.employee = employee;
//         this.amount = calculateAmount();
//     }

//     // Method to finalize the transaction
//     public void finalizeTransaction() {
//         if (payment != null && payment.isSuccessful()) {
//             System.out.println("Payment processed successfully for transaction " + transactionID);
            
//             // Prepare the dress for the next rental
//             WashAndPrep washAndPrep = new WashAndPrep();
//             washAndPrep.prepareDress(dress);
//             System.out.println("Transaction " + transactionID + " has been finalized.");
//         } else {
//             System.out.println("Transaction " + transactionID + " could not be finalized due to payment issues.");
//         }
//     }

//     // Method to calculate rental duration in days
//     public int calculateDuration() {
//         long difference = endDate.getTime() - startDate.getTime();
//         return (int) (difference / (1000 * 60 * 60 * 24));
//     }

//     // Method to calculate the amount based on rental duration and dress price
//     private double calculateAmount() {
//         double dailyRate = dress.getPrice();  // Assuming dress has a getPrice() method
//         return rentalDuration * dailyRate;
//     }

//     // Checkout method to process payment
//     public void checkout(PaymentType paymentMethod) {
//         payment = new Payment(amount, customer, employee, paymentMethod);
//         if (payment.processPayment()) {
//             System.out.println("Checkout successful for transaction " + transactionID);
//         } else {
//             System.out.println("Checkout failed for transaction " + transactionID);
//         }
//     }

//     // Getters
//     public String getTransactionID() {
//         return transactionID;
//     }

//     public Date getStartDate() {
//         return startDate;
//     }

//     public Date getEndDate() {
//         return endDate;
//     }

//     public int getRentalDuration() {
//         return rentalDuration;
//     }

//     public Dress getDress() {
//         return dress;
//     }

//     public Customer getCustomer() {
//         return customer;
//     }

//     public Payment getPayment() {
//         return payment;
//     }

//     public double getAmount() {
//         return amount;
//     }

//     @Override
//     public String toString() {
//         return "Transaction ID: " + transactionID + ", Amount: $" + amount + ", Customer: " + customer.getName() +
//                 ", Dress: " + dress + ", Duration: " + rentalDuration + " days";
//     }
// }
