package com.fashion.weddingdressrental;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the Wedding Dress Rental system.
 * Each employee has attributes such as ID, name, location, salary, role, and a list of completed training sessions.
 */
public class Employee {
    private String employeeId;
    private String name;
    private String location;
    private double salary;
    private String role;
    private List<String> completedTrainingSessions;

    /**
     * Constructs an Employee object with the given attributes.
     * 
     * @param employeeId The unique ID of the employee.
     * @param name The name of the employee.
     * @param location The work location of the employee.
     * @param salary The salary of the employee.
     * @param role The role or job title of the employee.
     */
    public Employee(String employeeId, String name, String location, double salary, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.location = location;
        this.salary = salary;
        this.role = role;
        this.completedTrainingSessions = new ArrayList<>();
    }

    /**
     * Gets the employee's ID.
     * 
     * @return The employee ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Gets the name of the employee.
     * 
     * @return The employee's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the work location of the employee.
     * 
     * @return The employee's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the salary of the employee.
     * 
     * @return The employee's salary.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the employee.
     * 
     * @param salary The new salary to set.
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the role of the employee.
     * 
     * @return The employee's role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the employee.
     * 
     * @param role The new role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the list of completed training sessions by the employee.
     * 
     * @return The list of completed training sessions.
     */
    public List<String> getCompletedTrainingSessions() {
        return completedTrainingSessions;
    }

    /**
     * Adds a completed training session to the employee's record.
     * 
     * @param trainingId The ID of the completed training session.
     */
    public void addCompletedTraining(String trainingId) {
        completedTrainingSessions.add(trainingId);
    }

    /**
     * Converts the employee's attributes to a string representation.
     * 
     * @return A string containing the employee's details.
     */
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

    /**
     * Converts the employee's attributes to a CSV (Comma-Separated Values) format.
     * 
     * @return A CSV string representing the employee's details.
     */
    public String toCSV() {
        return employeeId + "," + name + "," + location + "," + salary + "," + role + "," +
                String.join(";", completedTrainingSessions);
    }
}