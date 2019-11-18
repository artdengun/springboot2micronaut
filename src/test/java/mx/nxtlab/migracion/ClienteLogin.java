package mx.nxtlab.migracion;

import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client(value="http://localhost:8081")
public interface ClienteLogin {

    @Post(value = "/login/{userId}", produces = "application/x-www-form-urlencoded")
    LoginResponse login(long userId, String password);

}
