package com.example.virtualmagic;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity
public class VirtualModel implements Serializable {

    @NonNull
    @PrimaryKey
    String id1;


    String name;
    String strength;
    String speed;
    String power;
    String eye;
    String hair;
    String image1post;

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getImage1post() {
        return image1post;
    }

    public void setImage1post(String image1post) {
        this.image1post = image1post;
    }


    /*public VirtualModel(String id1, String name, String strength, String speed, String power, String eye, String hair, String image1post) {
        this.id1 = id1;
        this.name = name;
        this.strength = strength;
        this.speed = speed;
        this.power = power;
        this.eye = eye;
        this.hair = hair;
        this.image1post = image1post;
    }*/

    public VirtualModel()
    {}
}
