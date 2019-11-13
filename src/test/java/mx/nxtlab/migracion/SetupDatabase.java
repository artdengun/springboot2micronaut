package mx.nxtlab.migracion;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

@ManagedBean
@Lazy(false)
public class SetupDatabase {

    @Inject
    private JdbcTemplate jdbc;

    @PostConstruct
    public void init() {
        var populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("tables.sql"));
        populator.addScript(new ClassPathResource("data.sql"));
        populator.execute(jdbc.getDataSource());
    }
}
