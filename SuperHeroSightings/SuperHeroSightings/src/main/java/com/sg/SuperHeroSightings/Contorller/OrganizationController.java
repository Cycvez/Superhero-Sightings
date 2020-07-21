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
import com.sg.SuperHeroSightings.Entity.Organization;
import com.sg.SuperHeroSightings.Entity.Supers;
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
public class OrganizationController {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SupersDao supersDao;

    @Autowired
    SightingDao sightingDao;

//    Set<ConstraintViolation<Organization>> violations = new HashSet<>();,

    @GetMapping("organizations")
    public String displayOrganization(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganization();
        List<Supers> supers = supersDao.getAllSupers();
        Organization organization = new Organization();

        model.addAttribute("supers", supers);
        model.addAttribute("organizations", organizations);
        model.addAttribute("organization", organization);
//        model.addAttribute("errors", violations);

        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String contactInfo = request.getParameter("contactInfo");
        String[] supersIds = request.getParameterValues("supersId");

        List<Supers> supers = new ArrayList<>();
        if (supersIds != null) {
            for (String supersId : supersIds) {
                supers.add(supersDao.getSuperById(Integer.parseInt(supersId)));
            }
        } else {
            FieldError error = new FieldError("organizations", "supers", "Must include at least one super");
            result.addError(error);
        }

        if (result.hasErrors()) {
            model.addAttribute("organizations", organizationDao.getAllOrganization());
            model.addAttribute("supers", supersDao.getAllSupers());
            return "organizations";
        }

        organization.setName(name);
        organization.setDescription(description);
        organization.setAddress(address);
        organization.setContactInfo(contactInfo);
        organization.setSupers(supers);

        organizationDao.addOrganization(organization);
        return "redirect:/organizations";

    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);

        return "organizationDetail";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganizationById(id);

        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Supers> supers = supersDao.getAllSupers();

        model.addAttribute("organization", organization);
        model.addAttribute("supers", supers);

        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String edittedOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model) {
        String[] supersIds = request.getParameterValues("supersId");

        List<Supers> supers = new ArrayList<>();
        if (supersIds != null) {
            for (String supersId : supersIds) {
                supers.add(supersDao.getSuperById(Integer.parseInt(supersId)));
            }
        } else {
            FieldError error = new FieldError("organization", "supers", "Must include at least one super");
            result.addError(error);
        }

        organization.setSupers(supers);
        if (result.hasErrors()) {
            model.addAttribute("organization", organization);
            model.addAttribute("supers", supersDao.getAllSupers());
            return "editOrganization";
        }
        organizationDao.updateOrganization(organization);

        return "redirect:/organizations";
    }
}
