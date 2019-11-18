package mx.nxtlab.migracion;

import io.micronaut.context.annotation.Value;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

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
    public static void setup() {headers.setContentType()}

    @Test
    public void ok() {
        ResponseEntity<Map> resp = rest.postForEntity(
                "http://localhost:" + port + "/login/1",
                new HttpEntity<>("password=password", headers ));
                assertNotNull(resp);
                assertTrue(resp.hasBody());
                assertEquals(true, resp.getBody().get("success") );
    }

    @Test
    public void blocked() {
            ResponseEntity<Map> resp = rest.postForEntity(
                    "http://localhost:" + port + "/login/1",
                    new HttpEntity<>("password=password", headers ));
            assertNotNull(resp);
            assertTrue(resp.hasBody());
            assertEquals(true, resp.getBody().get("error") );
    }

    @Test
    public void badPassword() {
            ResponseEntity<Map> resp = rest.postForEntity(
                    "http://localhost:" + port + "/login/1",
                    new HttpEntity<>("password=foo", headers ));
            assertNotNull(resp);
            assertTrue(resp.hasBody());
            assertEquals(false, resp.getBody().get("success") );

    }
}

