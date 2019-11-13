package mx.nxtlab.migracion;

import io.micronaut.context.annotation.Context;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Context
public class SetupDatabase {

    @Inject
    private JdbcTemplate jdbc;

    @PostConstruct
    public void init() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("tables.sql"));
        populator.addScript(new ClassPathResource("data.sql"));
        populator.execute(jdbc.getDataSource());
    }
}
