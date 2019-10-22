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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class HeroDAOImplTest {
    
    @Autowired
    HeroDAO heroDAO;
    
    @Autowired
    SightingDAO sightingDAO;
    
    @Autowired
    OrgDAO orgDAO;
    
    @Autowired
    LocationDAO locationDAO;
    
    public HeroDAOImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Hero> heroes = heroDAO.getAllHeroes();
        for(Hero hero : heroes) {
            heroDAO.deleteHeroById(hero.getHeroID());
        }
    }
    
    @After
    public void tearDown() {
    }
  
    @Test
    public void testAddAndGetHero() {
        Hero hero = new Hero();
        hero.setHeroName("Test Spiderman");
        hero.setHeroDescription("A man that is a spider");
        hero.setSuperPower("Can climb");
        hero.setHeroStatus("SH");
        hero = heroDAO.addHero(hero);
        
        Hero fromDAO = heroDAO.getHeroByID(hero.getHeroID());
        
        assertEquals(hero, fromDAO);
    }

    @Test
    public void testGetAllHeroes() {
        Hero hero = new Hero();
        hero.setHeroName("Test Spiderman");
        hero.setHeroDescription("A man that is a spider");
        hero.setSuperPower("Can climb");
        hero.setHeroStatus("SH");
        hero = heroDAO.addHero(hero);
        
        Hero hero2 = new Hero();
        hero2.setHeroName("Test Superman");
        hero2.setHeroDescription("A man that is super");
        hero2.setSuperPower("Can fly");
        hero2.setHeroStatus("SV");
        hero2 = heroDAO.addHero(hero2);
        
        List<Hero> heroes = heroDAO.getAllHeroes();
        
        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));
    }


    @Test
    public void testUpdateHero() {
        Hero hero = new Hero();
        hero.setHeroName("Test Batman");
        hero.setHeroDescription("A man that is a bat");
        hero.setSuperPower("Has cape and cool car");
        hero.setHeroStatus("SH");
        hero = heroDAO.addHero(hero);
        
        Hero fromDAO = heroDAO.getHeroByID(hero.getHeroID());
        assertEquals(hero, fromDAO);
        
        hero.setHeroName("Batman Returns");
        heroDAO.updateHero(hero);
        
        assertNotEquals(hero, fromDAO);
        
        fromDAO = heroDAO.getHeroByID(hero.getHeroID());
        assertEquals(hero, fromDAO);
    }


    @Test
    public void testDeleteHeroById() {
        Hero hero = new Hero();
        hero.setHeroName("Test Spiderman");
        hero.setHeroDescription("A man that is a spider");
        hero.setSuperPower("Can climb");
        hero.setHeroStatus("SH");
        hero = heroDAO.addHero(hero);
        
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
        
        List<Hero> members = new ArrayList<>();
        members.add(hero);
        Org org = new Org();
        org.setMembers(members);
        org.setOrgName("Test Org");
        org.setOrgDescrip("An org");
        org.setOrgPhone("123-123-1234");
        org.setOrgEmail("test@email.com");
        org.setOrgStatus("SH");
        org.setLocation(location);
        orgDAO.addOrg(org);
        
        Sighting sighting = new Sighting();
        sighting.setSightingDate(new Date());
        sighting.setHero(hero);
        sighting.setLocation(location);
        sightingDAO.addSighting(sighting);
        
        Org orgFromDAO = orgDAO.getOrgByID(org.getOrgID());
        List<Hero> heroFromOrg = orgFromDAO.getMembers();
        assertTrue(heroFromOrg.contains(hero));
        
        Sighting sightingFromDAO = sightingDAO.getSightingByID(sighting.getSightingID());
        Hero heroFromDAO = sightingFromDAO.getHero();
        assertEquals(hero, heroFromDAO);
        
        Hero fromDAO = heroDAO.getHeroByID(hero.getHeroID());
        assertEquals(hero, fromDAO);
        
        
        heroDAO.deleteHeroById(hero.getHeroID());
        sightingFromDAO = sightingDAO.getSightingByID(sighting.getSightingID());
        assertNull(sightingFromDAO);
        
        fromDAO = heroDAO.getHeroByID(hero.getHeroID());
        assertNull(fromDAO);
        
        orgFromDAO = orgDAO.getOrgByID(org.getOrgID());
        heroFromOrg = orgFromDAO.getMembers();
        assertFalse(heroFromOrg.contains(hero));
    }
    
}
