package exceptions;

// Falla cuando una cosecha que se debía rechazar no se rechaza o aparece en el catálogo
public class RechazoNoAplicadoException extends AssertionError {

    public static final String MENSAJE = "La cosecha debía ser rechazada y la app no la rechazó";

    // Serenity crea la excepción con este constructor cuando falla un seeThat(...).orComplainWith(...)
    public RechazoNoAplicadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
