/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dto.Hero;
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
public class HeroDAOImpl implements HeroDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroByID(int heroID) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM Hero WHERE heroID = ?";
            return jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), heroID);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String GET_ALL_HEROES = "SELECT * FROM Hero";
        return jdbc.query(GET_ALL_HEROES, new HeroMapper());
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO Hero(heroName, heroDescription, "
                + "superPower, heroStatus) VALUES(?,?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.getSuperPower(),
                hero.getHeroStatus());

        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroID(newID);
        return hero;
    }

    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE Hero SET heroName=?, heroDescription=?, "
                + "superPower=?, heroStatus=? WHERE heroID=?";
        jdbc.update(UPDATE_HERO,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.getSuperPower(),
                hero.getHeroStatus(),
                hero.getHeroID());
    }

    @Override
    @Transactional
    public void deleteHeroById(int heroID) {
        final String DELETE_HERO_FROM_ORG = "DELETE FROM OrgMembers WHERE heroID = ?";
        jdbc.update(DELETE_HERO_FROM_ORG, heroID);

        final String DELETE_HERO_SIGHTING = "DELETE FROM Sighting WHERE heroID = ?";
        jdbc.update(DELETE_HERO_SIGHTING, heroID);

        final String DELETE_HERO = "DELETE FROM Hero WHERE heroID = ?";
        jdbc.update(DELETE_HERO, heroID);
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroID(rs.getInt("heroID"));
            hero.setHeroName(rs.getString("heroName"));
            hero.setHeroDescription(rs.getString("heroDescription"));
            hero.setSuperPower(rs.getString("superPower"));
            hero.setHeroStatus(rs.getString("heroStatus"));

            return hero;
        }
    }
}
