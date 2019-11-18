package mx.nxtlab.migracion;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the login endpoint.
 *
 * @author Raul Estrada
 */

@MicronautTest
public class TestLogin {

    @Inject
    private ClienteLogin login;

    @Inject
    private JdbcTemplate jdbc;

    @Test
    public void ok() {
        LoginResponse resp = login.login(1, "password");
        assertNotNull(resp);
        assertTrue(resp.isSuccess());
    }

    @Test
    public void blocked() {
        jdbc.update("UPDATE demo_user SET status=999 WHERE user_id=?", 3);
        LoginResponse resp = login.login(3, "password");
        assertNotNull(resp);
        assertFalse(resp.isSuccess());
        assertTrue(resp.getError().contains("blocked"));
    }

    @Test
    public void badPassword() {
        LoginResponse resp = login.login(1,"foo");
        assertNotNull(resp);
        assertFalse(resp.isSuccess());
        assertTrue(resp.getError().contains("error"));
    }
}

