package com.fashion.weddingdressrental;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fashion.weddingdressrental.DressCustomization.Customization;

public class EmployeeManager {
    private static final String FILE_PATH = "employees.txt";
    private Map<String, Employee> employees;

    public EmployeeManager() {
        employees = new HashMap<>();
        loadEmployeesFromFile();
    }

    // Load employees from file
    private void loadEmployeesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String employeeId = parts[0];
                    String name = parts[1];
                    String location = parts[2];
                    double salary = Double.parseDouble(parts[3]);
                    String role = parts[4];

                    Employee employee = new Employee(employeeId, name, location, salary, role);
                    employees.put(employeeId, employee);
                }
            }
            System.out.println("Employees loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading employees from file: " + e.getMessage());
        }
    }

    // Save employees to file
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

	public Collection<Customization> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}
}
