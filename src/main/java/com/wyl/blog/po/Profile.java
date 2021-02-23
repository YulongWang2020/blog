package com.wyl.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "t_profile")
public class Profile {
    @Id
    @GeneratedValue
    private Long id;

    private String skills;

    private String aboutMe;

    private String picture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", skills=" + skills +
                ", aboutMe='" + aboutMe + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
