/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author ericabenton
 */
public class Location {

    private int locationID;
    
    @NotBlank(message = "Location name must not be empty.")
    @Size(max = 50, message="Location name must be less than 50 characters.")
    private String locationName;
    
    @NotBlank(message = "Location description must not be empty.")
    @Size(max = 200, message="Location description must be less than 200 characters.")
    private String locationDescrip;
    
    @NotBlank(message = "Street address must not be empty.")
    @Size(max = 100, message="Street address must be less than 100 characters.")
    private String street1;
    
    @Size(max = 100, message="Street address must be less than 100 characters.")
    private String street2;
    
    @NotBlank(message = "City must not be empty.")
    @Size(max = 50, message="City must be less than 50 characters.")
    private String city;
    
    @NotBlank(message = "State must not be empty.")
    @Size(max = 2, message="State must be 2 characters.")
    private String state;
    
    @NotBlank(message = "Zip code must not be empty.")
    @Size(max = 5, message="Zip code must be 5 characters.")
    private String zipcode;
    
    @NotBlank(message = "Country must not be empty.")
    @Size(max = 80, message="Country must be less than 80 characters.")
    private String country;
    
    @NotBlank(message = "Latitude must not be empty.")
    @Size(max = 15, message="Latitude must be less than 15 characters.")
    private String locationLat;
    
    @NotBlank(message = "Longitude must not be empty.")
    @Size(max = 15, message="Longitude must be less than 15 characters.")
    private String locationLong;

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescrip() {
        return locationDescrip;
    }

    public void setLocationDescrip(String locationDescrip) {
        this.locationDescrip = locationDescrip;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.locationID;
        hash = 67 * hash + Objects.hashCode(this.locationName);
        hash = 67 * hash + Objects.hashCode(this.locationDescrip);
        hash = 67 * hash + Objects.hashCode(this.street1);
        hash = 67 * hash + Objects.hashCode(this.street2);
        hash = 67 * hash + Objects.hashCode(this.city);
        hash = 67 * hash + Objects.hashCode(this.state);
        hash = 67 * hash + Objects.hashCode(this.zipcode);
        hash = 67 * hash + Objects.hashCode(this.country);
        hash = 67 * hash + Objects.hashCode(this.locationLat);
        hash = 67 * hash + Objects.hashCode(this.locationLong);
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
        final Location other = (Location) obj;
        if (this.locationID != other.locationID) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.locationDescrip, other.locationDescrip)) {
            return false;
        }
        if (!Objects.equals(this.street1, other.street1)) {
            return false;
        }
        if (!Objects.equals(this.street2, other.street2)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zipcode, other.zipcode)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.locationLat, other.locationLat)) {
            return false;
        }
        if (!Objects.equals(this.locationLong, other.locationLong)) {
            return false;
        }
        return true;
    }

}
