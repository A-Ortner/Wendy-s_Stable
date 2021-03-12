package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;

/**
 * This component is only created, if the profile {@code datawipe} is active
 * You can activate this profile by adding {@code -Dspring.profiles.active=datawipe} to your maven command line
 */
@Configuration
@Profile("datawipe")
public class DataWipeBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private DataSource source;

    public DataWipeBean(DataSource source) {
        this.source = source;
    }

    /**
     * Executed once when the component is instantiated. Wipes the whole database
     */
    @PostConstruct
    void wipeDatabase () {
        try {
            ScriptUtils.executeSqlScript(source.getConnection(), new ClassPathResource("sql/dropData.sql"));
        } catch (Exception e) {
            LOGGER.error("Error wiping database", e);
        }
    }
}
