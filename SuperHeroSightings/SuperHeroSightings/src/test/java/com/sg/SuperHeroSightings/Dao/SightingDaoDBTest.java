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
import java.time.LocalDateTime;
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
public class SightingDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SupersDao supersDao;

    @Autowired
    SightingDao sightingDao;

    public SightingDaoDBTest() {
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
     * Test of getSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingById() {

    }

    /**
     * Test of addSighting method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSighting() {
        Location location = new Location();
        location.setName("Test Name One");
        location.setDescription("Test Description One");
        location.setAddress("Test Address One");
        location.setLatitude(11.11);
        location.setLongitude(11.01);
        location = locationDao.addLocation(location);

        Supers superOne = new Supers();
        superOne.setName("Test Super name");
        superOne.setSuperPower("Test Super superPower");
        superOne.setDescription("Test Super Description");
        superOne.setHero(true);
        superOne = supersDao.addSuper(superOne);

        List<Supers> supers = new ArrayList<>();
        supers.add(superOne);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setTimeStamp(LocalDate.now());
        sighting.setSupers(supers);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

        assertEquals(sighting, fromDao);

    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Supers superOne = new Supers();
        superOne.setName("Test Super name");
        superOne.setSuperPower("Test Super superPower");
        superOne.setDescription("Test Super Description");
        superOne.setHero(true);
        superOne = supersDao.addSuper(superOne);

        Supers superTwo = new Supers();
        superTwo.setName("Test Super name Two");
        superTwo.setSuperPower("Test Super superPower Two");
        superTwo.setDescription("Test Super Description Two");
        superTwo.setHero(false);
        superTwo = supersDao.addSuper(superTwo);

        Supers superThree = new Supers();
        superThree.setName("Test Super name Three");
        superThree.setSuperPower("Test Super superPower Three");
        superThree.setDescription("Test Super Description Three");
        superThree.setHero(true);
        superThree = supersDao.addSuper(superThree);

        List<Supers> supersOne = new ArrayList<>();
        supersOne.add(superOne);

        List<Supers> supersTwo = new ArrayList<>();
        supersTwo.add(superTwo);

        List<Supers> supersThree = new ArrayList<>();
        supersThree.add(superThree);

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

        Sighting sightingOne = new Sighting();
        sightingOne.setLocation(locationOne);
        sightingOne.setTimeStamp(LocalDate.now());
        sightingOne.setSupers(supersOne);

        sightingOne = sightingDao.addSighting(sightingOne);

        Sighting sightingTwo = new Sighting();
        sightingTwo.setLocation(locationTwo);
        sightingTwo.setTimeStamp(LocalDate.now());
        sightingTwo.setSupers(supersTwo);

        sightingTwo = sightingDao.addSighting(sightingTwo);

        Sighting sightingThree = new Sighting();
        sightingThree.setLocation(locationThree);
        sightingThree.setTimeStamp(LocalDate.now());
        sightingThree.setSupers(supersThree);

        sightingThree = sightingDao.addSighting(sightingThree);

        List<Sighting> sightings = sightingDao.getAllSightings();

        assertEquals(3, sightings.size());
        assertTrue(sightings.contains(sightingOne));
        assertTrue(sightings.contains(sightingTwo));
        assertTrue(sightings.contains(sightingThree));

    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Location location = new Location();
        location.setName("Test Name One");
        location.setDescription("Test Description One");
        location.setAddress("Test Address One");
        location.setLatitude(11.11);
        location.setLongitude(11.01);
        location = locationDao.addLocation(location);

        Supers superOne = new Supers();
        superOne.setName("Test Super name");
        superOne.setSuperPower("Test Super superPower");
        superOne.setDescription("Test Super Description");
        superOne.setHero(true);
        superOne = supersDao.addSuper(superOne);

        List<Supers> supers = new ArrayList<>();
        supers.add(superOne);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setTimeStamp(LocalDate.now());
        sighting.setSupers(supers);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

        assertEquals(sighting, fromDao);

        Supers superTwo = new Supers();
        superTwo.setName("Test Super name Two");
        superTwo.setSuperPower("Test Super superPower Two");
        superTwo.setDescription("Test Super Description Two");
        superTwo.setHero(false);
        superTwo = supersDao.addSuper(superTwo);
        supers.add(superTwo);

        sighting.setSupers(supers);

        sightingDao.updateSighting(sighting);

        assertFalse(sighting.equals(fromDao));

        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        Location location = new Location();
        location.setName("Test Name One");
        location.setDescription("Test Description One");
        location.setAddress("Test Address One");
        location.setLatitude(11.11);
        location.setLongitude(11.01);
        location = locationDao.addLocation(location);

        Supers superOne = new Supers();
        superOne.setName("Test Super name");
        superOne.setSuperPower("Test Super superPower");
        superOne.setDescription("Test Super Description");
        superOne.setHero(true);
        superOne = supersDao.addSuper(superOne);

        List<Supers> supers = new ArrayList<>();
        supers.add(superOne);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setTimeStamp(LocalDate.now());
        sighting.setSupers(supers);
        sighting = sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

        assertEquals(sighting, fromDao);

        sightingDao.deleteSightingById(sighting.getId());

        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);

    }

    /**
     * Test of getAllSightingsOfSuper method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightingsOfSuper() {
        Location location = new Location();
        location.setName("Test Name One");
        location.setDescription("Test Description One");
        location.setAddress("Test Address One");
        location.setLatitude(11.11);
        location.setLongitude(11.01);
        location = locationDao.addLocation(location);

        Supers testMan = new Supers();
        testMan.setName("Test Super name");
        testMan.setSuperPower("Test Super superPower");
        testMan.setDescription("Test Super Description");
        testMan.setHero(true);
        testMan = supersDao.addSuper(testMan);

        Supers yesMan = new Supers();
        yesMan.setName("Test Super name Two");
        yesMan.setSuperPower("Test Super superPower Two");
        yesMan.setDescription("Test Super Description Two");
        yesMan.setHero(false);
        yesMan = supersDao.addSuper(yesMan);

        List<Supers> yesSighting = new ArrayList<>();
        yesSighting.add(yesMan);

        List<Supers> testSighting = new ArrayList<>();
        testSighting.add(testMan);

        List<Supers> bothSighting = new ArrayList<>();
        bothSighting.add(testMan);
        bothSighting.add(yesMan);

        Sighting sightingYes = new Sighting();
        sightingYes.setLocation(location);
        sightingYes.setTimeStamp(LocalDate.now());
        sightingYes.setSupers(yesSighting);

        sightingYes = sightingDao.addSighting(sightingYes);

        Sighting sightingTest = new Sighting();
        sightingTest.setLocation(location);
        sightingTest.setTimeStamp(LocalDate.now());
        sightingTest.setSupers(testSighting);

        sightingTest = sightingDao.addSighting(sightingTest);

        Sighting sightingBoth = new Sighting();
        sightingBoth.setLocation(location);
        sightingBoth.setTimeStamp(LocalDate.now());
        sightingBoth.setSupers(bothSighting);

        sightingBoth = sightingDao.addSighting(sightingBoth);

        List<Sighting> sightings = sightingDao.getAllSightingsOfSuper(yesMan);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sightingBoth));
        assertTrue(sightings.contains(sightingYes));
        assertFalse(sightings.contains(sightingTest));
    }

    /**
     * Test of getAllSightingsOfLocation method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightingsOfLocation() {
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

        Supers superOne = new Supers();
        superOne.setName("Test Super name");
        superOne.setSuperPower("Test Super superPower");
        superOne.setDescription("Test Super Description");
        superOne.setHero(true);
        superOne = supersDao.addSuper(superOne);

        List<Supers> supers = new ArrayList<>();
        supers.add(superOne);

        Sighting sightingOne = new Sighting();
        sightingOne.setLocation(locationOne);
        sightingOne.setTimeStamp(LocalDate.now());
        sightingOne.setSupers(supers);

        sightingOne = sightingDao.addSighting(sightingOne);

        Sighting sightingTwo = new Sighting();
        sightingTwo.setLocation(locationTwo);
        sightingTwo.setTimeStamp(LocalDate.now());
        sightingTwo.setSupers(supers);

        sightingTwo = sightingDao.addSighting(sightingTwo);

        List<Sighting> sightings = sightingDao.getAllSightingsOfLocation(locationTwo);
        assertEquals(1, sightings.size());
        assertTrue(sightings.contains(sightingTwo));
        assertFalse(sightings.contains(sightingOne));

    }

}
