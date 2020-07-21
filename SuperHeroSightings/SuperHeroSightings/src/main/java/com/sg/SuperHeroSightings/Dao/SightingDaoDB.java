/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSightings.Dao;

import com.sg.SuperHeroSightings.Dao.LocationDaoDB.LocationMapper;
import com.sg.SuperHeroSightings.Dao.SupersDaoDB.SupersMapper;
import com.sg.SuperHeroSightings.Entity.Location;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Sighting getSightingById(int id) {
        try {
            Sighting sighting = jdbc.queryForObject("SELECT * FROM Sighting WHERE ID_Sighting = ?", new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(id));
            sighting.setSupers(getSupersForSighting(id));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSighting(int id) {
        return jdbc.queryForObject("Select l.* FROM Location l "
                + "Join Sighting sight ON sight.ID_Location= l.ID_Location "
                + "WHERE sight.ID_Sighting = ?", new LocationMapper(), id);
    }

    private List<Supers> getSupersForSighting(int id) {
        return jdbc.query("SELECT Supers.* FROM Supers "
                + "JOIN Super_Sightings ON Super_Sightings.ID_Super=Supers.ID_Super "
                + "WHERE Super_Sightings.ID_Sighting=?", new SupersMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = jdbc.query("SELECT * FROM Sighting", new SightingMapper());
        associateLocationAndSupers(sightings);
        return sightings;
    }

    private void associateLocationAndSupers(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            sighting.setSupers(getSupersForSighting(sighting.getId()));
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        jdbc.update("INSERT INTO Sighting(ID_Location, Date) "
                + "VALUES(?, ?)",
                sighting.getLocation().getId(),
                sighting.getTimeStamp());
        Integer newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        insertSightingToSuper(sighting);
        return sighting;
    }

    private void insertSightingToSuper(Sighting sighting) {
        for (Supers supers : sighting.getSupers()) {
            jdbc.update("INSERT INTO Super_Sightings(ID_Super, ID_Sighting) "
                    + "VALUES(?, ?)",
                    supers.getId(),
                    sighting.getId());
        }
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        jdbc.update("UPDATE Sighting SET ID_Location = ?, Date = ? Where ID_Sighting = ?",
                sighting.getLocation().getId(),
                sighting.getTimeStamp(),
                sighting.getId());
        jdbc.update("DELETE FROM Super_Sightings WHERE ID_Sighting = ?",
                sighting.getId());
        insertSightingToSuper(sighting);
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        jdbc.update("DELETE FROM Super_Sightings WHERE ID_Sighting = ?", id);
        jdbc.update("DELETE FROM Sighting WHERE ID_Sighting = ?", id);
    }

    @Override
    public List<Sighting> getAllSightingsOfSuper(Supers supers) {
        List<Sighting> sightings = jdbc.query("Select Sighting.* FROM Sighting "
                + "JOIN Super_Sightings ON Super_Sightings.ID_Sighting= Sighting.ID_Sighting "
                + "WHERE Super_Sightings.ID_Super=?", new SightingMapper(), supers.getId());
        associateLocationAndSupers(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getAllSightingsOfLocation(Location location) {
        List<Sighting> sightings = jdbc.query("Select * FROM sighting WHERE "
                + "ID_Location= ?", new SightingMapper(), location.getId());
        associateLocationAndSupers(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getTheMostRecentSightings() {
        List<Sighting> sightings = jdbc.query("SELECT * FROM sighting "
                + "Order By Date Desc LIMIT 0, 10",
                new SightingMapper());
        associateLocationAndSupers(sightings);
        return sightings;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting result = new Sighting();
            result.setId(rs.getInt("ID_Sighting"));
            result.setTimeStamp(rs.getDate("Date").toLocalDate());
            return result;
        }
    }
}
