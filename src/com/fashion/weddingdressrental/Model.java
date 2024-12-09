package com.fashion.weddingdressrental;

/**
 * Represents a model available for hiring.
 */
public class Model {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String nationality;
    private double height;
    private double weight;
    private String eyeColor;
    private String hairColor;
    private double bust;
    private double waist;
    private double hips;
    private String dressSize;
    private String skills;
    private int experienceYears;
    private String skinTone;
    private double pricePerDay;

    /**
     * Constructs a new Model instance.
     *
     * @param id              the unique identifier for the model
     * @param name            the name of the model
     * @param age             the age of the model
     * @param gender          the gender of the model
     * @param nationality     the nationality of the model
     * @param height          the height of the model in centimeters
     * @param weight          the weight of the model in kilograms
     * @param eyeColor        the eye color of the model
     * @param hairColor       the hair color of the model
     * @param bust            the bust measurement in centimeters (for female models)
     * @param waist           the waist measurement in centimeters
     * @param hips            the hips measurement in centimeters
     * @param dressSize       the dress size of the model
     * @param skills          the skills of the model (e.g., runway, editorial)
     * @param experienceYears the number of years of experience
     * @param skinTone        the skin tone of the model
     * @param pricePerDay     the price per day for hiring the model
     */
    public Model(String id, String name, int age, String gender, String nationality, double height, double weight,
                 String eyeColor, String hairColor, double bust, double waist, double hips, String dressSize,
                 String skills, int experienceYears, String skinTone, double pricePerDay) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.height = height;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.bust = bust;
        this.waist = waist;
        this.hips = hips;
        this.dressSize = dressSize;
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.skinTone = skinTone;
        this.pricePerDay = pricePerDay;
    }

    /**
     * Gets the ID of the model.
     *
     * @return the model ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the model.
     *
     * @return the model name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price per day for hiring the model.
     *
     * @return the price per day
     */
    public double getPricePerDay() {
        return pricePerDay;
    }

    @Override
    public String toString() {
        return String.format("%s | Name: %s | Age: %d | Gender: %s | Nationality: %s | Height: %.2f | Weight: %.2f | Bust: %.2f | Waist: %.2f | Hips: %.2f | DressSize: %s | Experience: %d years | Skills: %s",
                id, name, age, gender, nationality, height, weight, bust, waist, hips, dressSize, experienceYears, skills);
    }
}
