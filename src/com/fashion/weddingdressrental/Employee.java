package com.fashion.weddingdressrental;

public class Employee {
    private String employeeId;
    private String name;
    private String location;
    private double salary;

    public Employee(String employeeId, String name, String location, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.location = location;
        this.salary = salary;
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

    @Override
    public String toString() {
        return "Employee{" +
               "ID='" + employeeId + '\'' +
               ", Name='" + name + '\'' +
               ", Location='" + location + '\'' +
               ", Salary=$" + salary +
               '}';
    }

    public String toCSV() {
        return employeeId + "," + name + "," + location + "," + salary;
    }

}
