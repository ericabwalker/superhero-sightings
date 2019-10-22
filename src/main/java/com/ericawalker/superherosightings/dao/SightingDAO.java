/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dto.Hero;
import com.ericawalker.superherosightings.dto.Location;
import com.ericawalker.superherosightings.dto.Sighting;
import java.util.List;

/**
 *
 * @author ericabenton
 */
public interface SightingDAO {
    
    Sighting getSightingByID(int sightingID);
    
    List<Sighting> getMostRecentSightings();
    
    List<Sighting> getAllSightings();
    
    Sighting addSighting(Sighting sighting);
    
    void updateSighting(Sighting sighting);
    
    void deleteSightingById(int sightingID);
    
    List<Sighting> getSightingsForHero(Hero hero);
    
    List<Sighting> getSightingsForLocation(Location location);
    
}
