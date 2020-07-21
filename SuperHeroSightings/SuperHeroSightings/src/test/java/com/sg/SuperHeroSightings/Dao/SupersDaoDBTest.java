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
public class SupersDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SupersDao supersDao;

    @Autowired
    SightingDao sightingDao;

    public SupersDaoDBTest() {
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
     * Test of getSuperById method, of class SupersDaoDB.
     */
    @Test
    public void testAddAndGetSuperById() {
        Supers testMan = new Supers();
        testMan.setName("Test Super name");
        testMan.setSuperPower("Test Super superPower");
        testMan.setDescription("Test Super Description");
        testMan.setHero(true);
        testMan = supersDao.addSuper(testMan);

        Supers fromDao = supersDao.getSuperById(testMan.getId());

        assertEquals(testMan, fromDao);

    }

    /**
     * Test of getAllSupers method, of class SupersDaoDB.
     */
    @Test
    public void testGetAllSupers() {
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

        List<Supers> supers = supersDao.getAllSupers();

        assertEquals(3, supers.size());
        assertTrue(supers.contains(superOne));
        assertTrue(supers.contains(superTwo));
        assertTrue(supers.contains(superThree));
    }

    /**
     * Test of updateSuper method, of class SupersDaoDB.
     */
    @Test
    public void testUpdateSuper() {
        Supers testMan = new Supers();
        testMan.setName("Test Super name");
        testMan.setSuperPower("Test Super superPower");
        testMan.setDescription("Test Super Description");
        testMan.setHero(true);
        testMan = supersDao.addSuper(testMan);

        Supers fromDao = supersDao.getSuperById(testMan.getId());

        assertEquals(testMan, fromDao);

        testMan.setName("I change my Name");
        supersDao.updateSuper(testMan);

        assertFalse(testMan.equals(fromDao));

        fromDao = supersDao.getSuperById(testMan.getId());
        assertEquals(testMan, fromDao);
    }

    /**
     * Test of deleteSuperById method, of class SupersDaoDB.
     */
    @Test
    public void testDeleteSuperById() {
        Supers testMan = new Supers();
        testMan.setName("Test Super name");
        testMan.setSuperPower("Test Super superPower");
        testMan.setDescription("Test Super Description");
        testMan.setHero(true);
        testMan = supersDao.addSuper(testMan);

        Supers fromDao = supersDao.getSuperById(testMan.getId());

        assertEquals(testMan, fromDao);

        supersDao.deleteSuperById(testMan.getId());

        fromDao = supersDao.getSuperById(testMan.getId());
        assertNull(fromDao);

    }

    /**
     * Test of getAllSupersInOrginization method, of class SupersDaoDB.
     */
    @Test
    public void testGetAllSupersInOrganization() {
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

        List<Supers> supers = new ArrayList<>();
        supers.add(superTwo);
        supers.add(superOne);

        Organization org = new Organization();
        org.setName("Test Name TWO");
        org.setDescription("Test Description TWO");
        org.setAddress("Test Address TWO");
        org.setContactInfo("Test Contact TWO");
        org.setSupers(supers);
        org = organizationDao.addOrganization(org);
        
        List<Supers> supersOrg = supersDao.getAllSupersInOrganization(org);
        
        assertEquals(2,supersOrg.size());
        

    }

    /**
     * Test of getAllSupersSightedAtLocations method, of class SupersDaoDB.
     */
    @Test
    public void testGetAllSupersSightedAtLocations() {
    }

}
