package mx.nxtlab.migracion;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.MediaType;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the login endpoint.
 *
 * @author Raul Estrada
 */

@MicronautTest
public class TestLogin {

    private final RestTemplate rest = new RestTemplate();
    @Value("${micronaut.server.port}")
    private int port;

    @Inject
    private JdbcTemplate jdbc;
    private static HttpHeaders headers = new HttpHeaders();

    @BeforeAll
    public static void setup() {headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)}

    @Test
    public void ok() {
        ResponseEntity<Map> resp = rest.postForEntity(
                "http://localhost:" + port + "/login/1",
                new HttpEntity<>("password=password", headers), Map.class);
        assertNotNull(resp);
        assertTrue(resp.hasBody());
        assertEquals(true, resp.getBody().get("success") );
    }

    @Test
    public void blocked() {
        jdbc.update("UPDATE demo_user SET status=999 WHERE user_id=?", 3);
            ResponseEntity<Map> resp = rest.postForEntity(
                    "http://localhost:" + port + "/login/3",
                    new HttpEntity<>("password=password", headers ), Map.class);
            assertNotNull(resp);
            assertTrue(resp.hasBody());
            assertEquals(false, resp.getBody().get("success") );
            assertTrue(resp.getBody().containsKey("error"));
            assertTrue(resp.getBody().get("error").toString().contains("blocked"));
    }

    @Test
    public void badPassword() {
            ResponseEntity<Map> resp = rest.postForEntity(
                    "http://localhost:" + port + "/login/1",
                    new HttpEntity<>("password=foo", headers ), Map.class);
            assertNotNull(resp);
            assertTrue(resp.hasBody());
            assertEquals(false, resp.getBody().get("success") );

    }
}

