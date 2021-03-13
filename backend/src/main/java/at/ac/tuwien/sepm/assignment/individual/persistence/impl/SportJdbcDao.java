package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        return sports;
    }


    private Sport mapRow(ResultSet resultSet, int i) throws SQLException {
        final Sport sport = new Sport();
        sport.setId(resultSet.getLong("id"));
        sport.setName(resultSet.getString("name"));
        return sport;
    }
}
