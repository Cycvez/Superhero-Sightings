/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.Dao;

import com.sg.SuperHeroSightings.Entity.Location;
import com.sg.SuperHeroSightings.Entity.Organization;
import com.sg.SuperHeroSightings.Entity.Sighting;
import com.sg.SuperHeroSightings.Entity.Supers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author carlo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SupersDao supersDao;

    @Autowired
    SightingDao sightingDao;

    public LocationDaoDBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Location> locations = locationDao.getAllLocation();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }

        List<Organization> organizations = organizationDao.getAllOrganization();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }

        List<Supers> supers = supersDao.getAllSupers();
        for (Supers s : supers) {
            supersDao.deleteSuperById(s.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocationById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(77.77);
        location.setLongitude(88.88);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

    }

    /**
     * Test of getAllLocation method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocation() {
        Location locationOne = new Location();
        locationOne.setName("Test Name One");
        locationOne.setDescription("Test Description One");
        locationOne.setAddress("Test Address One");
        locationOne.setLatitude(11.11);
        locationOne.setLongitude(11.01);
        locationOne = locationDao.addLocation(locationOne);

        Location locationTwo = new Location();
        locationTwo.setName("Test Name Two");
        locationTwo.setDescription("Test Description Two");
        locationTwo.setAddress("Test Address Two");
        locationTwo.setLatitude(22.22);
        locationTwo.setLongitude(22.02);
        locationTwo = locationDao.addLocation(locationTwo);

        Location locationThree = new Location();
        locationThree.setName("Test Name Three");
        locationThree.setDescription("Test Description Three");
        locationThree.setAddress("Test Address Three");
        locationThree.setLatitude(33.33);
        locationThree.setLongitude(33.03);
        locationThree = locationDao.addLocation(locationThree);

        List<Location> locations = locationDao.getAllLocation();
        assertEquals(3, locations.size());
        assertTrue(locations.contains(locationOne));
        assertTrue(locations.contains(locationTwo));
        assertTrue(locations.contains(locationThree));

    }

    /**
     * Test of updateLoaction method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLoaction() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(77.77);
        location.setLongitude(88.88);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

        location.setName("New Test Name");
        locationDao.updateLoaction(location);

        assertFalse(location.equals(fromDao));

        fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
        
        Supers s = new Supers();
        s.setName("Test Super name");
        s.setSuperPower("Test Super superPower");
        s.setDescription("Test Super Description");
        s.setHero(true);
        s = supersDao.addSuper(s);

        List<Supers> supers = new ArrayList<>();
        supers.add(s);
        

        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(77.77);
        location.setLongitude(88.88);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setTimeStamp(LocalDate.now());
        sighting.setSupers(supers);
        

        location = locationDao.addLocation(location);
        
        sightingDao.addSighting(sighting);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);

        locationDao.deleteLocationById(location.getId());
        fromDao = locationDao.getLocationById(location.getId());
        
        assertNull(fromDao);
    }

}
