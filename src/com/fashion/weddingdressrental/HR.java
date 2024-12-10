package com.fashion.weddingdressrental;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages HR-related functionalities, including assigning interviews, hiring candidates,
 * creating training sessions, and assigning employees to trainings.
 */
public class HR {
    private CandidateManager candidateManager;
    private EmployeeManager employeeManager;
    private TrainingManager trainingManager;
    private Scanner scanner;
    private static final String INTERVIEW_FILE = "interviews.txt";

    /**
     * Constructs an HR instance.
     *
     * @param candidateManager The manager responsible for handling candidate records.
     * @param employeeManager  The manager responsible for handling employee records.
     * @param trainingManager  The manager responsible for handling training sessions.
     * @param scanner          A scanner for user input.
     */
    public HR(CandidateManager candidateManager, EmployeeManager employeeManager, TrainingManager trainingManager, Scanner scanner) {
        this.candidateManager = candidateManager;
        this.employeeManager = employeeManager;
        this.trainingManager = trainingManager;
        this.scanner = scanner;
    }

    /**
     * Assigns an interview to a candidate with specified details. If the candidate already has an interview,
     * the user is prompted to update the interview details.
     *
     * @param candidateId The ID of the candidate.
     * @param time        The time of the interview.
     * @param location    The location of the interview.
     */
    public void assignInterview(String candidateId, String time, String location) {
        Candidate candidate = candidateManager.findCandidateById(candidateId);
        if (candidate == null) {
            System.out.println("Candidate not found.");
            return;
        }

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

        saveOrUpdateInterview(candidateId, candidate.getName(), time, location);
        System.out.println("Interview assigned/updated successfully for " + candidate.getName());
    }

    /**
     * Checks if the candidate already has an interview assigned.
     *
     * @param candidateId The ID of the candidate.
     * @return The existing interview details if found, otherwise null.
     */
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
        return null;
    }

    /**
     * Saves or updates the interview details for a candidate.
     *
     * @param candidateId   The ID of the candidate.
     * @param candidateName The name of the candidate.
     * @param time          The time of the interview.
     * @param location      The location of the interview.
     */
    private void saveOrUpdateInterview(String candidateId, String candidateName, String time, String location) {
        List<String> allInterviews = new ArrayList<>();
        boolean updated = false;

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

        if (!updated) {
            allInterviews.add("Candidate ID: " + candidateId + ", Name: " + candidateName +
                              ", Time: " + time + ", Location: " + location);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INTERVIEW_FILE))) {
            for (String interview : allInterviews) {
                writer.write(interview);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to interview file: " + e.getMessage());
        }
    }

    /**
     * Hires a candidate, converting them to an employee, and removes the candidate record.
     *
     * @param candidateId The ID of the candidate to be hired.
     * @param salary      The salary of the new employee.
     * @param location    The location of the new employee.
     * @param role        The role of the new employee.
     */
    public void hireCandidate(String candidateId, double salary, String location, String role) {
        Candidate candidate = candidateManager.findCandidateById(candidateId);
        if (candidate == null) {
            System.out.println("Candidate not found.");
            return;
        }

        String employeeId = "EMP-" + (int) (Math.random() * 10000);
        Employee employee = new Employee(employeeId, candidate.getName(), location, salary, role);
        employeeManager.addEmployee(employee);

        candidateManager.removeCandidate(candidateId);

        System.out.println("Candidate " + candidate.getName() + " has been hired as Employee ID: " + employeeId);
    }

    /**
     * Displays all interviews saved in the system.
     */
    public void displayAllInterviews() {
        System.out.println("=== Scheduled Interviews ===");
        try (BufferedReader reader = new BufferedReader(new FileReader(INTERVIEW_FILE))) {
            String line;
            boolean hasInterviews = false;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                hasInterviews = true;
            }
            if (!hasInterviews) {
                System.out.println("No interviews found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading interview file: " + e.getMessage());
        }
    }

    /**
     * Creates a new training session with the specified details.
     *
     * @param topic   The topic of the training session.
     * @param trainer The name of the trainer conducting the session.
     * @param date    The date of the training session.
     */
    public void createTrainingSession(String topic, String trainer, String date) {
        String trainingId = "TRAIN-" + (int) (Math.random() * 10000);
        TrainingSession session = new TrainingSession(trainingId, topic, trainer, date, new ArrayList<>());
        trainingManager.addTrainingSession(session);

        System.out.println("Training session created successfully with ID: " + trainingId);
    }

    /**
     * Displays a menu for creating a predefined training session.
     */
    public void createTrainingSessionMenu() {
        System.out.println("Select a training topic:");
        List<String> topics = List.of("Workplace Safety", "Customer Service Excellence", "Diversity and Inclusion");
        for (int i = 0; i < topics.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, topics.get(i));
        }
        System.out.print("Enter your choice: ");
        int topicChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (topicChoice < 1 || topicChoice > topics.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
        String topic = topics.get(topicChoice - 1);

        System.out.print("Enter trainer name: ");
        String trainer = scanner.nextLine();
        System.out.print("Enter date (MM/DD/YYYY): ");
        String date = scanner.nextLine();

        String trainingId = "TRAIN-" + (int) (Math.random() * 10000);
        TrainingSession session = new TrainingSession(trainingId, topic, trainer, date, new ArrayList<>());
        trainingManager.addTrainingSession(session);

        System.out.println("Training session created successfully with ID: " + trainingId);
    }

    /**
     * Assigns a list of employees to a training session.
     *
     * @param trainingId  The ID of the training session.
     * @param employeeIds The list of employee IDs to be assigned to the session.
     */
    public void assignEmployeesToTraining(String trainingId, List<String> employeeIds) {
        TrainingSession session = trainingManager.findTrainingById(trainingId);

        if (session == null) {
            System.out.println("Training session not found.");
            return;
        }

        for (String employeeId : employeeIds) {
            Employee employee = employeeManager.getEmployeeMap().get(employeeId);
            if (employee != null) {
                session.addParticipant(employeeId);
                System.out.println("Employee " + employee.getName() + " assigned to training session " + trainingId);
            } else {
                System.out.println("Employee with ID " + employeeId + " not found.");
            }
        }

        trainingManager.saveTrainingsToFile();
    }

    /**
     * Displays all training sessions in the system.
     */
    public void viewAllTrainingSessions() {
        List<TrainingSession> sessions = trainingManager.getAllTrainingSessions();

        if (sessions.isEmpty()) {
            System.out.println("No training sessions available.");
            return;
        }

        System.out.println("\n--- Training Sessions ---");
        for (TrainingSession session : sessions) {
            System.out.println("Training ID: " + session.getTrainingId());
            System.out.println("Topic: " + session.getTopic());
            System.out.println("Trainer: " + session.getTrainer());
            System.out.println("Date: " + session.getDate());
            System.out.println("Participants: " + session.getParticipantIds());
            System.out.println("-----------------------");
        }
    }

    /**
     * Marks a training session as completed for all its participants.
     *
     * @param trainingId The ID of the training session.
     */
    public void markTrainingAsCompleted(String trainingId) {
        TrainingSession session = trainingManager.findTrainingById(trainingId);

        if (session == null) {
            System.out.println("Training session not found.");
            return;
        }

        for (String employeeId : session.getParticipantIds()) {
            Employee employee = employeeManager.getEmployeeMap().get(employeeId);
            if (employee != null) {
                if (!employee.getCompletedTrainingSessions().contains(trainingId)) {
                    employee.addCompletedTraining(trainingId);
                    System.out.println("Employee " + employee.getName() + " marked as completed for training " + trainingId);
                } else {
                    System.out.println("Training " + trainingId + " already marked as completed for " + employee.getName());
                }
            } else {
                System.out.println("Employee with ID " + employeeId + " not found.");
            }
        }

        employeeManager.saveEmployeesToFile();
    }
}