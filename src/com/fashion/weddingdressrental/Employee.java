package com.fashion.weddingdressrental;

public class Employee {
    private String employeeId;
    private String name;
    private String location;
    private double salary;
    private String role;

    public Employee(String employeeId, String name, String location, double salary, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.location = location;
        this.salary = salary;
        this.role = role;
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

    @Override
    public String toString() {
        return "Employee{" +
               "ID='" + employeeId + '\'' +
               ", Name='" + name + '\'' +
               ", Location='" + location + '\'' +
               ", Salary=$" + salary +
               ", Role='" + role + '\'' +
               '}';
    }

    public String toCSV() {
        return employeeId + "," + name + "," + location + "," + salary + "," + role;
    }
}
