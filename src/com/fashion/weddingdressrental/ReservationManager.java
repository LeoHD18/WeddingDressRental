package com.fashion.weddingdressrental;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationManager {
    private static final String FILE_PATH = "reservations.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                        customer.addReservation(reservation);
                    }
                }
            }
            System.out.println("Reservations loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading reservations from file: " + e.getMessage());
        }
        return reservations;
    }

    // Save all reservations to file
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

    // Add a new reservation and save to file
    public static void addReservation(Reservation reservation, List<Reservation> reservations) {
        reservations.add(reservation);
        saveReservationsToFile(reservations);
        System.out.println("Reservation added and saved successfully.");
    }

    // Display all reservations
    public static void displayReservations(List<Reservation> reservations) {
        System.out.println("\n--- Reservations ---");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
