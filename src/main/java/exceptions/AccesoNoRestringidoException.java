package exceptions;

// Falla cuando un usuario entra a una página o ve una opción que no le corresponde
public class AccesoNoRestringidoException extends AssertionError {

    public static final String MENSAJE = "El usuario pudo entrar a una opción que no le corresponde";

    // Serenity crea la excepción con este constructor cuando falla un seeThat(...).orComplainWith(...)
    public AccesoNoRestringidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
