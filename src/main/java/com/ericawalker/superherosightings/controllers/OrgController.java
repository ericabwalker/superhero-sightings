/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.controllers;

import com.ericawalker.superherosightings.dao.HeroDAO;
import com.ericawalker.superherosightings.dao.LocationDAO;
import com.ericawalker.superherosightings.dao.OrgDAO;
import com.ericawalker.superherosightings.dto.Hero;
import com.ericawalker.superherosightings.dto.Location;
import com.ericawalker.superherosightings.dto.Org;
import com.ericawalker.superherosightings.dto.OrgRequest;
import java.util.ArrayList;
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
public class OrgController {

    @Autowired
    OrgDAO orgDAO;

    @Autowired
    HeroDAO heroDAO;

    @Autowired
    LocationDAO locationDAO;

    @GetMapping("orgs")
    public String displayOrgs(Model model) {
        List<Location> locations = locationDAO.getAllLocations();
        List<Hero> heroes = heroDAO.getAllHeroes();
        List<Org> orgs = orgDAO.getAllOrgs();
        model.addAttribute("orgs", orgs);
        model.addAttribute("locations", locations);
        model.addAttribute("heroes", heroes);
        return "orgs";
    }

    @GetMapping("addOrg")
    public String addOrgRedirect() {
        return "redirect:/orgs";
    }

    @PostMapping("addOrg")
    public String addOrg(@Valid OrgRequest orgRequest, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            List<Location> locations = locationDAO.getAllLocations();
            List<Hero> heroes = heroDAO.getAllHeroes();
            model.addAttribute("locations", locations);
            model.addAttribute("heroes", heroes);
            return "addOrg";
        }

        Org org = new Org();
        org.setLocation(locationDAO.getLocationByID(orgRequest.getLocationID()));

        List<Hero> members = new ArrayList<>();
        for (Integer heroID : orgRequest.getHeroIDs()) {
            members.add(heroDAO.getHeroByID(heroID));
        }

        org.setOrgName(orgRequest.getOrgName());
        org.setOrgDescrip(orgRequest.getOrgDescrip());
        org.setOrgPhone(orgRequest.getOrgPhone());
        org.setOrgEmail(orgRequest.getOrgEmail());
        org.setOrgStatus(orgRequest.getOrgStatus());
        org.setMembers(members);

        orgDAO.addOrg(org);

        return "redirect:/orgs";
    }
    

    @PostMapping("deleteOrg")
    public String deleteOrg(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("orgID"));
        orgDAO.deleteOrgById(id);

        return "redirect:/orgs";
    }

    @GetMapping("editOrg")
    public String editOrg(Integer orgID, Model model) {
        Org org = orgDAO.getOrgByID(orgID);
        List<Location> locations = locationDAO.getAllLocations();
        List<Hero> heroes = heroDAO.getAllHeroes();

        model.addAttribute("orgRequest", OrgRequest.newFromOrg(org));
        model.addAttribute("locations", locations);
        model.addAttribute("heroes", heroes);

        return "editOrg";
    }

    @PostMapping("editOrg")
    public String performEditOrg(@Valid OrgRequest orgRequest, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            List<Location> locations = locationDAO.getAllLocations();
            List<Hero> heroes = heroDAO.getAllHeroes();
            model.addAttribute("locations", locations);
            model.addAttribute("heroes", heroes);
            return "editOrg";
        }

        Org org = new Org();
        org.setOrgID(orgRequest.getOrgID());
        org.setLocation(locationDAO.getLocationByID(orgRequest.getLocationID()));

        List<Hero> members = new ArrayList<>();
        for (Integer heroID : orgRequest.getHeroIDs()) {
            members.add(heroDAO.getHeroByID(heroID));
        }

        org.setOrgName(orgRequest.getOrgName());
        org.setOrgDescrip(orgRequest.getOrgDescrip());
        org.setOrgPhone(orgRequest.getOrgPhone());
        org.setOrgEmail(orgRequest.getOrgEmail());
        org.setOrgStatus(orgRequest.getOrgStatus());
        org.setMembers(members);

        orgDAO.updateOrg(org);

        return "redirect:/orgs";
    }

}
