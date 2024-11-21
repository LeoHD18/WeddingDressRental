package com.fashion.weddingdressrental;

public class HR {
    private CandidateManager candidateManager;
    private EmployeeManager employeeManager;

    public HR(CandidateManager candidateManager, EmployeeManager employeeManager) {
        this.candidateManager = candidateManager;
        this.employeeManager = employeeManager;
    }

    // Hire a candidate and add to employees
    public void hireCandidate(String candidateId, double salary, String location, String role) {
        Candidate candidate = candidateManager.findCandidateById(candidateId);
        if (candidate == null) {
            System.out.println("Candidate not found.");
            return;
        }

        String employeeId = "EMP-" + (int) (Math.random() * 10000);
        Employee employee = new Employee(employeeId, candidate.getName(), location, salary, role);
        employeeManager.addEmployee(employee);

        // Remove the candidate after hiring
        candidateManager.removeCandidate(candidateId);

        System.out.println("Candidate " + candidate.getName() + " has been hired as Employee ID: " + employeeId);
    }
}
