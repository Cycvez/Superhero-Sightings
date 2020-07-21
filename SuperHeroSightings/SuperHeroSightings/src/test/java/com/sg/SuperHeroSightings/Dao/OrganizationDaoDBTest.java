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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class OrganizationDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SupersDao supersDao;

    @Autowired
    SightingDao sightingDao;

    public OrganizationDaoDBTest() {
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
     * Test of getOrginizationById method, of class OrginizationDaoDB.
     */
    @Test
    public void testAddAndGetOrganizationById() {
        Supers superOne = new Supers();
        superOne.setName("Test Super name");
        superOne.setSuperPower("Test Super superPower");
        superOne.setDescription("Test Super Description");
        superOne.setHero(true);
        superOne = supersDao.addSuper(superOne);

        List<Supers> supers = new ArrayList<>();
        supers.add(superOne);

        Organization org = new Organization();
        org.setName("Test Name One");
        org.setDescription("Test Description One");
        org.setAddress("Test Address One");
        org.setContactInfo("Test Contact Info");
        org.setSupers(supers);
        org = organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());

        assertEquals(org, fromDao);

    }

    /**
     * Test of getAllOrginization method, of class OrginizationDaoDB.
     */
    @Test
    public void testGetAllOrganization() {
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
        supersTwo.add(superThree);

        Organization org = new Organization();
        org.setName("Test Name One");
        org.setDescription("Test Description One");
        org.setAddress("Test Address One");
        org.setContactInfo("Test Contact Info");
        org.setSupers(supersOne);
        org = organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setName("Test Name TWO");
        org2.setDescription("Test Description TWO");
        org2.setAddress("Test Address TWO");
        org2.setContactInfo("Test Contact TWO");
        org2.setSupers(supersTwo);
        org2 = organizationDao.addOrganization(org2);

        List<Organization> orgs = organizationDao.getAllOrganization();

        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org));
        assertTrue(orgs.contains(org2));
    }

    /**
     * Test of updateOrginization method, of class OrginizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
        Supers superOne = new Supers();
        superOne.setName("Test Super name");
        superOne.setSuperPower("Test Super superPower");
        superOne.setDescription("Test Super Description");
        superOne.setHero(true);
        superOne = supersDao.addSuper(superOne);

        List<Supers> supers = new ArrayList<>();
        supers.add(superOne);

        Organization org = new Organization();
        org.setName("Test Name One");
        org.setDescription("Test Description One");
        org.setAddress("Test Address One");
        org.setContactInfo("Test Contact Info");
        org.setSupers(supers);
        org = organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());

        assertEquals(org, fromDao);

        org.setName("Test New Name");
        organizationDao.updateOrganization(org);

        assertNotEquals(org, fromDao);

        fromDao = organizationDao.getOrganizationById(org.getId());

        assertEquals(org, fromDao);
    }

    /**
     * Test of deleteOrginizationById method, of class OrginizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationById() {
        Supers superOne = new Supers();
        superOne.setName("Test Super name");
        superOne.setSuperPower("Test Super superPower");
        superOne.setDescription("Test Super Description");
        superOne.setHero(true);
        superOne = supersDao.addSuper(superOne);

        List<Supers> supers = new ArrayList<>();
        supers.add(superOne);

        Organization org = new Organization();
        org.setName("Test Name One");
        org.setDescription("Test Description One");
        org.setAddress("Test Address One");
        org.setContactInfo("Test Contact Info");
        org.setSupers(supers);
        org = organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());

        assertEquals(org, fromDao);

        organizationDao.deleteOrganizationById(org.getId());
        fromDao = organizationDao.getOrganizationById(org.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getAllOrginizationsForSuper method, of class OrginizationDaoDB.
     */
    @Test
    public void testGetAllOrganizationsForSuper() {
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

        List<Supers> supersOne = new ArrayList<>();
        supersOne.add(superOne);

        List<Supers> supersTwo = new ArrayList<>();
        supersTwo.add(superTwo);
        supersTwo.add(superOne);

        Organization org = new Organization();
        org.setName("Test Name One");
        org.setDescription("Test Description One");
        org.setAddress("Test Address One");
        org.setContactInfo("Test Contact Info");
        org.setSupers(supersOne);
        org = organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setName("Test Name TWO");
        org2.setDescription("Test Description TWO");
        org2.setAddress("Test Address TWO");
        org2.setContactInfo("Test Contact TWO");
        org2.setSupers(supersTwo);
        org2 = organizationDao.addOrganization(org2);

        List<Organization> orgs = organizationDao.getAllOrganizationsForSuper(superOne);

        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org));
        assertTrue(orgs.contains(org2));
        
    }

}
