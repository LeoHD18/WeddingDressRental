package com.fashion.weddingdressrental;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Handles operations related to models, such as loading, saving to files
 */
public class ModelManager {
    private static final String MODELS_FILE = "models.txt";
    private static final String HIRED_MODELS_FILE = "hiredModels.txt";
    private static Map<String, Model> models = new HashMap<>(); // hashmap to store model and id
    private static Set<String> hiredModelIds = new HashSet<>(); // set to track hired model

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
     * Saves a hired model to the hired models file along with a note.
     *
     * @param model the model to save
     * @param note  the note provided by the user for the model's agent
     */
    public static void saveHiredModel(Model model, String note) {
         if (hiredModelIds.contains(model.getId())) {
            System.out.println("This model has already been hired.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIRED_MODELS_FILE, true))) {
            writer.write(model.toString() + " | Note: " + note);
            writer.newLine();
            hiredModelIds.add(model.getId()); // Track the hired model
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
     * Checks if a model is already hired.
     *
     * @param modelId the ID of the model to check
     * @return true if the model is already hired, false otherwise
     */
    public static boolean isModelHired(String modelId) {
        return hiredModelIds.contains(modelId);
    }

     /**
     * Displays all hired models along with their associated notes.
     */
    public static void viewHiredModels() {
        System.out.println("\n--- Hired Models ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(HIRED_MODELS_FILE))) {
            String line;
            boolean hasHiredModels = false;

            while ((line = reader.readLine()) != null) {
                hasHiredModels = true;
                System.out.println(line);
            }

            if (!hasHiredModels) {
                System.out.println("No models have been hired yet.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Hired models file not found. No models have been hired yet.");
        } catch (IOException e) {
            System.out.println("Error reading hired models file: " + e.getMessage());
        }
    }

   
}
