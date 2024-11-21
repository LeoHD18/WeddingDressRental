package com.fashion.weddingdressrental;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HR {
    private CandidateManager candidateManager;
    private EmployeeManager employeeManager;
    private static final String INTERVIEW_FILE = "interviews.txt";

    public HR(CandidateManager candidateManager, EmployeeManager employeeManager) {
        this.candidateManager = candidateManager;
        this.employeeManager = employeeManager;
    }

  // Assign a candidate to an interview
    public void assignInterview(String candidateId, String time, String location) {
        Candidate candidate = candidateManager.findCandidateById(candidateId);
        if (candidate == null) {
            System.out.println("Candidate not found.");
            return;
        }

        // Check if the candidate already has an assigned interview
        String existingInterview = findExistingInterview(candidateId);
        if (existingInterview != null) {
            System.out.println("Candidate already has an interview assigned:");
            System.out.println(existingInterview);
            System.out.print("Do you want to update the interview details? (Y/N): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().trim().toUpperCase();
            if (!response.equals("Y")) {
                System.out.println("Operation canceled. Existing interview remains unchanged.");
                return;
            }
        }

        // Save or update the interview information
        saveOrUpdateInterview(candidateId, candidate.getName(), time, location);
        System.out.println("Interview assigned/updated successfully for " + candidate.getName());
    }

    // Check if the candidate already has an interview assigned
    private String findExistingInterview(String candidateId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(INTERVIEW_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Candidate ID: " + candidateId + ",")) {
                    return line;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading interview file: " + e.getMessage());
        }
        return null; // No existing interview found
    }

     // Save or update the interview details
    private void saveOrUpdateInterview(String candidateId, String candidateName, String time, String location) {
        List<String> allInterviews = new ArrayList<>();
        boolean updated = false;

        // Read all existing interviews and update the entry for this candidate
        try (BufferedReader reader = new BufferedReader(new FileReader(INTERVIEW_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Candidate ID: " + candidateId + ",")) {
                    line = "Candidate ID: " + candidateId + ", Name: " + candidateName +
                           ", Time: " + time + ", Location: " + location;
                    updated = true;
                }
                allInterviews.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading interview file: " + e.getMessage());
        }

        // If not updated, add the new interview to the list
        if (!updated) {
            allInterviews.add("Candidate ID: " + candidateId + ", Name: " + candidateName +
                              ", Time: " + time + ", Location: " + location);
        }

        // Write all interviews back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INTERVIEW_FILE))) {
            for (String interview : allInterviews) {
                writer.write(interview);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to interview file: " + e.getMessage());
        }
    }
    // Hire a candidate and add to employees
    public void hireCandidate(String candidateId, double salary, String location, String role) {
        Candidate candidate = candidateManager.findCandidateById(candidateId);
        if (candidate == null) {
            System.out.println("Candidate not found.");
            return;
        }

        String employeeId = "EMP-" + (int) (Math.random() * 10000);
        Employee employee = new Employee(employeeId, candidate.getName(), location, salary, role);
        employeeManager.addEmployee(employee);

        // Remove the candidate after hiring
        candidateManager.removeCandidate(candidateId);

        System.out.println("Candidate " + candidate.getName() + " has been hired as Employee ID: " + employeeId);
    }
}
