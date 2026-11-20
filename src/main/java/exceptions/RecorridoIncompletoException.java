package exceptions;

// Falla cuando un escenario necesita un dato (un usuario o una cosecha) que debía crear un escenario anterior del recorrido.
// Pasa, por ejemplo, si se ejecuta un escenario o un feature suelto en vez del recorrido completo.
public class RecorridoIncompletoException extends IllegalStateException {

    public RecorridoIncompletoException(String mensaje) {
        super(mensaje + " Ejecuta el recorrido completo: 01, 02 y 03 en orden.");
    }
}
