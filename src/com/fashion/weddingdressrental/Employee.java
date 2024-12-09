package com.fashion.weddingdressrental;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String employeeId;
    private String name;
    private String location;
    private double salary;
    private String role;
    private List<String> completedTrainingSessions;

    public Employee(String employeeId, String name, String location, double salary, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.location = location;
        this.salary = salary;
        this.role = role;
        this.completedTrainingSessions = new ArrayList<>();
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getCompletedTrainingSessions() {
        return completedTrainingSessions;
    }

    public void addCompletedTraining(String trainingId) {
        completedTrainingSessions.add(trainingId);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Employee{")
               .append("ID='").append(employeeId).append('\'')
               .append(", Name='").append(name).append('\'')
               .append(", Location='").append(location).append('\'')
               .append(", Salary=$").append(salary)
               .append(", Role='").append(role).append('\'')
               .append("}\n");
        if (!completedTrainingSessions.isEmpty()) {
            builder.append("  Completed Trainings: ").append(completedTrainingSessions).append("\n");
        }
        return builder.toString();
    }

    // public String toCSV() {
    //     return employeeId + "," + name + "," + location + "," + salary + "," + role;
    // }
    
    public String toCSV() {
        return employeeId + "," + name + "," + location + "," + salary + "," + role + "," +
                String.join(";", completedTrainingSessions);
    }
}