package exceptions;

// Falla cuando el usuario, después de registrarse o iniciar sesión, no llega a la página o al perfil esperado
public class RedireccionIncorrectaException extends AssertionError {

    public static final String MENSAJE = "El usuario no llegó a la página o al perfil esperado";

    // Serenity crea la excepción con este constructor cuando falla un seeThat(...).orComplainWith(...)
    public RedireccionIncorrectaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
