package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class SportJdbcDao implements SportDao {

    private static final String TABLE_NAME = "sport";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SportJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Sport getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Sport> sports = jdbcTemplate.query(sql, this::mapRow, id);

        if (sports.isEmpty()) throw new NotFoundException("Could not find sport with id " + id);

        return sports.get(0);
    }

    @Override
    public List<Sport> getAllSports() throws PersistenceException {
        LOGGER.trace("getAllSports()");
        final String sql = "SELECT * FROM " + TABLE_NAME + ";";
        List<Sport> sports = new LinkedList<Sport>();
        try {
            sports = jdbcTemplate.query(sql, this::mapRow);
        } catch (Exception e) {
            throw new PersistenceException("Error during running the query in database: " + e.getMessage());
        }
        for (Sport s: sports) {
            LOGGER.info(s.getId() + s.getName() + s.getDescription());
        }
        return sports;
    }

    @Override
    public Sport createSport(Sport sport) throws PersistenceException {
        LOGGER.trace("createSport({})", sport.toString());
        final String sql = "INSERT INTO " + TABLE_NAME + " (name, description)" + " VALUES (?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, sport.getName());
                stmt.setString(2, sport.getDescription());
                return stmt;
            }, keyHolder);
        } catch (Exception e) {
            LOGGER.error("PersistenceException {}{}", e.getMessage(), e.getStackTrace());
            throw new PersistenceException("Error during saving horse: " + e.getMessage());
        }
        sport.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        LOGGER.info("Saved sport {}", sport.toString());
        return sport;
    }


    private Sport mapRow(ResultSet resultSet, int i) throws SQLException {
        final Sport sport = new Sport();
        sport.setId(resultSet.getLong("id"));
        sport.setName(resultSet.getString("name"));
        sport.setDescription(resultSet.getString("description"));
        return sport;
    }
}
