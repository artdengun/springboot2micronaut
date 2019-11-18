package mx.nxtlab.migracion;

import io.micronaut.http.annotation.Controller;
import io.micronaut.context.annotation.Value;
import io.micronaut.runtime.Micronaut;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.inject.Singleton;

@Controller
public class MigracionApplication {

    @Singleton
    public DataSource dataSource(@Value("${datasource.url}") String url,
                                 @Value("${datasource.username}") String username,
                                 @Value("${datasource.password}") String password,
                                 @Value("${datasource.maxtotal}") int maxTotal
                                 ){
        DataSource ds = null;
        //DataSource ds = new BasicDataSource();
        //ds.setUrl(url);
        //ds.setUsername(username);
        //ds.setPassword(password);
        //ds.setMaxTotal(maxTotal);
        return ds;
    }

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