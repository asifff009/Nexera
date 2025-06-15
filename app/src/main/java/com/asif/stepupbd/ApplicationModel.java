package com.asif.stepupbd;

public class ApplicationModel {
    String email, skill, experience, interest;

    public ApplicationModel(String email, String skill, String experience, String interest) {
        this.email = email;
        this.skill = skill;
        this.experience = experience;
        this.interest = interest;
    }

    public String getEmail() {
        return email;
    }

    public String getSkill() {
        return skill;
    }

    public String getExperience() {
        return experience;
    }

    public String getInterest() {
        return interest;
    }
}
