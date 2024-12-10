package com.fashion.weddingdressrental;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
    private static final String FILE_PATH = "reservations.txt";

    // Load Reservations from File
    public static List<Reservation> loadReservationsFromFile(CustomerManager customerManager, InventoryManager inventoryManager) {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    String reservationID = parts[0];
                    String customerId = parts[1];
                    String dressId = parts[2];
                    String storeId = parts[3];
                    int quantity = Integer.parseInt(parts[4]);
                    Date startDate = new Date(Long.parseLong(parts[5]));
                    Date endDate = new Date(Long.parseLong(parts[6]));
                    String status = parts[7];

                    Customer customer = customerManager.findCustomerById(customerId);
                    InventoryItem dress = inventoryManager.findDressByIdInStore(storeId, dressId);

                    if (customer != null && dress != null) {
                        Reservation reservation = new Reservation(reservationID, customer, dress, startDate, endDate, storeId, quantity);
                        reservation.setStatus(status);
                        reservations.add(reservation);
                        customer.addReservation(reservation);  // Update Customer's Reservations
                    }
                }
            }
            System.out.println("Reservations loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading reservations from file: " + e.getMessage());
        }
        return reservations;
    }

    // Save List of Reservations to File
    public static void saveReservationsToFile(List<Reservation> reservations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Reservation reservation : reservations) {
                writer.write(reservation.toCSV());
                writer.newLine();
            }
            System.out.println("Reservations saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }

    // Save Single Reservation to File
    public static void saveReservationToFile(Reservation reservation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(reservation.toCSV());
            writer.newLine();
            System.out.println("Reservation saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reservation: " + e.getMessage());
        }
    }

    // Find Reservation by ID
    public static Reservation findReservationById(String reservationId, CustomerManager customerManager, InventoryManager inventoryManager) {
        List<Reservation> reservations = loadReservationsFromFile(customerManager, inventoryManager);
        return reservations.stream()
                .filter(reservation -> reservation.getReservationID().equals(reservationId))
                .findFirst()
                .orElse(null);
    }

    // Update Reservation Status in File
    public static void updateReservationStatus(String reservationId, String newStatus, List<Reservation> reservations) {
        boolean updated = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Reservation reservation : reservations) {
                if (reservation.getReservationID().equals(reservationId)) {
                    reservation.setStatus(newStatus);  // Update in Memory
                    updated = true;
                }
                writer.write(reservation.toCSV());
                writer.newLine();
            }
            System.out.println(updated ? "Reservation updated successfully." : "Reservation not found.");
        } catch (IOException e) {
            System.out.println("Error updating reservation: " + e.getMessage());
        }
    }
}
