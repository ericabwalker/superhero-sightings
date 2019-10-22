/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ericabenton
 */
public class Org {
    
    private int orgID;
    private String orgName;
    private String orgDescrip;
    private String orgPhone;
    private String orgEmail;
    private String orgStatus;
    private Location location;
    private List<Hero> members = new ArrayList<>();

    
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Hero> getMembers() {
        return members;
    }

    public void setMembers(List<Hero> members) {
        this.members = members;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.orgID;
        hash = 29 * hash + Objects.hashCode(this.orgName);
        hash = 29 * hash + Objects.hashCode(this.orgDescrip);
        hash = 29 * hash + Objects.hashCode(this.orgPhone);
        hash = 29 * hash + Objects.hashCode(this.orgEmail);
        hash = 29 * hash + Objects.hashCode(this.orgStatus);
        hash = 29 * hash + Objects.hashCode(this.location);
        hash = 29 * hash + Objects.hashCode(this.members);
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
        final Org other = (Org) obj;
        if (this.orgID != other.orgID) {
            return false;
        }
        if (!Objects.equals(this.orgName, other.orgName)) {
            return false;
        }
        if (!Objects.equals(this.orgDescrip, other.orgDescrip)) {
            return false;
        }
        if (!Objects.equals(this.orgPhone, other.orgPhone)) {
            return false;
        }
        if (!Objects.equals(this.orgEmail, other.orgEmail)) {
            return false;
        }
        if (!Objects.equals(this.orgStatus, other.orgStatus)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.members, other.members)) {
            return false;
        }
        return true;
    }  
}
