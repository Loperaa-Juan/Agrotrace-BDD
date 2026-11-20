package exceptions;

// Falla cuando una cosecha con datos válidos no queda publicada (calidad de la IA o mensaje de confirmación)
public class PublicacionFallidaException extends AssertionError {

    public static final String MENSAJE = "La cosecha con datos válidos no quedó publicada";

    // Serenity crea la excepción con este constructor cuando falla un seeThat(...).orComplainWith(...)
    public PublicacionFallidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
