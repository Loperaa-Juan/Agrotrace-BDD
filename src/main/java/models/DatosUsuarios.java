package models;

// Crea usuarios de prueba con datos válidos para el formulario de registro.
// El correo y el número de documento cambian en cada usuario para no chocar con cuentas ya creadas.
public class DatosUsuarios {

    private static final String TIPO_DOCUMENTO_PERSONA = "CC";
    // PENDIENTE: reemplazar por el texto exacto de la opción NIT en la lista "Tipo de documento"
    private static final String TIPO_DOCUMENTO_NEGOCIO = "NIT";

    // Parte de la hora actual y suma 1 por cada usuario creado:
    // no se repite ni dentro de una ejecución ni entre ejecuciones.
    private static long contador = System.currentTimeMillis();

    // Usuario nuevo del tipo indicado ("agricultor", "persona" o "negocio")
    public static UsuarioRegistro nuevo(String tipo) {
        String unico = valorUnico();

        return new UsuarioRegistro(
                "Prueba " + tipo,
                tipoDocumentoPara(tipo),
                unico,
                tipo + "." + unico + "@prueba.com",
                "3001234567",
                "Clave123*",
                "Calle 38 Sur # 43-20",
                "Envigado");
    }

    // Usuario nuevo con un correo dado (para el test de correo repetido).
    // El documento sí es nuevo, para que el único dato repetido sea el correo.
    public static UsuarioRegistro nuevoConCorreo(String tipo, String correo) {
        UsuarioRegistro base = nuevo(tipo);
        return new UsuarioRegistro(
                base.getNombreCompleto(),
                base.getTipoDocumento(),
                base.getNumeroDocumento(),
                correo,
                base.getTelefono(),
                base.getContrasena(),
                base.getDireccion(),
                base.getMunicipio());
    }

    // Usuario nuevo con un número de documento dado (para el test de NIT repetido).
    // El correo sí es nuevo, para que el único dato repetido sea el documento.
    public static UsuarioRegistro nuevoConDocumento(String tipo, String numeroDocumento) {
        UsuarioRegistro base = nuevo(tipo);
        return new UsuarioRegistro(
                base.getNombreCompleto(),
                base.getTipoDocumento(),
                numeroDocumento,
                base.getCorreo(),
                base.getTelefono(),
                base.getContrasena(),
                base.getDireccion(),
                base.getMunicipio());
    }

    private static String tipoDocumentoPara(String tipo) {
        return tipo.equals("negocio") ? TIPO_DOCUMENTO_NEGOCIO : TIPO_DOCUMENTO_PERSONA;
    }

    // 10 dígitos distintos en cada llamada
    private static String valorUnico() {
        contador++;
        return String.valueOf(contador).substring(3);
    }
}
