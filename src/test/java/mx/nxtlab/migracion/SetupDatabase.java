package mx.nxtlab.migracion;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
@Lazy(false)
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
