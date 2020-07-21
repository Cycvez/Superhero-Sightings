/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.Dao;

import com.sg.SuperHeroSightings.Entity.Location;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            return jdbc.queryForObject("SELECT * FROM Location WHERE ID_Location = ?", new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocation() {
        return jdbc.query("SELECT * FROM Location", new LocationMapper());
    }

    @Override
    public Location addLocation(Location location) {
        jdbc.update("INSERT INTO Location(Name, Description, Address, Latitude, Longitude) "
                + "VALUES(?, ?, ?, ?, ?)",
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLoaction(Location location) {
        jdbc.update("UPDATE Location SET Name = ?, Description = ?, "
                + "Address = ?, Latitude = ?, Longitude = ? WHERE ID_Location = ?",
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        jdbc.update("DELETE Super_Sightings.* From Super_Sightings "
                + "JOIN Sighting ON Super_Sightings.ID_Sighting= Sighting.ID_Sighting "
                + "WHERE Sighting.ID_Location = ?", id);
        jdbc.update("DELETE FROM Sighting WHERE ID_Location = ?", id);
        jdbc.update("DELETE FROM Location WHERE ID_Location = ?", id);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location result = new Location();
            result.setId(rs.getInt("ID_Location"));
            result.setName(rs.getString("Name"));
            result.setDescription(rs.getString("Description"));
            result.setAddress(rs.getString("Address"));
            result.setLatitude(rs.getDouble("Latitude"));
            result.setLongitude(rs.getDouble("Longitude"));

            return result;
        }
    }

}
