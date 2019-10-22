/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author ericabenton
 */
public class OrgRequest {
    
    private int orgID;
    
    @NotBlank(message = "Org name must not be empty.")
    @Size(max = 50, message="Org name must be less than 50 characters.")
    private String orgName;
    
    @NotBlank(message = "Org description must not be empty.")
    @Size(max = 200, message="Org name must be less than 200 characters.")
    private String orgDescrip;
    
    @Size(max = 15, message="Org phone number must be less than 15 characters.")
    private String orgPhone;
    
    @Email(message="Org email need to be a valid email address.")
    @Size(max = 50, message="Org email must be less than 50 characters.")
    private String orgEmail;
    
    @NotNull(message="Please choose a status.")
    @Pattern(regexp="^S[VH]$", message="Please choose a status.")
    private String orgStatus;
    
    @NotNull(message="Please choose a location.")
    @Min(value=1, message="Please choose a location.")
    private Integer locationID;
    
    @Size(min=1, message="Please choose at least one Hero.")
    private List<Integer> heroIDs = new ArrayList<>();

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescrip() {
        return orgDescrip;
    }

    public void setOrgDescrip(String orgDescrip) {
        this.orgDescrip = orgDescrip;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }

    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    public List<Integer> getHeroIDs() {
        return heroIDs;
    }

    public void setHeroIDs(List<Integer> heroIDs) {
        this.heroIDs = heroIDs;
    }

    public static OrgRequest newFromOrg(Org org) {
        OrgRequest orgRequest = new OrgRequest();
        orgRequest.setOrgID(org.getOrgID());
        orgRequest.setOrgName(org.getOrgName());
        orgRequest.setOrgDescrip(org.getOrgDescrip());
        orgRequest.setOrgEmail(org.getOrgEmail());
        orgRequest.setOrgPhone(org.getOrgPhone());
        orgRequest.setOrgStatus(org.getOrgStatus());
        orgRequest.setLocationID(org.getLocation() != null ? org.getLocation().getLocationID() : null);
        List<Integer> heroIDs = new LinkedList<>();
        for(Hero m : org.getMembers()) {
            heroIDs.add(m.getHeroID());
        }
        orgRequest.setHeroIDs(heroIDs);
        return orgRequest;
    }
}
