/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dto.Location;
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
public class LocationDAOImpl implements LocationDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationByID(int locationID) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM Location WHERE locationID = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), locationID);

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM Location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO Location(locationName, locationDescrip, "
                + "street1, street2, city, state, zipcode, country, locationLat, locationLong) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getLocationName(),
                location.getLocationDescrip(),
                location.getStreet1(),
                location.getStreet2(),
                location.getCity(),
                location.getState(),
                location.getZipcode(),
                location.getCountry(),
                location.getLocationLat(),
                location.getLocationLong());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationID(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Location SET locationName=?, locationDescrip=?, "
                + "street1=?, street2=?, city=?, state=?, zipcode=?, country=?, locationLat=?, "
                + "locationLong=? WHERE locationID=?";
        jdbc.update(UPDATE_LOCATION,
                location.getLocationName(),
                location.getLocationDescrip(),
                location.getStreet1(),
                location.getStreet2(),
                location.getCity(),
                location.getState(),
                location.getZipcode(),
                location.getCountry(),
                location.getLocationLat(),
                location.getLocationLong(),
                location.getLocationID());
    }

    @Override
    public void deleteLocationByID(int locationID) {
        final String DELETE_LOC_FROM_ORG = "DELETE FROM Org WHERE locationID = ?";
        jdbc.update(DELETE_LOC_FROM_ORG, locationID);

        final String DELETE_LOC_SIGHTING = "DELETE FROM Sighting WHERE locationID = ?";
        jdbc.update(DELETE_LOC_SIGHTING, locationID);

        final String DELETE_LOCATION = "DELETE FROM Location WHERE locationID = ?";
        jdbc.update(DELETE_LOCATION, locationID);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationID(rs.getInt("locationID"));
            location.setLocationName(rs.getString("locationName"));
            location.setLocationDescrip(rs.getString("locationDescrip"));
            location.setStreet1(rs.getString("street1"));
            location.setStreet2(rs.getString("street2"));
            location.setCity(rs.getString("city"));
            location.setState(rs.getString("state"));
            location.setZipcode(rs.getString("zipcode"));
            location.setCountry(rs.getString("country"));
            location.setLocationLat(rs.getString("locationLat"));
            location.setLocationLong(rs.getString("locationLong"));

            return location;
        }
    }

}
