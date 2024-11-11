package com.fashion.weddingdressrental;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlterationManager {
    private static final String FILE_PATH = "alterations.txt";

    // Method to save alteration requests to file (append mode for new additions)
    public static void saveAlterationsToFile(List<AlterationRequest> alterationRequests) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            for (AlterationRequest alteration : alterationRequests) {
                writer.write(alteration.toCSV());
                writer.newLine();
            }
            System.out.println("Alterations saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving alterations to file: " + e.getMessage());
        }
    }

    // Method to load alteration requests from file
    public static List<AlterationRequest> loadAlterationsFromFile() {
        List<AlterationRequest> alterations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String alterationId = parts[0];
                    String customerId = parts[1];
                    String details = parts[2];
                    double cost = Double.parseDouble(parts[3]);
                    Date completionDate = new Date(Long.parseLong(parts[4]));

                    AlterationRequest alteration = new AlterationRequest(alterationId, customerId, details, cost, completionDate);
                    alterations.add(alteration);
                }
            }
            System.out.println("Alterations loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading alterations from file: " + e.getMessage());
        }
        return alterations;
    }

    // Method to remove a specific alteration request from file by alteration ID
    public static void removeAlterationFromFile(String alterationId) {
        List<AlterationRequest> alterations = loadAlterationsFromFile();
        boolean removed = alterations.removeIf(alteration -> alteration.getAlterationId().equals(alterationId));
        
        if (removed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) { // Overwrite file
                for (AlterationRequest alteration : alterations) {
                    writer.write(alteration.toCSV());
                    writer.newLine();
                }
                System.out.println("Alteration with ID " + alterationId + " removed successfully.");
            } catch (IOException e) {
                System.out.println("Error updating alterations file: " + e.getMessage());
            }
        } else {
            System.out.println("Alteration with ID " + alterationId + " not found.");
        }
    }
}
