package mx.nxtlab.migracion;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/*
 * A simple login endpoint
 */
@Controller
public class LoginEndpoint {

    @Inject
    private UserDao dao;
    private final LoginResponse noSuchUserResponse = new LoginResponse();

    @PostConstruct
    public void init(){
        noSuchUserResponse.setSuccess(false);
        noSuchUserResponse.setError("No such user");
    }

    @Post("/login/{userId}")
    public LoginResponse login(@PathVariable long userId, String password) {
        return dao.getUser(userId).map(u-> {
            LoginResponse resp = new LoginResponse();
            if(u.getStatus() != 1) {
                resp.setError("User is blocked or inactive");
            } else if (u.validatePassword(password)) {
                resp.setFirstName(u.getFirstName());
                resp.setLastName(u.getLastName());
                resp.setLastSeen(u.getLastSeen());
                resp.setEmail(u.getEmail());
                resp.setSuccess(true);
            } else {
                resp.setError("Invalid password");
            }
            return resp;
        }).orElse(noSuchUserResponse);
    }

}
