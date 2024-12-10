package com.fashion.weddingdressrental;

import java.util.List;
import java.util.Scanner;

/**
 * Provides an interface for employees to interact with the system.
 * Employees can view assigned training sessions and complete them through this portal.
 */
public class EmployeePortal {
    private TrainingManager trainingManager;
    private EmployeeManager employeeManager;

    /**
     * Constructs an EmployeePortal instance.
     * 
     * @param trainingManager The manager responsible for handling training sessions.
     * @param employeeManager The manager responsible for handling employee records.
     */
    public EmployeePortal(TrainingManager trainingManager, EmployeeManager employeeManager) {
        this.trainingManager = trainingManager;
        this.employeeManager = employeeManager;
    }

    /**
     * Displays the training sessions assigned to an employee, including their completion status.
     * 
     * @param employee The employee whose assigned training sessions are to be displayed.
     */
    public void viewAssignedTrainings(Employee employee) {
        System.out.println("\n--- Assigned Trainings ---");
        List<String> completedTrainings = employee.getCompletedTrainingSessions();

        trainingManager.getAllTrainingSessions().stream()
            .filter(session -> session.getParticipantIds().contains(employee.getEmployeeId()))
            .forEach(session -> {
                System.out.printf("Training ID: %s\nTopic: %s\nTrainer: %s\nDate: %s\nStatus: %s\n\n",
                    session.getTrainingId(), session.getTopic(), session.getTrainer(), session.getDate(),
                    completedTrainings.contains(session.getTrainingId()) ? "Completed" : "Pending");
            });
    }

    /**
     * Allows an employee to complete a training session by passing an assessment and providing acknowledgment.
     * 
     * @param employee The employee completing the training session.
     * @param trainingId The ID of the training session to be completed.
     */
    public void completeTraining(Employee employee, String trainingId) {
        TrainingSession session = trainingManager.findTrainingById(trainingId);

        if (session == null) {
            System.out.println("Training session not found.");
            return;
        }

        // Check if the employee has already completed this training
        if (employee.getCompletedTrainingSessions().contains(trainingId)) {
            System.out.println("You have already completed this training: " + session.getTopic());
            return;
        }

        System.out.println("\n--- Training: " + session.getTopic() + " ---");
        System.out.println("Do you want to view the training material before starting the assessment? (Y/N): ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim().toUpperCase();

        if (choice.equals("Y")) {
            displayTrainingMaterial(session.getTopic());
        }

        // Assessment
        System.out.println("\n--- Training Assessment: " + session.getTopic() + " ---");
        System.out.println("Instructions: Please answer the following questions carefully. Type the correct option number.");
        System.out.println("A passing score is 4 out of 5. Good luck!");

        int score = 0;

        System.out.println("\nQ1: What is the primary focus of workplace safety?");
        System.out.println("1. Protecting employees from hazards");
        System.out.println("2. Increasing productivity");
        System.out.println("3. Reducing training costs");
        System.out.print("Your answer: ");
        if (scanner.nextInt() == 1) score++;

        System.out.println("\nQ2: Who is responsible for ensuring workplace safety?");
        System.out.println("1. HR");
        System.out.println("2. Everyone");
        System.out.println("3. Employees only");
        System.out.print("Your answer: ");
        if (scanner.nextInt() == 2) score++;

        System.out.println("\nQ3: What should you do in case of an emergency?");
        System.out.println("1. Follow safety protocols");
        System.out.println("2. Leave the building");
        System.out.println("3. Call a friend");
        System.out.print("Your answer: ");
        if (scanner.nextInt() == 1) score++;

        System.out.println("\nQ4: Why are safety drills important?");
        System.out.println("1. To improve response times during emergencies");
        System.out.println("2. To impress the management");
        System.out.println("3. To increase employee morale");
        System.out.print("Your answer: ");
        if (scanner.nextInt() == 1) score++;

        System.out.println("\nQ5: What should you do if you notice a safety hazard?");
        System.out.println("1. Report it immediately");
        System.out.println("2. Ignore it");
        System.out.println("3. Handle it yourself");
        System.out.print("Your answer: ");
        if (scanner.nextInt() == 1) score++;

        // Acknowledgment
        scanner.nextLine(); // Consume newline
        System.out.println("\n--- Acknowledgment ---");
        System.out.println("Please type 'I agree' to confirm you have read and understood the training material:");
        String acknowledgment = scanner.nextLine();

        if (!acknowledgment.equalsIgnoreCase("I agree")) {
            System.out.println("Acknowledgment not completed. Training not marked as complete.");
            return;
        }

        // Assessment results
        System.out.println("\n--- Assessment Results ---");
        System.out.println("Your score: " + score + "/5");

        if (score >= 4) {
            System.out.println("Congratulations! You passed the assessment.");
            employee.addCompletedTraining(trainingId);
            System.out.println("You have successfully completed the training: " + session.getTopic());
            employeeManager.saveEmployeesToFile();
        } else {
            System.out.println("You did not achieve the passing score. Please retake the training.");
        }
    }

    /**
     * Displays the training material for a specific topic.
     * 
     * @param topic The topic of the training session.
     */
    private void displayTrainingMaterial(String topic) {
        System.out.println("\n--- Training Material: " + topic + " ---");
        if (topic.equalsIgnoreCase("Workplace Safety")) {
            System.out.println("1. Always follow safety protocols.\n2. Report hazards immediately.\n3. Participate in safety drills.\n4. Use personal protective equipment (PPE).\n5. Keep emergency exits clear.");
        } else if (topic.equalsIgnoreCase("Customer Service Excellence")) {
            System.out.println("1. Greet customers warmly.\n2. Listen actively to their needs.\n3. Resolve issues efficiently.\n4. Exceed expectations.\n5. Gather and use feedback for improvement.");
        } else if (topic.equalsIgnoreCase("Diversity and Inclusion")) {
            System.out.println("1. Respect differences.\n2. Promote inclusivity.\n3. Avoid bias in decision-making.\n4. Create a welcoming environment.\n5. Celebrate cultural diversity.");
        } else {
            System.out.println("No material available for this topic.");
        }
    }
}