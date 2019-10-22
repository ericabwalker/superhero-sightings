/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dao.HeroDAOImpl.HeroMapper;
import com.ericawalker.superherosightings.dao.LocationDAOImpl.LocationMapper;
import com.ericawalker.superherosightings.dto.Hero;
import com.ericawalker.superherosightings.dto.Location;
import com.ericawalker.superherosightings.dto.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ericabenton
 */
@Repository
public class SightingDAOImpl implements SightingDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    HeroDAO heroDAO;

    @Override
    public Sighting getSightingByID(int sightingID) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE sightingID=?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), sightingID);
            sighting.setHero(getHeroForSighting(sightingID));
            sighting.setLocation(getLocationForSighting(sightingID));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Hero getHeroForSighting(int sightingID) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT h.* FROM Hero h "
                + "JOIN Sighting s ON s.heroID = h.heroID WHERE s.sightingID = ?";
        return jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING, new HeroMapper(), sightingID);
    }

    private Location getLocationForSighting(int sightingID) {
        final String SELECT_LOC_FOR_SIGHTING = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.locationID = l.locationID WHERE s.sightingID = ?";
        return jdbc.queryForObject(SELECT_LOC_FOR_SIGHTING, new LocationMapper(), sightingID);
    }

    @Override
    public List<Sighting> getMostRecentSightings() {
        try {
            final String SELECT_SIGHTINGS_BY_DATE = "SELECT * FROM Sighting ORDER BY sightingDate DESC LIMIT 10";
            List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_DATE, new SightingMapper());
            return sightings;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateHeroesAndLocations(sightings);
        return sightings;
    }

    private void associateHeroesAndLocations(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setHero(getHeroForSighting(sighting.getSightingID()));
            sighting.setLocation(getLocationForSighting(sighting.getSightingID()));
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO Sighting(sightingDate, heroID, locationID) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getSightingDate(),
                sighting.getHero().getHeroID(),
                sighting.getLocation().getLocationID());

        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingID(newID);
        return sighting;
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE Sighting SET sightingDate=?, heroID=?, "
                + "locationID=? WHERE sightingID=?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getSightingDate(),
                sighting.getHero().getHeroID(),
                sighting.getLocation().getLocationID(),
                sighting.getSightingID());
    }

    @Override
    @Transactional
    public void deleteSightingById(int sightingID) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE sightingID=?";
        jdbc.update(DELETE_SIGHTING, sightingID);
    }

    @Override
    public List<Sighting> getSightingsForHero(Hero hero) {
        final String SELECT_SIGHTINGS_FOR_HERO = "SELECT * FROM Sighting WHERE heroID=?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_HERO, new SightingMapper(), hero.getHeroID());
        associateHeroesAndLocations(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOC = "SELECT * FROM Sighting WHERE locationID=?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOC, new SightingMapper(), location.getLocationID());
        associateHeroesAndLocations(sightings);
        return sightings;
    }

    public final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            int locationID = rs.getInt("locationID");
            int heroID = rs.getInt("heroID");

            sighting.setSightingID(rs.getInt("sightingID"));
            sighting.setSightingDate(rs.getDate("sightingDate"));
            sighting.setLocation(locationDAO.getLocationByID(locationID));
            sighting.setHero(heroDAO.getHeroByID(heroID));

            return sighting;
        }
    }

}
