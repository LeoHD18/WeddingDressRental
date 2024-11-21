package com.fashion.weddingdressrental;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HR {
    private CandidateManager candidateManager;
    private EmployeeManager employeeManager;

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

        writeInterviewInfoToFile(candidate, time, location);
        System.out.println("Interview assigned to " + candidate.getName() + " at " + location + " on " + time);
    }

    // Hire a candidate and add to employees
    public void hireCandidate(String candidateId, double salary, String location) {
        Candidate candidate = candidateManager.findCandidateById(candidateId);
        if (candidate == null) {
            System.out.println("Candidate not found.");
            return;
        }

        String employeeId = "EMP-" + (int) (Math.random() * 10000);
        Employee employee = new Employee(employeeId, candidate.getName(), location, salary);
        employeeManager.addEmployee(employee);

        System.out.println("Candidate " + candidate.getName() + " has been hired as Employee ID: " + employeeId);
    }

    // Write interview details to file
    private void writeInterviewInfoToFile(Candidate candidate, String time, String location) {
        String interviewFilePath = "interviews.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(interviewFilePath, true))) {
            writer.write("Candidate ID: " + candidate.getCandidateId() + ", Name: " + candidate.getName() +
                         ", Time: " + time + ", Location: " + location);
            writer.newLine();
            System.out.println("Interview details saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving interview details: " + e.getMessage());
        }
    }
}
