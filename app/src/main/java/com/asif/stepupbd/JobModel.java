package com.asif.stepupbd;

public class JobModel {
    private int id;
    private String image;
    private String description;
    private String skill;
    private String experience;
    private String duration;
    private String postedBy;  // Optional: useful if you need to filter by poster

    public JobModel(int id, String image, String description, String skill, String experience, String duration) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.skill = skill;
        this.experience = experience;
        this.duration = duration;
    }

    public JobModel(int id, String image, String description, String skill, String experience, String duration, String postedBy) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.skill = skill;
        this.experience = experience;
        this.duration = duration;
        this.postedBy = postedBy;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getSkill() {
        return skill;
    }

    public String getExperience() {
        return experience;
    }

    public String getDuration() {
        return duration;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}
