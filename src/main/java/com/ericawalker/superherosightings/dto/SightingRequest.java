/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dto;

import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author ericabenton
 */
public class SightingRequest {

    private int sightingID;
    
    @NotNull(message="Please enter a date, YYYY/MM/DD")
    @DateTimeFormat(pattern="yyyy/MM/dd")
    @PastOrPresent
    private Date sightingDate;
    
    @NotNull(message="Please choose a hero.")
    @Min(value=1, message="Please choose a hero.")
    private Integer heroID;
    
    @NotNull(message="Please choose a location.")
    @Min(value=1, message="Please choose a location.")
    private Integer locationID;

    public int getSightingID() {
        return sightingID;
    }

    public void setSightingID(int sightingID) {
        this.sightingID = sightingID;
    }

    public Date getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(Date sightingDate) {
        this.sightingDate = sightingDate;
    }

    public Integer getHeroID() {
        return heroID;
    }

    public void setHeroID(Integer heroID) {
        this.heroID = heroID;
    }

    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }
    
    public static SightingRequest newFromSighting(Sighting sighting) {
        SightingRequest sightingRequest = new SightingRequest();
        sightingRequest.setSightingID(sighting.getSightingID());
        sightingRequest.setSightingDate(sighting.getSightingDate());
        sightingRequest.setHeroID(sighting.getHero() != null ? sighting.getHero().getHeroID() : null);
        sightingRequest.setLocationID(sighting.getLocation() != null ? sighting.getLocation().getLocationID() : null);
        return sightingRequest;
    }
}
