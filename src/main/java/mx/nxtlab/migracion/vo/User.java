package mx.nxtlab.migracion.vo;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private int status;
    private Date lastSeen;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public boolean validatePassword(String pass) {
        return pass != null && pass.equals(password);
    }
}
