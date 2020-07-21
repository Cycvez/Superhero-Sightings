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
import com.sg.SuperHeroSightings.Entity.Supers;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author carlo
 */
@Controller
public class SupersController {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SupersDao supersDao;

    @Autowired
    SightingDao sightingDao;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("supers")
    public String displaySupers(Model model) {
        List<Supers> supersList = supersDao.getAllSupers();
        Supers supers = new Supers();

        model.addAttribute("supers", supers);
        model.addAttribute("supersList", supersList);

        return "supers";
    }

    @PostMapping("addSuper")
    public String addSuper(@Valid Supers supers, BindingResult result, HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String superPower = request.getParameter("superPower");
        String description = request.getParameter("description");
        String hero= request.getParameter("hero");

        if (result.hasErrors()) {
            model.addAttribute("supersList", supersDao.getAllSupers());
            return "supers";
        }

        supers.setName(name);
        supers.setSuperPower(superPower);
        supers.setDescription(description);
        supers.setHero(Boolean.parseBoolean(hero));

        supersDao.addSuper(supers);

        return "redirect:/supers";
    }

    @GetMapping("deleteSuper")
    public String deleteSuper(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        supersDao.deleteSuperById(id);

        return "redirect:/supers";
    }

    @GetMapping("editSuper")
    public String editSuper(Integer id, Model model) {
        Supers supers = supersDao.getSuperById(id);
        model.addAttribute("supers", supers);
        return "editSuper";
    }

    @PostMapping("editSuper")
    public String edittedSuper(@Valid Supers supers, BindingResult result) {
        if (result.hasErrors()) {
            return "editSuper";
        }
        supersDao.updateSuper(supers);
        return "redirect:/supers";
    }
}
