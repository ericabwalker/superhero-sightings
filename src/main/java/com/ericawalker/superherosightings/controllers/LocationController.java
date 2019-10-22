/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericawalker.superherosightings.controllers;

import com.ericawalker.superherosightings.dao.LocationDAO;
import com.ericawalker.superherosightings.dto.Location;
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
public class LocationController {

    @Autowired
    LocationDAO locationDAO;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @GetMapping("addLocation")
    public String addLocationRedirect() {
        return "redirect:/locations";
    }

    @PostMapping("addLocation")
    public String addLocation(@Valid Location location, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            List<Location> locations = locationDAO.getAllLocations();
            model.addAttribute("locations", locations);
            return "addLocation";
        }

        String locationName = request.getParameter("locationName");
        String locationDescrip = request.getParameter("locationDescrip");
        String street1 = request.getParameter("street1");
        String street2 = request.getParameter("street2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipcode = request.getParameter("zipcode");
        String country = request.getParameter("country");
        String locationLat = request.getParameter("locationLat");
        String locationLong = request.getParameter("locationLong");

        location.setLocationName(locationName);
        location.setLocationDescrip(locationDescrip);
        location.setStreet1(street1);
        location.setStreet2(street2);
        location.setCity(city);
        location.setState(state);
        location.setZipcode(zipcode);
        location.setCountry(country);
        location.setLocationLat(locationLat);
        location.setLocationLong(locationLong);

        locationDAO.addLocation(location);

        return "redirect:/locations";
    }

    @PostMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("locationID"));
        locationDAO.deleteLocationByID(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("locationID"));
        Location location = locationDAO.getLocationByID(id);

        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result) {
        if(result.hasErrors()) {
            return "editLocation";
        }

        locationDAO.updateLocation(location);

        return "redirect:/locations";
    }
}
