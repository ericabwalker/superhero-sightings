/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dto.Location;
import java.util.List;

/**
 *
 * @author ericabenton
 */
public interface LocationDAO {
    
    Location getLocationByID(int locationID);
    
    List<Location> getAllLocations();
    
    Location addLocation(Location location);
    
    void updateLocation(Location location);
    
    void deleteLocationByID(int locationID);
    
}
