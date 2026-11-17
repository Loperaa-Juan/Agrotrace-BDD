package models;

// Datos del formulario "2. Completa tus datos" de la página de registro.
public class UsuarioRegistro {

    private final String nombreCompleto;
    private final String tipoDocumento;
    private final String numeroDocumento;
    private final String correo;
    private final String telefono;
    private final String contrasena;
    private final String direccion;
    private final String municipio;

    public UsuarioRegistro(String nombreCompleto, String tipoDocumento, String numeroDocumento,
                           String correo, String telefono, String contrasena,
                           String direccion, String municipio) {
        this.nombreCompleto = nombreCompleto;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.direccion = direccion;
        this.municipio = municipio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getMunicipio() {
        return municipio;
    }
}
