package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.util.Sexes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class HorseJdbcDao implements HorseDao {

    private static final String TABLE_NAME = "horse";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HorseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Horse mapRow(ResultSet resultSet, int i) throws SQLException {
        final Horse horse = new Horse();
        horse.setId(resultSet.getLong("id"));
        horse.setName(resultSet.getString("name"));

        //todo: check if better to change from enum to string an make enum in frontend
        if(resultSet.getString("sex").equals("M")) horse.setSex(Sexes.M);
        else horse.setSex(Sexes.W);

        horse.setDateOfBirth((resultSet.getDate("dateofbirth").toLocalDate()));
        horse.setDescription(resultSet.getString("description"));
        horse.setFavSportId((Long) resultSet.getObject("favsportid"));
        horse.setParent1Id((Long) resultSet.getObject("parent1id"));
        horse.setParent2Id((Long) resultSet.getObject("parent2id"));
        return horse;
    }

    @Override
    public Horse createHorse(Horse horse) throws PersistenceException {
        LOGGER.trace("createHorse({})", horse.toString());
        final String sql = "INSERT INTO " + TABLE_NAME + " (name, sex, dateofbirth, description, favsportid, parent1id, parent2id)" + " VALUES (?,?,?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, horse.getName());
                stmt.setString(2, horse.getSex().name());
                stmt.setDate(3, java.sql.Date.valueOf(horse.getDateOfBirth()));
                stmt.setString(4, horse.getDescription());
                stmt.setObject(5, horse.getFavSportId());
                stmt.setObject(6, horse.getParent1Id());
                stmt.setObject(7, horse.getParent2Id());
                return stmt;
            }, keyHolder);
        } catch (Exception e) {
            LOGGER.error("PersistenceException {}{}", e.getMessage(), e.getStackTrace());
            throw new PersistenceException("Error during saving horse: " + e.getMessage());
        }
        horse.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        LOGGER.info("Saved horse {}", horse.toString());
        return horse;
    }

    @Override
    public List<Horse> getAllHorses() {
        LOGGER.trace("getAllHorses()");
        final String sql = "SELECT * FROM " + TABLE_NAME + ";";
        List<Horse> horses = new LinkedList<>();
        try {
            horses = jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new PersistenceException("Error during running the query in database: " + e.getMessage());
        }
        for (Horse h : horses) {
            LOGGER.info(h.toString());
        }
        return horses;
    }

    @Override
    public Horse getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Horse> horses = jdbcTemplate.query(sql, this::mapRow, id);

        if (horses.isEmpty()) throw new NotFoundException("Could not find sport with id " + id);

        return horses.get(0);
    }

    @Override
    public Horse updateHorse(Horse horse) throws PersistenceException {
        LOGGER.info("Update horse with id {}", horse.getId()); //todo: make trace
            /*if(horse.getParent1Id() == 0) horse.setParent1Id(null);
            if(horse.getParent2Id() == 0) horse.setParent2Id(null);
            if(horse.getFavSportId() == 0) horse.setFavSportId(null);*/


        final String sql = "UPDATE " + TABLE_NAME + " SET" + " id=?, name= ?, sex= ?, dateofbirth= ?, description= ?, favsportid= ?, parent1id= ?, parent2id= ?" + "WHERE id=" + horse.getId().intValue() + ";";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, horse.getId().intValue());
                stmt.setString(2, horse.getName());
                stmt.setString(3, horse.getSex().name());
                stmt.setDate(4, java.sql.Date.valueOf(horse.getDateOfBirth()));
                stmt.setString(5, horse.getDescription());
                stmt.setObject(6, horse.getFavSportId());
                stmt.setObject(7, horse.getParent1Id());
                stmt.setObject(8, horse.getParent2Id());
                return stmt;
            }, keyHolder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("Error during updating horse: " + e.getMessage());
        }
        return horse;
    }
}
