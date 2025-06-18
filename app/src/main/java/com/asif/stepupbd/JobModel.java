package com.asif.stepupbd;

public class JobModel {
    int id;
    String description, skill, experience, duration, postedBy;

    public JobModel(int id, String description, String skill, String experience, String duration, String postedBy) {
        this.id = id;
        this.description = description;
        this.skill = skill;
        this.experience = experience;
        this.duration = duration;
        this.postedBy = postedBy;
    }

    // Getters (optional) ...
}
