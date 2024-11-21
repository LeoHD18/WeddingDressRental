package com.fashion.weddingdressrental;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
    private static final String FILE_PATH = "reservations.txt";

 // Load reservations from file
    public static List<Reservation> loadReservationsFromFile(CustomerManager customerManager, InventoryManager inventoryManager) {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String reservationID = parts[0];
                    String customerId = parts[1];
                    String dressId = parts[2];
                    Date startDate = new Date(Long.parseLong(parts[3]));
                    Date endDate = new Date(Long.parseLong(parts[4]));
                    String status = parts[5];

                    Customer customer = customerManager.findCustomerById(customerId);
                    InventoryItem dress = inventoryManager.findDressById(dressId);

                    if (customer != null && dress != null) {
                        Reservation reservation = new Reservation(reservationID, customer, dress, startDate, endDate);
                        reservation.setStatus(status);
                        reservations.add(reservation);
                        customer.addReservation(reservation);  // Update customer reservations
                    }
                }
            }
            System.out.println("Reservations loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading reservations from file: " + e.getMessage());
        }
        return reservations;
    }

    //ADDED
    // Ensure this method accepts CustomerManager and InventoryManager
    public static Reservation findReservationById(String reservationId, CustomerManager customerManager, InventoryManager inventoryManager) {
        List<Reservation> reservations = loadReservationsFromFile(customerManager, inventoryManager); // Load reservations
        return reservations.stream()
            .filter(reservation -> reservation.getReservationID().equals(reservationId))
            .findFirst()
            .orElse(null); // Return the matching reservation or null if not found
    }


    // Save the updated reservations list to the file
    public static void saveReservationsToFile(List<Reservation> reservations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Reservation reservation : reservations) {
                writer.write(reservation.toCSV());
                writer.newLine();
            }
            System.out.println("Reservations saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservations to file: " + e.getMessage());
        }
    }

    // Update reservation status in the file (this will only save the specific reservation)
    public static void updateReservationStatus(String reservationId, String newStatus, List<Reservation> reservations) {
        boolean updated = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Reservation reservation : reservations) {
                if (reservation.getReservationID().equals(reservationId)) {
                    reservation.setStatus(newStatus); // Update the status in memory
                    updated = true;
                }
                writer.write(reservation.toCSV()); // Write the reservation to the file
                writer.newLine();
            }
    
            if (updated) {
                System.out.println("Reservation " + reservationId + " status updated to " + newStatus + ".");
            } else {
                System.out.println("Reservation ID not found.");
            }
        } catch (IOException e) {
            System.out.println("Error updating reservation status: " + e.getMessage());
        }
    }
}