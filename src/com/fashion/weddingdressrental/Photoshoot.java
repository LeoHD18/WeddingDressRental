package com.fashion.weddingdressrental;

import java.util.List;

/**
 * Represents a photoshoot session including selected models, photographer, dress, and session details.
 */
public class Photoshoot {
    private String photoshootId;
    private List<Model> models;
    private Employee photographer;
    private List<InventoryItem> dress;
    private String location;
    private String time;
    private String style;
    private String mood;
    private String output;
    private String purpose;
    private String lightingSetup;
    private String cameraSettings;
    private String resolution;
    private double cost;

    /**
     * Constructs a new Photoshoot instance.
     *
     * @param photoshootId   Unique identifier for the photoshoot.
     * @param models         List of selected models.
     * @param photographer   The photographer assigned to the photoshoot.
     * @param dress          The dress chosen for the photoshoot.
     * @param location       Location of the photoshoot.
     * @param time           Scheduled time for the photoshoot.
     * @param style          Style of the photoshoot (e.g., portrait, landscape).
     * @param mood           Mood of the photoshoot (e.g., formal, casual).
     * @param output         Desired output format (e.g., JPEG, RAW).
     * @param purpose        Purpose of the photoshoot.
     * @param lightingSetup  Lighting configuration used.
     * @param cameraSettings Camera settings for the session.
     * @param resolution     Desired resolution for the photos.
     * @param cost           Total cost of the photoshoot.
     */
    public Photoshoot(String photoshootId, List<Model> models, Employee photographer, List<InventoryItem> dress,
                      String location, String time, String style, String mood, String output, String purpose,
                      String lightingSetup, String cameraSettings, String resolution, double cost) {
        this.photoshootId = photoshootId;
        this.models = models;
        this.photographer = photographer;
        this.dress = dress;
        this.location = location;
        this.time = time;
        this.style = style;
        this.mood = mood;
        this.output = output;
        this.purpose = purpose;
        this.lightingSetup = lightingSetup;
        this.cameraSettings = cameraSettings;
        this.resolution = resolution;
        this.cost = cost;
    }

    /**
     * Converts the photoshoot details into a CSV format string for file storage.
     *
     * @return A CSV string representation of the photoshoot.
     */
    public String toCSV() {
        return photoshootId + "," +
               models.stream().map(Model::getId).reduce((m1, m2) -> m1 + ";" + m2).orElse("") + "," +
               photographer.getEmployeeId() + "," +
               dress.stream().map(InventoryItem::getDressId).reduce((d1, d2) -> d1 + ";" + d2).orElse("") + "," +
               location + "," + time + "," + style + "," + mood + "," + output + "," + purpose + "," +
               lightingSetup + "," + cameraSettings + "," + resolution + "," + cost;
    }

    @Override
    public String toString() {
        return "Photoshoot{" +
               "ID='" + photoshootId + '\'' +
               ", Models=" + models +
               ", Photographer=" + photographer.getName() +
               ", Dress=" + dress +
               ", Location='" + location + '\'' +
               ", Time='" + time + '\'' +
               ", Style='" + style + '\'' +
               ", Mood='" + mood + '\'' +
               ", Output='" + output + '\'' +
               ", Purpose='" + purpose + '\'' +
               ", LightingSetup='" + lightingSetup + '\'' +
               ", CameraSettings='" + cameraSettings + '\'' +
               ", Resolution='" + resolution + '\'' +
               ", Cost=" + cost +
               '}';
    }
}
