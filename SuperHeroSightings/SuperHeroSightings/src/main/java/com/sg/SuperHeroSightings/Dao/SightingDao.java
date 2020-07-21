/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.Dao;

import com.sg.SuperHeroSightings.Entity.Location;
import com.sg.SuperHeroSightings.Entity.Sighting;
import com.sg.SuperHeroSightings.Entity.Supers;
import java.util.List;

/**
 *
 * @author carlo
 */
public interface SightingDao {

    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int id);
    
    List<Sighting> getAllSightingsOfSuper(Supers supers);
    
    List<Sighting> getAllSightingsOfLocation(Location location);
    
    List<Sighting> getTheMostRecentSightings();
}
