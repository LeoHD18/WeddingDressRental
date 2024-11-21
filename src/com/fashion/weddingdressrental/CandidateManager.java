package com.fashion.weddingdressrental;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CandidateManager {
    private static final String FILE_PATH = "candidates.txt";
    private Map<String, Candidate> candidates;

    public CandidateManager() {
        candidates = new HashMap<>();
        loadCandidatesFromFile();
    }

    // Load candidates from file
    private void loadCandidatesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String candidateId = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String phone = parts[3];
                    String resume = parts[4];
                    double interviewPoints = Double.parseDouble(parts[5]);

                    Candidate candidate = new Candidate(candidateId, name, email, phone, resume);
                    candidate.setInterviewPoints(interviewPoints);
                    candidates.put(candidateId, candidate);
                }
            }
            System.out.println("Candidates loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading candidates from file: " + e.getMessage());
        }
    }

    // Save candidates to file
    public void saveCandidatesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Candidate candidate : candidates.values()) {
                writer.write(candidate.toCSV());
                writer.newLine();
            }
            System.out.println("Candidates saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving candidates to file: " + e.getMessage());
        }
    }

    public void addCandidate(Candidate candidate) {
        candidates.put(candidate.getCandidateId(), candidate);
        saveCandidatesToFile();
    }

    public Candidate findCandidateById(String candidateId) {
        return candidates.get(candidateId);
    }

    public void displayCandidates() {
        System.out.println("\n--- Candidate List ---");
        candidates.values().forEach(System.out::println);
    }
}
