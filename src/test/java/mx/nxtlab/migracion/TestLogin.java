package mx.nxtlab.migracion;

import org.junit.runner.RunWith;

/**
 * Test the login endpoint.
 *
 * @author Raul Estrada
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment)
@TestPropertySource("classpath:test.properties")
public class TestLogin {
}
