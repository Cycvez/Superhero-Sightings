/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.Dao;

import com.sg.SuperHeroSightings.Entity.Location;
import com.sg.SuperHeroSightings.Entity.Organization;
import com.sg.SuperHeroSightings.Entity.Supers;
import java.util.List;

/**
 *
 * @author carlo
 */
public interface SupersDao {

    Supers getSuperById(int id);

    List<Supers> getAllSupers();

    Supers addSuper(Supers Super);

    void updateSuper(Supers Super);

    void deleteSuperById(int id);
    
    List<Supers> getAllSupersInOrganization(Organization organization);
    
    List<Supers> getAllSupersSightedAtLocations(Location location);
}
