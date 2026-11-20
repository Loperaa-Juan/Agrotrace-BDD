package exceptions;

// Falla cuando un formulario no muestra el mensaje de error esperado (campos obligatorios, datos repetidos o no válidos)
public class MensajeDeErrorNoMostradoException extends AssertionError {

    public static final String MENSAJE = "El formulario no mostró el mensaje de error esperado";

    // Serenity crea la excepción con este constructor cuando falla un seeThat(...).orComplainWith(...)
    public MensajeDeErrorNoMostradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
