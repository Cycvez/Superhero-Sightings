/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.Dao;

import com.sg.SuperHeroSightings.Entity.Organization;
import com.sg.SuperHeroSightings.Entity.Supers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author carlo
 */
@Repository
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Organization getOrganizationById(int id) {
        try {
            Organization organization = jdbc.queryForObject("SELECT * FROM Organization WHERE ID_Organization = ?", new OrganizationMapper(), id);
            organization.setSupers(getSupersForOrganization(id));
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Supers> getSupersForOrganization(int id) {
        return jdbc.query("SELECT Supers.* FROM Supers "
                + "JOIN Members ON Members.ID_Super= Supers.ID_Super "
                + "WHERE Members.ID_Organization=? ", new SupersDaoDB.SupersMapper(), id);
    }

    @Override
    public List<Organization> getAllOrganization() {
        return jdbc.query("SELECT * FROM Organization", new OrganizationMapper());
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        jdbc.update("INSERT INTO Organization(Name, Description, Address, ContactInfo) "
                + "VALUES(?,?,?,?)",
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContactInfo());

        Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        insertOrganizationToMember(organization);
        return organization;
    }

    private void insertOrganizationToMember(Organization organization) {
        for (Supers supers : organization.getSupers()) {
            jdbc.update("INSERT INTO Members(ID_Super, ID_Organization) "
                    + "VALUES(?, ?)",
                    supers.getId(),
                    organization.getId());
        }
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        jdbc.update("UPDATE Organization SET Name = ?, Description = ?, "
                + "Address = ?, ContactInfo = ? WHERE ID_Organization = ?",
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContactInfo(),
                organization.getId());
        jdbc.update("DELETE FROM Members WHERE ID_Organization = ?",
                organization.getId());
        insertOrganizationToMember(organization);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        jdbc.update("DELETE FROM Members WHERE ID_Organization = ?", id);
        jdbc.update("DELETE FROM Organization WHERE ID_Organization = ?", id);
    }

    @Override
    public List<Organization> getAllOrganizationsForSuper(Supers supers) {
        List<Organization> organizations = jdbc.query("Select Organization.* FROM Organization "
                + "JOIN Members ON Members.ID_Organization= Organization.ID_Organization "
                + "WHERE Members.ID_Super= ?", new OrganizationMapper(), supers.getId());
        return organizations;
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization result = new Organization();
            result.setId(rs.getInt("ID_Organization"));
            result.setName(rs.getString("Name"));
            result.setDescription(rs.getString("Description"));
            result.setAddress(rs.getString("Address"));
            result.setContactInfo(rs.getString("ContactInfo"));
            result.setContactInfo(rs.getString("ContactInfo"));

            return result;
        }
    }
}
