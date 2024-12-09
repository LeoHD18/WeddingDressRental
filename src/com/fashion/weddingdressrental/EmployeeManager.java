package com.fashion.weddingdressrental;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeManager {
    private static final String FILE_PATH = "employees.txt"; // Define the file path here
    private TrainingManager trainingManager;
    //private Map<String, Employee> employees;
    private Map<String, Employee> employees = new HashMap<>();

    public EmployeeManager(TrainingManager trainingManager) {
        this.trainingManager = trainingManager;
        employees = new HashMap<>();
        loadEmployeesFromFile();
    }

    private void loadEmployeesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6); // Adjust to accommodate the extra field
                if (parts.length >= 5) {
                    String employeeId = parts[0];
                    String name = parts[1];
                    String location = parts[2];
                    double salary = Double.parseDouble(parts[3]);
                    String role = parts[4];
                    List<String> completedTrainings = parts.length == 6 && !parts[5].isEmpty()
                            ? List.of(parts[5].split(";"))
                            : new ArrayList<>();
    
                    Employee employee = new Employee(employeeId, name, location, salary, role);
                    employee.getCompletedTrainingSessions().addAll(completedTrainings);
                    employees.put(employeeId, employee);
                }
            }
            System.out.println("Employees loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading employees from file: " + e.getMessage());
        }
    }

    public void saveEmployeesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Employee employee : employees.values()) {
                writer.write(employee.toCSV());
                writer.newLine();
            }
            System.out.println("Employees saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving employees to file: " + e.getMessage());
        }
    }

    public void addEmployee(Employee employee) {
        employees.put(employee.getEmployeeId(), employee);
        saveEmployeesToFile();
    }

    public void displayEmployees() {
        System.out.println("\n--- Employee List ---");
        employees.values().forEach(System.out::println);
    }

    public Map<String, Employee> getEmployeeMap() {
        return employees;
    }
}


// public class EmployeeManager {
//     private TrainingManager trainingManager;
//     private Map<String, Employee> employees;

//     public EmployeeManager(TrainingManager trainingManager) {
//         this.trainingManager = trainingManager;
//         employees = new HashMap<>();
//         loadEmployeesFromFile();
//     }

//     private void loadEmployeesFromFile() {
//         try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//             String line;
//             while ((line = reader.readLine()) != null) {
//                 String[] parts = line.split(",", 6); // Adjust to accommodate the extra field
//                 if (parts.length >= 5) {
//                     String employeeId = parts[0];
//                     String name = parts[1];
//                     String location = parts[2];
//                     double salary = Double.parseDouble(parts[3]);
//                     String role = parts[4];
//                     List<String> completedTrainings = parts.length == 6 && !parts[5].isEmpty()
//                             ? List.of(parts[5].split(";"))
//                             : new ArrayList<>();
    
//                     Employee employee = new Employee(employeeId, name, location, salary, role);
//                     employee.getCompletedTrainingSessions().addAll(completedTrainings);
//                     employees.put(employeeId, employee);
//                 }
//             }
//             System.out.println("Employees loaded successfully.");
//         } catch (IOException e) {
//             System.out.println("Error loading employees from file: " + e.getMessage());
//         }
//     }

//     // Save employees to file
//     public void saveEmployeesToFile() {
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
//             for (Employee employee : employees.values()) {
//                 writer.write(employee.toCSV());
//                 writer.newLine();
//             }
//             System.out.println("Employees saved successfully.");
//         } catch (IOException e) {
//             System.out.println("Error saving employees to file: " + e.getMessage());
//         }
//     }

//     public void addEmployee(Employee employee) {
//         employees.put(employee.getEmployeeId(), employee);
//         saveEmployeesToFile();
//     }

//     public void displayEmployees() {
//         System.out.println("\n--- Employee List ---");
//         employees.values().forEach(System.out::println); // Automatically calls the updated toString method of Employee
//     }

//     public Map<String, Employee> getEmployeeMap() {
//         return employees;
//     }

//     public void viewAssignedTrainings(Employee employee) {
//         System.out.println("--- Assigned Trainings ---");
//         List<String> completedTrainings = employee.getCompletedTrainingSessions();
    
//         trainingManager.getAllTrainingSessions().stream()
//             .filter(session -> session.getParticipantIds().contains(employee.getEmployeeId()))
//             .forEach(session -> {
//                 System.out.printf("Training ID: %s\nTopic: %s\nTrainer: %s\nDate: %s\nStatus: %s\n\n",
//                     session.getTrainingId(), session.getTopic(), session.getTrainer(), session.getDate(),
//                     completedTrainings.contains(session.getTrainingId()) ? "Completed" : "Pending");
//             });
//     }
// }
