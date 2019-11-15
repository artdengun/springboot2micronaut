package mx.nxtlab.migracion;

@Http
public interface ClienteLogin {
    public LoginResponse login(long userId, String password);

}
