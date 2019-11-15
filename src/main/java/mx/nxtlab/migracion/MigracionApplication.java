package mx.nxtlab.migracion;
import javax.inject.Singleton;
import javax.sql.DataSource;

import io.micronaut.context.annotation.Factory;
import io.micronaut.runtime.Micronaut;
import org.springframework.jdbc.core.JdbcTemplate;

@Factory
public class MigracionApplication {

    @Singleton
    public JdbcTemplate jdbcTemplate(DataSource ds){
        JdbcTemplate jdbc = new JdbcTemplate(ds);
        jdbc.setDatabaseProductName("PostgresSQL");
        return jdbc;
    }

    public static void main(String[] args) {
        Micronaut.run(MigracionApplication.class, args);
    }
}