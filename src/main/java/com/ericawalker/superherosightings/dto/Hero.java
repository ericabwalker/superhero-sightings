/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author ericabenton
 */
public class Hero {
    
    private int heroID;
    
    @NotBlank(message = "Hero name must not be empty.")
    @Size(max = 50, message="Hero name must be less than 50 characters.")
    private String heroName;
    
    @NotBlank(message = "Hero description must not be empty.")
    @Size(max = 200, message="Hero description must be less than 200 characters.")
    private String heroDescription;
    
    @NotBlank(message = "Superpower must not be empty.")
    @Size(max = 50, message="Superpower must be less than 50 characters.")
    private String superPower;
    
    @NotNull(message="Please choose a status.")
    @Pattern(regexp="^S[VH]$", message="Please choose a status.")
    private String heroStatus;

    public int getHeroID() {
        return heroID;
    }

    public void setHeroID(int heroID) {
        this.heroID = heroID;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroDescription() {
        return heroDescription;
    }

    public void setHeroDescription(String heroDescription) {
        this.heroDescription = heroDescription;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    public String getHeroStatus() {
        return heroStatus;
    }

    public void setHeroStatus(String heroStatus) {
        this.heroStatus = heroStatus;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.heroID;
        hash = 11 * hash + Objects.hashCode(this.heroName);
        hash = 11 * hash + Objects.hashCode(this.heroDescription);
        hash = 11 * hash + Objects.hashCode(this.superPower);
        hash = 11 * hash + Objects.hashCode(this.heroStatus);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hero other = (Hero) obj;
        if (this.heroID != other.heroID) {
            return false;
        }
        if (!Objects.equals(this.heroName, other.heroName)) {
            return false;
        }
        if (!Objects.equals(this.heroDescription, other.heroDescription)) {
            return false;
        }
        if (!Objects.equals(this.superPower, other.superPower)) {
            return false;
        }
        if (!Objects.equals(this.heroStatus, other.heroStatus)) {
            return false;
        }
        return true;
    }
}
