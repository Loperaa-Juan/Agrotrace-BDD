package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CosechasRegistradas {

    private static final Map<String, DatosCosecha> cosechas = new HashMap<>();

    // Guarda la cosecha con la calidad que le asignó la IA ("Calidad Buena", "Calidad Regular").
    // Si ya había una cosecha con esa calidad, la reemplaza.
    public static void guardar(String calidad, DatosCosecha cosecha) {
        cosechas.put(calidad, cosecha);
    }

    // Devuelve la última cosecha guardada con esa calidad.
    public static DatosCosecha obtener(String calidad) {
        DatosCosecha cosecha = cosechas.get(calidad);
        if (cosecha == null) {
            throw new IllegalStateException("No hay una cosecha con '" + calidad
                    + "' publicada en esta ejecución. Ejecuta el feature completo.");
        }
        return cosecha;
    }

    // Devuelve todas las cosechas guardadas en esta ejecución.
    // Si no hay ninguna, falla: así la validación del catálogo no pasa sin haber revisado nada.
    public static List<DatosCosecha> obtenerTodas() {
        if (cosechas.isEmpty()) {
            throw new IllegalStateException("No hay cosechas publicadas en esta ejecución. "
                    + "Ejecuta el feature completo.");
        }
        return List.copyOf(cosechas.values());
    }
}
