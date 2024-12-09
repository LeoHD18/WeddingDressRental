package com.fashion.weddingdressrental;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingManager {
    private static final String FILE_PATH = "trainings.txt";
    private List<TrainingSession> trainingSessions;

    public TrainingManager() {
        trainingSessions = new ArrayList<>();
        loadTrainingsFromFile();
    }

    private void loadTrainingsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String trainingId = parts[0];
                    String topic = parts[1];
                    String trainer = parts[2];
                    String date = parts[3];
                    List<String> participantIds = List.of(parts[4].split(";"));

                    TrainingSession session = new TrainingSession(trainingId, topic, trainer, date, new ArrayList<>(participantIds));
                    trainingSessions.add(session);
                }
            }
            System.out.println("Trainings loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading trainings: " + e.getMessage());
        }
    }

    public void saveTrainingsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (TrainingSession session : trainingSessions) {
                writer.write(session.toCSV());
                writer.newLine();
            }
            System.out.println("Trainings saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving trainings: " + e.getMessage());
        }
    }

    public void addTrainingSession(TrainingSession session) {
        trainingSessions.add(session);
        saveTrainingsToFile();
    }

    public List<TrainingSession> getAllTrainingSessions() {
        return trainingSessions;
    }

    public TrainingSession findTrainingById(String trainingId) {
        return trainingSessions.stream()
                .filter(session -> session.getTrainingId().equals(trainingId))
                .findFirst()
                .orElse(null);
    }

    // private static final List<String> predefinedTopics = List.of(
    //     "Workplace Safety",
    //     "Diversity and Inclusion",
    //     "Technical Skills",
    //     "Customer Service Excellence",
    //     "Leadership Training"
    // );

    // public List<String> getPredefinedTopics() {
    //     return predefinedTopics;
    // }
}