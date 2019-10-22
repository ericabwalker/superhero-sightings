/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.controllers;

import com.ericawalker.superherosightings.dao.HeroDAO;
import com.ericawalker.superherosightings.dao.LocationDAO;
import com.ericawalker.superherosightings.dao.OrgDAO;
import com.ericawalker.superherosightings.dao.SightingDAO;
import com.ericawalker.superherosightings.dto.Hero;
import com.ericawalker.superherosightings.dto.Location;
import com.ericawalker.superherosightings.dto.Sighting;
import com.ericawalker.superherosightings.dto.SightingRequest;
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
public class SightingController {

    @Autowired
    SightingDAO sightingDAO;

    @Autowired
    HeroDAO heroDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    OrgDAO orgDAO;

    @GetMapping("/")
    public String displayLatestSightings(Model model) {
        List<Sighting> sightings = sightingDAO.getMostRecentSightings();
        model.addAttribute("sightings", sightings);
        return "index";
    }

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDAO.getAllSightings();
        List<Hero> heroes = heroDAO.getAllHeroes();
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "sightings";
    }

    @GetMapping("addSighting")
    public String addSightingRedirect() {
        return "redirect:/sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(@Valid SightingRequest sightingRequest, BindingResult result, HttpServletRequest request, Model model) throws Exception {
        if (result.hasErrors()) {
            List<Hero> heroes = heroDAO.getAllHeroes();
            List<Location> locations = locationDAO.getAllLocations();
            model.addAttribute("heroes", heroes);
            model.addAttribute("locations", locations);
            return "addSighting";
        }

        Sighting sighting = new Sighting();
        sighting.setHero(heroDAO.getHeroByID(sightingRequest.getHeroID()));
        sighting.setLocation(locationDAO.getLocationByID(sightingRequest.getLocationID()));
        sighting.setSightingDate(sightingRequest.getSightingDate());
        sightingDAO.addSighting(sighting);

        return "redirect:/sightings";
    }

    @PostMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("sightingID"));
        sightingDAO.deleteSightingById(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer sightingID, Model model) {
        Sighting sighting = sightingDAO.getSightingByID(sightingID);
        List<Hero> heroes = heroDAO.getAllHeroes();
        List<Location> locations = locationDAO.getAllLocations();

        model.addAttribute("sightingRequest", SightingRequest.newFromSighting(sighting));
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(@Valid SightingRequest sightingRequest, BindingResult result, HttpServletRequest request, Model model) throws Exception {
        if (result.hasErrors()) {
            List<Hero> heroes = heroDAO.getAllHeroes();
            List<Location> locations = locationDAO.getAllLocations();
            model.addAttribute("heroes", heroes);
            model.addAttribute("locations", locations);
            return "editSighting";
        }

        Sighting sighting = new Sighting();
        sighting.setSightingID(sightingRequest.getSightingID());
        sighting.setSightingDate(sightingRequest.getSightingDate());
        sighting.setHero(heroDAO.getHeroByID(sightingRequest.getHeroID()));
        sighting.setLocation(locationDAO.getLocationByID(sightingRequest.getLocationID()));

        sightingDAO.updateSighting(sighting);

        return "redirect:/sightings";
    }

}
