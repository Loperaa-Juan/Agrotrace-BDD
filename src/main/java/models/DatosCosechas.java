package models;

import java.time.LocalDate;

// Crea cosechas de prueba con datos válidos para el formulario de publicación.

public class DatosCosechas {

    // Parte de la hora actual y suma 1 por cada cosecha creada:
    // la descripción no se repite ni dentro de una ejecución ni entre ejecuciones.
    private static long contador = System.currentTimeMillis();

    public static DatosCosecha nueva(String producto, String foto) {
        return new DatosCosecha(
                producto,
                100,
                5000,
                foto,
                "Cosecha de prueba creada por la automatización " + valorUnico(),
                LocalDate.now());
    }

    // 10 dígitos distintos en cada llamada
    private static String valorUnico() {
        contador++;
        return String.valueOf(contador).substring(3);
    }
}
