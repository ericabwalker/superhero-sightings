/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dto.Hero;
import com.ericawalker.superherosightings.dto.Location;
import com.ericawalker.superherosightings.dto.Org;
import com.ericawalker.superherosightings.dto.Sighting;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ericabenton
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDAOImplTest {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    OrgDAO orgDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    SightingDAO sightingDAO;

    @Autowired
    HeroDAO heroDAO;

    public LocationDAOImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Location> locations = locationDAO.getAllLocations();
        for (Location location : locations) {
            locationDAO.deleteLocationByID(location.getLocationID());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setLocationName("US Bank");
        location.setLocationDescrip("The bank on the corner");
        location.setStreet1("1234 Main Street");
        location.setCity("Cincinnati");
        location.setState("OH");
        location.setZipcode("12345");
        location.setCountry("USA");
        location.setLocationLat("123-ad-123");
        location.setLocationLong("321-da-211");
        locationDAO.addLocation(location);

        Location fromDAO = locationDAO.getLocationByID(location.getLocationID());

        assertEquals(location, fromDAO);
    }

    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setLocationName("US Bank");
        location.setLocationDescrip("The bank on the corner");
        location.setStreet1("1234 Main Street");
        location.setCity("Cincinnati");
        location.setState("OH");
        location.setZipcode("12345");
        location.setCountry("USA");
        location.setLocationLat("123-ad-123");
        location.setLocationLong("321-da-211");
        locationDAO.addLocation(location);

        Location location2 = new Location();
        location2.setLocationName("PNC Bank");
        location2.setLocationDescrip("The bank on the other corner");
        location2.setStreet1("1234 Market Street");
        location2.setCity("Dayton");
        location2.setState("OH");
        location2.setZipcode("54321");
        location2.setCountry("USA");
        location2.setLocationLat("456-ad-123");
        location2.setLocationLong("893-da-211");
        locationDAO.addLocation(location2);

        List<Location> locations = locationDAO.getAllLocations();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setLocationName("US Bank");
        location.setLocationDescrip("The bank on the corner");
        location.setStreet1("1234 Main Street");
        location.setCity("Cincinnati");
        location.setState("OH");
        location.setZipcode("12345");
        location.setCountry("USA");
        location.setLocationLat("123-ad-123");
        location.setLocationLong("321-da-211");
        locationDAO.addLocation(location);

        Location fromDAO = locationDAO.getLocationByID(location.getLocationID());
        assertEquals(location, fromDAO);

        location.setLocationName("A new name");
        locationDAO.updateLocation(location);

        assertNotEquals(location, fromDAO);

        fromDAO = locationDAO.getLocationByID(location.getLocationID());
        assertEquals(location, fromDAO);
    }

    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setLocationName("US Bank");
        location.setLocationDescrip("The bank on the corner");
        location.setStreet1("1234 Main Street");
        location.setCity("Cincinnati");
        location.setState("OH");
        location.setZipcode("12345");
        location.setCountry("USA");
        location.setLocationLat("123-ad-123");
        location.setLocationLong("321-da-211");
        locationDAO.addLocation(location);

        int testLocID = location.getLocationID();

        Org org = new Org();
        org.setOrgName("Test Org");
        org.setOrgDescrip("An org");
        org.setOrgPhone("123-123-1234");
        org.setOrgEmail("test@email.com");
        org.setOrgStatus("SH");
        org.setLocation(location);
        orgDAO.addOrg(org);

        Hero hero = new Hero();
        hero.setHeroName("Test Spiderman");
        hero.setHeroDescription("A man that is a spider");
        hero.setSuperPower("Can climb");
        hero.setHeroStatus("SH");
        hero = heroDAO.addHero(hero);

        Sighting sighting = new Sighting();
        sighting.setSightingDate(new Date());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDAO.addSighting(sighting);

        Org orgFromDAO = orgDAO.getOrgByID(org.getOrgID());
        int testLocID2 = orgFromDAO.getLocation().getLocationID();
        assertEquals(testLocID, testLocID2);

        Sighting sightingFromDAO = sightingDAO.getSightingByID(sighting.getSightingID());
        Location locFromSighting = sightingFromDAO.getLocation();
        assertEquals(location, locFromSighting);

        Location fromDAO = locationDAO.getLocationByID(location.getLocationID());
        assertEquals(location, fromDAO);

        locationDAO.deleteLocationByID(location.getLocationID());

        sightingFromDAO = sightingDAO.getSightingByID(sighting.getSightingID());
        assertNull(sightingFromDAO);

        fromDAO = locationDAO.getLocationByID(location.getLocationID());
        assertNull(fromDAO);

        orgFromDAO = orgDAO.getOrgByID(org.getOrgID());
        assertNull(orgFromDAO);
    }

}
