package mx.nxtlab.migracion.vo;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    int status;
    Date lastSeen;
    String firstName;
    String lastName;
    String password;
    String email;

    public boolean validatePassword(String pass) {
        return pass != null && pass.equals(password);
    }
}
