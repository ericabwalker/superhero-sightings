/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dao.HeroDAOImpl.HeroMapper;
import com.ericawalker.superherosightings.dto.Hero;
import com.ericawalker.superherosightings.dto.Org;
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
public class OrgDAOImpl implements OrgDAO {

    @Autowired
    JdbcTemplate jdbc;
    
    @Autowired
    LocationDAO locationDAO;

    @Override
    public Org getOrgByID(int orgID) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM Org WHERE orgID = ?";
            Org org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrgMapper(), orgID);
            org.setMembers(getMembersForOrg(orgID));
            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Hero> getMembersForOrg(int orgID) {
        final String SELECT_MEM_FOR_ORG = "SELECT h.* FROM Hero h JOIN OrgMembers"
                + " om ON om.heroID =h.heroID WHERE om.orgID=?";
        return jdbc.query(SELECT_MEM_FOR_ORG, new HeroMapper(), orgID);
    }

    @Override
    public List<Org> getAllOrgs() {
        final String GET_ALL_ORGS = "SELECT * FROM Org";
        return jdbc.query(GET_ALL_ORGS, new OrgMapper());
    }

    @Override
    @Transactional
    public Org addOrg(Org org) {
        final String INSERT_ORG = "INSERT INTO Org(orgName, orgDescrip, orgPhone, "
                + "orgEmail, orgStatus, locationID) VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getOrgName(),
                org.getOrgDescrip(),
                org.getOrgPhone(),
                org.getOrgEmail(),
                org.getOrgStatus(),
                org.getLocation().getLocationID());

        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrgID(newID);
        insertOrgMember(org);
        return org;
    }

    private void insertOrgMember(Org org) {
        final String INSERT_ORG_MEMBER = "INSERT INTO OrgMembers(orgID, heroID) VALUES(?,?)";
        for (Hero hero : org.getMembers()) {
            jdbc.update(INSERT_ORG_MEMBER,
                    org.getOrgID(),
                    hero.getHeroID());
        }
    }

    @Override
    @Transactional
    public void updateOrg(Org org) {
        final String UPDATE_ORG = "UPDATE Org SET orgName=?, orgDescrip=?, orgPhone=?,"
                + "orgEmail=?, orgStatus=?, locationID=? WHERE orgID=?";
        jdbc.update(UPDATE_ORG,
                org.getOrgName(),
                org.getOrgDescrip(),
                org.getOrgPhone(),
                org.getOrgEmail(),
                org.getOrgStatus(),
                org.getLocation().getLocationID(),
                org.getOrgID());
        final String DELETE_ORG_MEMBER = "DELETE FROM OrgMembers WHERE orgID=?";
        jdbc.update(DELETE_ORG_MEMBER, org.getOrgID());
        insertOrgMember(org);
    }

    @Override
    public void deleteOrgById(int orgID) {
        final String DELETE_ORG_FROM_ORGMEM = "DELETE FROM OrgMembers WHERE orgID=?";
        jdbc.update(DELETE_ORG_FROM_ORGMEM, orgID);

        final String DELETE_ORG = "DELETE FROM Org WHERE orgID = ?";
        jdbc.update(DELETE_ORG, orgID);
    }

    public final class OrgMapper implements RowMapper<Org> {

        @Override
        public Org mapRow(ResultSet rs, int index) throws SQLException {
            Org org = new Org();
            int id = rs.getInt("locationID");
            
            org.setOrgID(rs.getInt("orgID"));
            org.setOrgName(rs.getString("orgName"));
            org.setOrgDescrip(rs.getString("orgDescrip"));
            org.setOrgPhone(rs.getString("orgPhone"));
            org.setOrgEmail(rs.getString("orgEmail"));
            org.setOrgStatus(rs.getString("orgStatus"));
            org.setLocation(locationDAO.getLocationByID(id));
            org.setMembers(getMembersForOrg(org.getOrgID()));

            return org;
        }
    }

}
