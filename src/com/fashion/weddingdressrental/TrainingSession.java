package com.fashion.weddingdressrental;

import java.util.List;

public class TrainingSession {
    private String trainingId;
    private String topic;
    private String trainer;
    private String date;
    private List<String> participantIds; // List of Employee IDs

    public TrainingSession(String trainingId, String topic, String trainer, String date, List<String> participantIds) {
        this.trainingId = trainingId;
        this.topic = topic;
        this.trainer = trainer;
        this.date = date;
        this.participantIds = participantIds;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public String getTopic() {
        return topic;
    }

    public String getTrainer() {
        return trainer;
    }

    public String getDate() {
        return date;
    }

    public List<String> getParticipantIds() {
        return participantIds;
    }

    public void addParticipant(String employeeId) {
        participantIds.add(employeeId);
    }

    @Override
    public String toString() {
        return trainingId + "," + topic + "," + trainer + "," + date + "," + participantIds;
    }

    public String toCSV() {
        return trainingId + "," + topic + "," + trainer + "," + date + "," + String.join(";", participantIds);
    }
}