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
public class SupersDaoDB implements SupersDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Supers getSuperById(int id) {
        try {
            return jdbc.queryForObject("SELECT * FROM Supers WHERE ID_Super = ?", new SupersMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }
    

    @Override
    public List<Supers> getAllSupers() {
        return jdbc.query("SELECT * FROM Supers", new SupersMapper());
    }

    @Override
    public Supers addSuper(Supers supers) {

        jdbc.update("INSERT INTO Supers(Name, SuperPower, Description, Hero_Villain) "
                + "VALUES(?,?,?,?)",
                supers.getName(),
                supers.getSuperPower(),
                supers.getDescription(),
                supers.isHero());
        Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        supers.setId(newId);
        return supers;
    }

    @Override
    public void updateSuper(Supers supers) {
        jdbc.update("UPDATE Supers SET Name = ?, SuperPower = ?, "
                + "Description = ?, Hero_Villain = ? WHERE ID_Super = ?",
                supers.getName(),
                supers.getSuperPower(),
                supers.getDescription(),
                supers.isHero(),
                supers.getId());
        
    }
//WHERE I LEFT OFFFFFFF
    @Override
    @Transactional
    public void deleteSuperById(int id) {
        jdbc.update("DELETE FROM Members WHERE ID_Super=?", id);
        jdbc.update("DELETE FROM Super_Sightings WHERE ID_Super = ?", id);
        jdbc.update("DELETE FROM Supers WHERE ID_Super = ?", id);
    }

    @Override
    public List<Supers> getAllSupersInOrganization(Organization organization) {
        List<Supers> supersInOrg = jdbc.query("Select Supers.* FROM Supers "
                + "JOIN Members ON Members.ID_Super=Supers.ID_Super "
                + "WHERE Members.ID_Organization=?", new SupersMapper(), organization.getId());
        return supersInOrg;
    }

    @Override
    public List<Supers> getAllSupersSightedAtLocations(Location location) {
        return jdbc.query("SELECT * FROM Supers s"
                + "JOIN Super_Sightings ss ON s.ID_Super= ss.ID_Super"
                + "JOIN Sighting si ON si.ID_Sighting= ss.ID_Sighting"
                + "JOIN Location l ON si.ID_Location= l.ID_Location"
                + "WHERE l.ID_Location = ?",new SupersMapper(),location.getId());
    }

    public static final class SupersMapper implements RowMapper<Supers> {

        @Override
        public Supers mapRow(ResultSet rs, int index) throws SQLException {
            Supers result = new Supers();
            result.setId(rs.getInt("ID_Super"));
            result.setName(rs.getString("Name"));
            result.setSuperPower(rs.getString("SuperPower"));
            result.setDescription(rs.getString("Description"));
            result.setHero(rs.getBoolean("Hero_Villain"));

            return result;
        }
    }
}
