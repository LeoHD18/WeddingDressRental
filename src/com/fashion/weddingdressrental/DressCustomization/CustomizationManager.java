package com.fashion.weddingdressrental.DressCustomization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CustomizationManager {
    private static final String FILE_PATH = "customizations.txt";

    public static void saveCustomization(Customization customization) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(customization.toCSV());
            writer.newLine();
            System.out.println("Customization saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving customization: " + e.getMessage());
        }
    }

    public static List<Customization> loadCustomizations() {
        List<Customization> customizations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                customizations.add(Customization.fromCSV(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading customizations: " + e.getMessage());
        }
        return customizations;
    }

    public static void updateCustomizationStatus(String customizationId, String newStatus) {
        List<Customization> customizations = loadCustomizations();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Customization customization : customizations) {
                if (customization.getCustomizationId().equals(customizationId)) {
                    customization.setStatus(newStatus);
                }
                writer.write(customization.toCSV());
                writer.newLine();
            }
            System.out.println("Customization status updated.");
        } catch (IOException e) {
            System.out.println("Error updating customization status: " + e.getMessage());
        }
    }
}