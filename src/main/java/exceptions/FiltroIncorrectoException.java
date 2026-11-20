package exceptions;

// Falla cuando los filtros muestran cosechas que no cumplen el filtro u ocultan las que sí lo cumplen
public class FiltroIncorrectoException extends AssertionError {

    public static final String MENSAJE = "El filtro no muestra los resultados esperados";

    // Serenity crea la excepción con este constructor cuando falla un seeThat(...).orComplainWith(...)
    public FiltroIncorrectoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
