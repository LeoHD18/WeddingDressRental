package com.fashion.weddingdressrental.Feedback;

import java.io.*;
import java.util.*;

public class FeedbackManager {
    private static final String FILE_PATH = "feedback.txt";

    public static void saveFeedback(Feedback feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(feedback.toCSV());
            writer.newLine();
            System.out.println("Feedback saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving feedback: " + e.getMessage());
        }
    }

    public static List<Feedback> loadFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Feedback feedback = Feedback.fromCSV(line);
                if (feedback != null) {
                    feedbackList.add(feedback);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading feedback: " + e.getMessage());
        }
        return feedbackList;
    }
}