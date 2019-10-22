/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.controllers;

import com.ericawalker.superherosightings.dao.HeroDAO;
import com.ericawalker.superherosightings.dao.SightingDAO;
import com.ericawalker.superherosightings.dto.Hero;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ericabenton
 */
@Controller
public class HeroController {

    @Autowired
    HeroDAO heroDAO;

    @Autowired
    SightingDAO sightingDAO;

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroDAO.getAllHeroes();
        model.addAttribute("heroes", heroes);
        return "heroes";
    }

    @GetMapping("addHero")
    public String addHeroRedirect() {
        return "redirect:/heroes";
    }

    @PostMapping("addHero")
    public String addHero(@Valid Hero hero, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            List<Hero> heroes = heroDAO.getAllHeroes();
            model.addAttribute("heroes", heroes);
            return "addHero";
        }

        String heroName = request.getParameter("heroName");
        String heroDescription = request.getParameter("heroDescription");
        String superPower = request.getParameter("superPower");
        String heroStatus = request.getParameter("heroStatus");

        hero.setHeroName(heroName);
        hero.setHeroDescription(heroDescription);
        hero.setSuperPower(superPower);
        hero.setHeroStatus(heroStatus);

        heroDAO.addHero(hero);

        return "redirect:/heroes";
    }

    @PostMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("heroID"));
        heroDAO.deleteHeroById(id);

        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("heroID"));
        Hero hero = heroDAO.getHeroByID(id);

        model.addAttribute("hero", hero);
        return "editHero";
    }
    
    
    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result) {
        if(result.hasErrors()) {
            return "editHero";
        }

        heroDAO.updateHero(hero);

        return "redirect:/heroes";
    }
}
