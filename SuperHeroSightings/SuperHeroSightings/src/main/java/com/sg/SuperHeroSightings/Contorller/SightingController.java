/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.Contorller;

import com.sg.SuperHeroSightings.Dao.LocationDao;
import com.sg.SuperHeroSightings.Dao.OrganizationDao;
import com.sg.SuperHeroSightings.Dao.SightingDao;
import com.sg.SuperHeroSightings.Dao.SupersDao;
import com.sg.SuperHeroSightings.Entity.Location;
import com.sg.SuperHeroSightings.Entity.Sighting;
import com.sg.SuperHeroSightings.Entity.Supers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author carlo
 */
@Controller
public class SightingController {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SupersDao supersDao;

    @Autowired
    SightingDao sightingDao;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location> locations = locationDao.getAllLocation();
        List<Supers> supers = supersDao.getAllSupers();
        Sighting sighting = new Sighting();

        model.addAttribute("sighting", sighting);
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("supers", supers);
        model.addAttribute("errors", violations);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        String locationId = request.getParameter("locationId");
        String[] supersIds = request.getParameterValues("supersId");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String timestamp = request.getParameter("timestamp");
        LocalDate localDate = LocalDate.parse(timestamp, formatter);

        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        List<Supers> supers = new ArrayList<>();
        if (supersIds != null) {
            for (String supersId : supersIds) {
                supers.add(supersDao.getSuperById(Integer.parseInt(supersId)));
            }
        } else {
            FieldError error = new FieldError("sighting", "supers", "Must include at least one super");
            result.addError(error);
        }

        sighting.setSupers(supers);

        if (result.hasErrors()) {
            model.addAttribute("locations", locationDao.getAllLocation());
            model.addAttribute("sightings", sightingDao.getAllSightings());
            model.addAttribute("supers", supersDao.getAllSupers());
            return "sightings";
        }
        sighting.setTimeStamp(localDate);
        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingsDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);

        return "sightingDetail";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sightings = sightingDao.getSightingById(id);
        List<Supers> supers = supersDao.getAllSupers();
        List<Location> locations = locationDao.getAllLocation();

        model.addAttribute("sighting", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("supers", supers);
        model.addAttribute("errors", violations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String edittedSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        String locationId = request.getParameter("locationId");
        String[] supersIds = request.getParameterValues("supersId");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String timestamp = request.getParameter("timestamp");
        LocalDate localDate = LocalDate.parse(timestamp, formatter);

        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));

        List<Supers> supers = new ArrayList<>();
        if (supersIds != null) {
            for (String supersId : supersIds) {
                supers.add(supersDao.getSuperById(Integer.parseInt(supersId)));
            }
        } else {
            FieldError error = new FieldError("sightings", "supers", "Must include at least one super");
            result.addError(error);
        }

        sighting.setSupers(supers);

        if (result.hasErrors()) {
            model.addAttribute("locations", locationDao.getAllLocation());
            model.addAttribute("sightings", sightingDao.getAllSightings());
            model.addAttribute("supers", supersDao.getAllSupers());
            return "editSighting";
        }
        sighting.setTimeStamp(localDate);
        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("/")
    public String mostRecentSightings(Model model) {
        List<Sighting> sightings = sightingDao.getTheMostRecentSightings();

        model.addAttribute("sightings", sightings);
        model.addAttribute("errors", violations);
        return "index";
    }

}
