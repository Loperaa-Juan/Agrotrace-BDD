package models;

public class CredencialesLogin {

    private final String correo;
    private final String contrasena;

    public CredencialesLogin (String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getCorreo(){ return correo; }
    public String getContrasena() { return contrasena; }
}
