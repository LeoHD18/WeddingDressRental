package com.fashion.weddingdressrental;

public class Candidate {
    private String candidateId;
    private String name;
    private String email;
    private String phone;
    private String resume;
    private double interviewPoints;

    public Candidate(String candidateId, String name, String email, String phone, String resume) {
        this.candidateId = candidateId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.resume = resume;
        this.interviewPoints = 0.0; // Default points before interview
    }

    // Getters and Setters
    public String getCandidateId() {
        return candidateId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getResume() {
        return resume;
    }

    public double getInterviewPoints() {
        return interviewPoints;
    }

    public void setInterviewPoints(double interviewPoints) {
        this.interviewPoints = interviewPoints;
    }

    @Override
    public String toString() {
        return "Candidate{" +
               "ID='" + candidateId + '\'' +
               ", Name='" + name + '\'' +
               ", Email='" + email + '\'' +
               ", Phone='" + phone + '\'' +
               ", Resume='" + resume + '\'' +
               ", Interview Points=" + interviewPoints +
               '}';
    }

    public String toCSV() {
        return candidateId + "," + name + "," + email + "," + phone + "," + resume + "," + interviewPoints;
    }
}
