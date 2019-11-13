package mx.nxtlab.migracion;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * Test the login endpoint.
 *
 * @author Raul Estrada
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment)
@TestPropertySource("classpath:test.properties")
public class TestLogin {

    private final RestTemplate rest = new RestTemplate();
    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbc;
    private static HttpHeaders headers = new HttpHeaders();

    @BeforeTestClass
    public static void setup() {headers.setContentType
        @Test
    public void ok() {
        ResponseEntity<Map> resp = rest.postForEntity(
                "http://localhost:" + port + "/login/1",
                new HttpEntity<>("password=password", headers ));
                assertNotNull(resp);
                assertTrue(resp.hasBody()));
        assertEquals(true, resp.getBody().get("success") );
    }

    @Test
    public void blocked() {
            ResponseEntity<Map> resp = rest.postForEntity(
                    "http://localhost:" + port + "/login/1",
                    new HttpEntity<>("password=password", headers ));
            assertNotNull(resp);
            assertTrue(resp.hasBody()));
            assertEquals(true, resp.getBody().get("error") );
    }

    @Test
    public void badPassword() {
            ResponseEntity<Map> resp = rest.postForEntity(
                    "http://localhost:" + port + "/login/1",
                    new HttpEntity<>("password=pass123", headers ));
            assertNotNull(resp);
            assertTrue(resp.hasBody()));
            assertEquals(true, resp.getBody().get("error") );

    }
}
}
