package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CosechasRegistradas {

    private static final Map<String, DatosCosecha> cosechas = new HashMap<>();

    // Guarda la cosecha usando el nombre de su foto como clave, porque cada escenario usa una foto distinta.
    // Si ya había una cosecha con esa foto, la reemplaza.
    public static void guardar(DatosCosecha cosecha) {
        cosechas.put(cosecha.getFoto(), cosecha);
    }

    // Devuelve la cosecha guardada con esa foto.
    public static DatosCosecha obtener(String foto) {
        DatosCosecha cosecha = cosechas.get(foto);
        if (cosecha == null) {
            throw new IllegalStateException("No hay una cosecha con la foto '" + foto
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
