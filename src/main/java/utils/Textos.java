package utils;

// Funciones de apoyo para revisar textos. No tocan el navegador: las usan las tareas y las interacciones.
public class Textos {

    private Textos() {
    }

    // true si el texto tiene algo escrito (no es null ni solo espacios)
    public static boolean tieneValor(String valor) {
        return valor != null && !valor.isBlank();
    }
}
