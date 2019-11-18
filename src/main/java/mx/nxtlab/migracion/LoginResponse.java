package mx.nxtlab.migracion;

import lombok.Data;
import mx.nxtlab.migracion.vo.User;

@Data
public class LoginResponse {
    private boolean success;
    private String error;
    private User user;
}
