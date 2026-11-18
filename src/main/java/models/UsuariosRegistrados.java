package models;

import java.util.HashMap;
import java.util.Map;

// Memoria compartida del recorrido encadenado.
// Guarda los usuarios registrados en un test para que los tests siguientes los usen
// (por ejemplo, el correo del agricultor para el test de correo repetido).
// Es estática porque Cucumber crea una instancia nueva de los step definitions en cada escenario.
public class UsuariosRegistrados {

    private static final Map<String, UsuarioRegistro> usuarios = new HashMap<>();

    // Guarda el usuario con su tipo ("agricultor", "persona" o "negocio").
    // Si ya había uno de ese tipo, lo reemplaza.
    public static void guardar(String tipo, UsuarioRegistro usuario) {
        usuarios.put(tipo, usuario);
    }

    // Devuelve el último usuario guardado de ese tipo.
    public static UsuarioRegistro obtener(String tipo) {
        UsuarioRegistro usuario = usuarios.get(tipo);
        if (usuario == null) {
            throw new IllegalStateException("No hay un usuario '" + tipo
                    + "' registrado en esta ejecución. Ejecuta el feature completo.");
        }
        return usuario;
    }
}