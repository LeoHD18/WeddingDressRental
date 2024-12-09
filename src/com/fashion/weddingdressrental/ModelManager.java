package com.fashion.weddingdressrental;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles operations related to models, such as loading, saving to files
 */
public class ModelManager {
    private static final String MODELS_FILE = "models.txt";
    private static final String HIRED_MODELS_FILE = "hiredModels.txt";
    private static Map<String, Model> models; // hashmap to store model and id
    private static Map<String,Model> hiredModels; // hashmap to track hired model

       /**
     * Constructor that initializes the ModelManager by loading models and hired models.
     */
    public ModelManager() {
        models = new HashMap<>();
        hiredModels = new HashMap<>();
        loadModels();
        loadHiredModels();
    }

  /**
     * Loads all models from the file into models.
     */
    public static void loadModels() {
        models.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(MODELS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Model model = new Model(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4],
                        Double.parseDouble(data[5]), Double.parseDouble(data[6]), data[7], data[8],
                        Double.parseDouble(data[9]), Double.parseDouble(data[10]), Double.parseDouble(data[11]),
                        data[12], data[13], Integer.parseInt(data[14]), data[15], Double.parseDouble(data[16]));
                models.put(model.getId(), model);
            }
        } catch (IOException e) {
            System.out.println("Error loading models: " + e.getMessage());
        }
    }

        /**
     * Retrieves all loaded models as a Map.
     *
     * @return the map of model IDs to Model objects
     */
    public static Map<String, Model> getModels() {
        return models;
    }

         /**
     * Retrieves all hired models as a Map.
     *
     * @return the map of model IDs to Model objects
     */
    public static Map<String, Model> getHiredModels() {
        return hiredModels;
    }

    /**
     * Loads all hired models from the file into the hiredModels map.
     */
    public static void loadHiredModels() {
        hiredModels.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(HIRED_MODELS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // Split to get the model and note
                String modelData = parts[0].trim();
                String[] modelDetails = modelData.split(","); // Get model ID
                if (modelDetails.length > 0) {
                    String modelId = modelDetails[0].trim();
                    Model model = models.get(modelId);
                    if (model != null) {
                        hiredModels.put(modelId, model);
                    }
                }
            }
            System.out.println("Hired models loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Hired models file not found. No models have been hired yet.");
        } catch (IOException e) {
            System.out.println("Error loading hired models: " + e.getMessage());
        }
    }

  /**
     * Saves a hired model to the hired models file along with a note.
     *
     * @param model the model to save
     * @param note  the note provided by the user for the model's agent
     */
    public static void saveHiredModel(Model model, String note, int days, double totalCost, String startHour, String endHour,
                                      String eventType, String eventLocation, String transportationAndAccommodation,
                                      String paymentTermsAgreement, String updatePreference) {
        if (hiredModels.containsKey(model.getId())) {
            System.out.println("This model has already been hired.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIRED_MODELS_FILE, true))) {
            writer.write(String.format(
                "%s | Days: %d | Total Cost: $%.2f | Start Hour: %s | End Hour: %s | Event Type: %s | Location: %s | Transport & Accommodation: %s | Payment Terms: %s | Updates via: %s | Note: %s\n",
                model.toString(), days, totalCost, startHour, endHour, eventType, eventLocation,
                transportationAndAccommodation, paymentTermsAgreement, updatePreference, note));
            writer.newLine();
            hiredModels.put(model.getId(), model); // Track the hired model
            System.out.println("Hired model saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving hired model: " + e.getMessage());
        }
    }

     /**
     * Finds a model by its ID.
     *
     * @param modelId the ID of the model to find
     * @return the model with the given ID, or null if not found
     */
    public static Model findModelById(String modelId) {
        return models.get(modelId);
    }

      /**
     * Checks if a model is already hired.
     *
     * @param modelId the ID of the model to check.
     * @return true if the model is already hired, false otherwise.
     */
    public static boolean isModelHired(String modelId) {
        return hiredModels.containsKey(modelId);
    }

        /**
     * Displays all models currently loaded in the map.
     */
    public static void displayAllModels() {
        if (models.isEmpty()) {
            System.out.println("No models available. Please load the models first.");
            return;
        }

        System.out.println("\n--- All Models ---");
        models.values().forEach(model -> System.out.println(model));
    }

  

      /**
     * Displays all hired models currently tracked in the hiredModels map.
     */
    public static void viewHiredModels() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIRED_MODELS_FILE))) {
            String line;
            if ((line = reader.readLine()) == null) {
                System.out.println("No models have been hired yet.");
                return;
            }
            System.out.println("\n--- Hired Models ---");
            do {
                System.out.println(line);
            } while ((line = reader.readLine()) != null);
        } catch (FileNotFoundException e) {
            System.out.println("Hired models file not found. No models have been hired yet.");
        } catch (IOException e) {
            System.out.println("Error reading hired models: " + e.getMessage());
        }
    }

   
}
