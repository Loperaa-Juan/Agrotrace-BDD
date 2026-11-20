package models;

import exceptions.RecorridoIncompletoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CosechasRegistradas {

    private static final Map<String, DatosCosecha> cosechas = new HashMap<>();

    // Cosecha que la IA rechazó (calidad C). Va aparte para que no entre en obtenerTodas(),
    // que son las cosechas que sí deben aparecer en el catálogo.
    private static DatosCosecha rechazada;

    // Guarda la cosecha con la calidad que le asignó la IA ("Calidad Buena", "Calidad Regular").
    // Si ya había una cosecha con esa calidad, la reemplaza.
    public static void guardar(String calidad, DatosCosecha cosecha) {
        cosechas.put(calidad, cosecha);
    }

    // Devuelve la última cosecha guardada con esa calidad.
    public static DatosCosecha obtener(String calidad) {
        DatosCosecha cosecha = cosechas.get(calidad);
        if (cosecha == null) {
            throw new RecorridoIncompletoException("No hay una cosecha con '" + calidad
                    + "' publicada en esta ejecución.");
        }
        return cosecha;
    }

    // Devuelve todas las cosechas guardadas en esta ejecución.
    // Si no hay ninguna, falla: así la validación del catálogo no pasa sin haber revisado nada.
    public static List<DatosCosecha> obtenerTodas() {
        if (cosechas.isEmpty()) {
            throw new RecorridoIncompletoException("No hay cosechas publicadas en esta ejecución.");
        }
        return List.copyOf(cosechas.values());
    }

    // Guarda la cosecha que la IA rechazó para validar después que no aparece en el catálogo.
    public static void guardarRechazada(DatosCosecha cosecha) {
        rechazada = cosecha;
    }

    // Devuelve la cosecha rechazada en esta ejecución.
    public static DatosCosecha obtenerRechazada() {
        if (rechazada == null) {
            throw new RecorridoIncompletoException("No hay una cosecha rechazada en esta ejecución.");
        }
        return rechazada;
    }
}
