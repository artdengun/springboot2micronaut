package mx.nxtlab.migracion;

import lombok.Data;
import mx.nxtlab.migracion.vo.User;

@Data
public class LoginResponse {
    boolean success;
    String error;
    User user;
}
