package com.fashion.weddingdressrental;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages operations related to photoshoots, such as saving to file, loading, and generating IDs.
 */
public class PhotoshootManager {
    private static final String FILE_PATH = "photoshoots.txt";

    /**
     * Saves a photoshoot to the file.
     *
     * @param photoshoot The photoshoot to save.
     */
    public static void savePhotoshoot(Photoshoot photoshoot) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(photoshoot.toCSV());
            writer.newLine();
            System.out.println("Photoshoot saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving photoshoot: " + e.getMessage());
        }
    }

    /**
     * Loads all photoshoots from the file.
     *
     * @return A list of photoshoots.
     */
    public static List<Photoshoot> loadPhotoshoots() {
        List<Photoshoot> photoshoots = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parsing logic for photoshoots (not implemented here for brevity)
            }
        } catch (IOException e) {
            System.out.println("Error loading photoshoots: " + e.getMessage());
        }
        return photoshoots;
    }

    /**
     * Generates a unique photoshoot ID.
     *
     * @return A unique ID for the photoshoot.
     */
    public static String generatePhotoshootId() {
        return "PS-" + System.currentTimeMillis();
    }
}
