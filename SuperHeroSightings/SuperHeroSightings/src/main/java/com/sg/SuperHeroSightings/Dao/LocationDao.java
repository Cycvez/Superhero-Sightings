/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.Dao;

import com.sg.SuperHeroSightings.Entity.Location;
import java.util.List;

/**
 *
 * @author carlo
 */
public interface LocationDao {

    Location getLocationById(int id);

    List<Location> getAllLocation();

    Location addLocation(Location location);

    void updateLoaction(Location location);

    void deleteLocationById(int id);
    
}
