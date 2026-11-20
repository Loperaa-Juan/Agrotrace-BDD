package exceptions;

// Falla cuando una cosecha publicada no aparece en el catálogo o en los resultados de búsqueda
public class CosechaNoVisibleException extends AssertionError {

    public static final String MENSAJE = "Una cosecha publicada no aparece en la página";

    // Serenity crea la excepción con este constructor cuando falla un seeThat(...).orComplainWith(...)
    public CosechaNoVisibleException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
