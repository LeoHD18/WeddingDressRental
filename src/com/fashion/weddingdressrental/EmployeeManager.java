package com.fashion.weddingdressrental;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages all employees in the Wedding Dress Rental system.
 * Responsible for loading, saving, and managing employee records.
 */
public class EmployeeManager {
    private static final String FILE_PATH = "employees.txt";
    private TrainingManager trainingManager;
    private Map<String, Employee> employees = new HashMap<>();

    /**
     * Constructs an EmployeeManager object.
     * 
     * @param trainingManager The TrainingManager to manage training sessions.
     */
    public EmployeeManager(TrainingManager trainingManager) {
        this.trainingManager = trainingManager;
        loadEmployeesFromFile();
    }

    /**
     * Loads employees from a file into the manager.
     */
    private void loadEmployeesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
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

    /**
     * Saves all employee records to a file.
     */
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

    /**
     * Adds an employee to the manager and saves the updated records to the file.
     * 
     * @param employee The employee to add.
     */
    public void addEmployee(Employee employee) {
        employees.put(employee.getEmployeeId(), employee);
        saveEmployeesToFile();
    }

    /**
     * Displays all employee records in the manager.
     */
    public void displayEmployees() {
        System.out.println("\n--- Employee List ---");
        employees.values().forEach(System.out::println);
    }

    /**
     * Gets the map of all employees.
     * 
     * @return A map containing employee ID as keys and Employee objects as values.
     */
    public Map<String, Employee> getEmployeeMap() {
        return employees;
    }
}