/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.Dao;

import com.sg.SuperHeroSightings.Entity.Organization;
import com.sg.SuperHeroSightings.Entity.Supers;
import java.util.List;

/**
 *
 * @author carlo
 */
public interface OrganizationDao {

    Organization getOrganizationById(int id);

    List<Organization> getAllOrganization();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int id);
    
    List<Organization> getAllOrganizationsForSuper(Supers supers);
}
