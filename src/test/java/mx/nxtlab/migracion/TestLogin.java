package mx.nxtlab.migracion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test the login endpoint.
 *
 * @author Raul Estrada
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test.properties")
public class TestLogin {

    private final RestTemplate rest = new RestTemplate();
    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbc;
    private static HttpHeaders headers = new HttpHeaders();

    public static void setup() {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Test
    public void ok() {
        ResponseEntity<Map> resp = rest.postForEntity(
                "http://localhost:" + port + "/login/1",
                new HttpEntity<>("password=password", headers), Map.class);
        assertNotNull(resp);
        assertTrue(resp.hasBody());
        assertEquals(true, Objects.requireNonNull(resp.getBody()).get("success"));
    }

    @Test
    public void blocked() {
        jdbc.update("UPDATE demo_user SET status=999 WHERE user_id=?", 3);
        ResponseEntity<Map> resp = rest.postForEntity(
                "http://localhost:" + port + "/login/3",
                new HttpEntity<>("password=password", headers), Map.class);
        assertNotNull(resp);
        assertTrue(resp.hasBody());
        assertEquals(false, Objects.requireNonNull(resp.getBody()).get("success"));
        assertTrue(resp.getBody().containsKey("error"));
        assertTrue(resp.getBody().get("error").toString().contains("blocked"));
    }

    @Test
    public void badPassword() {
        ResponseEntity<Map> resp = rest.postForEntity(
                "http://localhost:" + port + "/login/1",
                new HttpEntity<>("password=foo", headers), Map.class);
        assertNotNull(resp);
        assertTrue(resp.hasBody());
        assertEquals(false, Objects.requireNonNull(resp.getBody()).get("success"));
    }
}

