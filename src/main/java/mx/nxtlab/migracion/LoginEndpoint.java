package mx.nxtlab.migracion;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

/*
 * A simple login endpoint
 */
@RestController
public class LoginEndpoint {

    @Inject
    private UserDao dao;
    private final LoginResponse noSuchUserResponse = new LoginResponse();

    @PostConstruct
    public void init(){
        noSuchUserResponse.setSuccess(false);
        noSuchUserResponse.setError("No such user");
    }

    @RequestMapping("/login/{userId}")
    public LoginResponse login(@PathVariable long userId, String password) {
        return dao.getUser(userId).map(u-> {
            LoginResponse resp = new LoginResponse();
            if(u.getStatus() != 1) {
                resp.setError("User is blocked or inactive");
            } else if (u.validatePassword(password)) {
                resp.setUser(u);
                resp.setSuccess(true);
            } else {
                resp.setError("Invalid password");
            }
            return resp;
        }).orElse(noSuchUserResponse);
    }

}
