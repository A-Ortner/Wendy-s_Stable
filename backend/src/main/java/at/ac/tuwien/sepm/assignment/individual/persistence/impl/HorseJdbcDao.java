package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
        horse.setFavSportID(resultSet.getLong("favsportid"));
        return horse;
    }

    @Override
    public Horse createHorse(Horse horse) throws PersistenceException {
        LOGGER.trace("createHorse({})", horse.toString());
        final String sql = "INSERT INTO " + TABLE_NAME + " (name, sex, dateofbirth, description, favsportid)" + " VALUES (?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, horse.getName());
                stmt.setString(2, horse.getSex().name());
                stmt.setDate(3, java.sql.Date.valueOf(horse.getDateOfBirth()));
                stmt.setString(4, horse.getDescription());
                stmt.setLong(5, horse.getFavSportID());
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
        return horses;
    }
}
