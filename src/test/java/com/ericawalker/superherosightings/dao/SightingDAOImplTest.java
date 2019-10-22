/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dto.Hero;
import com.ericawalker.superherosightings.dto.Location;
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
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ericabenton
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDAOImplTest {

    @Autowired
    SightingDAO sightingDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    HeroDAO heroDAO;

    public SightingDAOImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Sighting> sightings = sightingDAO.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDAO.deleteSightingById(sighting.getSightingID());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddAndGetSighting() {
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

        Hero hero = new Hero();
        hero.setHeroName("Test Spiderman");
        hero.setHeroDescription("A man that is a spider");
        hero.setSuperPower("Can climb");
        hero.setHeroStatus("SH");
        heroDAO.addHero(hero);

        Sighting sighting = new Sighting();
        sighting.setSightingDate(new Date());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDAO.addSighting(sighting);

        Sighting fromDAO = sightingDAO.getSightingByID(sighting.getSightingID());

        assertEquals(sighting, fromDAO);
    }

    @Test
    public void testGetAllSightings() {
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

        Hero hero = new Hero();
        hero.setHeroName("Test Spiderman");
        hero.setHeroDescription("A man that is a spider");
        hero.setSuperPower("Can climb");
        hero.setHeroStatus("SH");
        heroDAO.addHero(hero);

        Sighting sighting = new Sighting();
        sighting.setSightingDate(new Date());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDAO.addSighting(sighting);
        
        Location location2 = new Location();
        location2.setLocationName("US Bank");
        location2.setLocationDescrip("The bank on the corner");
        location2.setStreet1("1234 Main Street");
        location2.setCity("Cincinnati");
        location2.setState("OH");
        location2.setZipcode("12345");
        location2.setCountry("USA");
        location2.setLocationLat("123-ad-123");
        location2.setLocationLong("321-da-211");
        locationDAO.addLocation(location2);

        Hero hero2 = new Hero();
        hero2.setHeroName("Test Spiderman2");
        hero2.setHeroDescription("A man that is a spider");
        hero2.setSuperPower("Can climb");
        hero2.setHeroStatus("SV");
        heroDAO.addHero(hero2);

        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(new Date());
        sighting2.setHero(hero);
        sighting2.setLocation(location);
        sightingDAO.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDAO.getAllSightings();
        
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));
    }

    @Test
    public void testUpdateSighting() {
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

        Hero hero = new Hero();
        hero.setHeroName("Test Spiderman");
        hero.setHeroDescription("A man that is a spider");
        hero.setSuperPower("Can climb");
        hero.setHeroStatus("SH");
        heroDAO.addHero(hero);
        
        Hero hero2 = new Hero();
        hero2.setHeroName("Test Batman");
        hero2.setHeroDescription("A man that is a bat");
        hero2.setSuperPower("Has cape");
        hero2.setHeroStatus("SV");
        heroDAO.addHero(hero2);

        Sighting sighting = new Sighting();
        sighting.setSightingDate(new Date());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDAO.addSighting(sighting);

        Sighting fromDAO = sightingDAO.getSightingByID(sighting.getSightingID());
        assertEquals(sighting, fromDAO);

        sighting.setHero(hero2);
        sightingDAO.updateSighting(sighting);
        
        assertNotEquals(sighting, fromDAO);
        
        fromDAO = sightingDAO.getSightingByID(sighting.getSightingID());
        assertEquals(sighting, fromDAO);
    }

    @Test
    public void testDeleteSightingById() {
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

        Sighting fromDAO = sightingDAO.getSightingByID(sighting.getSightingID());
        assertEquals(sighting, fromDAO);

        sightingDAO.deleteSightingById(sighting.getSightingID());

        fromDAO = sightingDAO.getSightingByID(sighting.getSightingID());
        assertNull(fromDAO);
    }

    @Test
    public void testGetSightingsForHero() {
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
        location2.setLocationName("Citi Bank");
        location2.setLocationDescrip("The bank on the other corner");
        location2.setStreet1("1234 Market Street");
        location2.setCity("Dayton");
        location2.setState("OH");
        location2.setZipcode("12345");
        location2.setCountry("USA");
        location2.setLocationLat("123-ad-123");
        location2.setLocationLong("321-da-211");
        locationDAO.addLocation(location2);

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
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(new Date());
        sighting2.setHero(hero);
        sighting2.setLocation(location2);
        sightingDAO.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDAO.getSightingsForHero(hero);
        
        assertEquals(2, sightings.size());    
    }

    @Test
    public void testGetSightingsForLocation() {
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
        
        Sighting sighting2 = new Sighting();
        sighting2.setSightingDate(new Date());
        sighting2.setHero(hero);
        sighting2.setLocation(location);
        sightingDAO.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDAO.getSightingsForLocation(location);
        
        assertEquals(2, sightings.size());
    }

}
