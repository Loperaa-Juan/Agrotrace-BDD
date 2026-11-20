package exceptions;

// Falla cuando un paso del feature trae un valor que el step definition no conoce (una calidad, un grado o un rol)
public class ValorDelFeatureNoValidoException extends IllegalArgumentException {

    public ValorDelFeatureNoValidoException(String mensaje) {
        super(mensaje);
    }
}
