/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.dao;

import com.ericawalker.superherosightings.dto.Hero;
import java.util.List;

/**
 *
 * @author ericabenton
 */
public interface HeroDAO {
    
    Hero getHeroByID(int heroID);
    
    List<Hero> getAllHeroes();
    
    Hero addHero(Hero hero);
    
    void updateHero(Hero hero);
    
    void deleteHeroById(int heroID);
    
}
