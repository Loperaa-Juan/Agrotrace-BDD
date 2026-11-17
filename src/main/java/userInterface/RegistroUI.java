package userInterface;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

@DefaultUrl("http://localhost:5173/login?modo=registro")
public class RegistroUI extends PageObject {
    // Buscamos el input de Nombre completo
    public static final Target INPUT_NOMBRE = Target.the("Input Nombre").located(By.id("nombre-input"));
    public static final Target MSG_NOMBRE_OBLIGATORIO = Target.the("Error si falta nombre").located(By.id("nombre-input-error"));

    // Tipo de documento
    public static final Target SELECT_TIPO_DOCUMENTO = Target.the("Select del tipo de documento").located(By.id("tipo-documento-input"));

    // Numero de documento (numero-documento-input)
    public static final Target INPUT_NUMERO_DOCUMENTO = Target.the("Input Numero de documento").located(By.id("numero-documento-input"));
    public static final Target MSG_NUMERO_DOCUMENTO_OBLIGATORIO = Target.the("Error si falta número de documento").located(By.id("numero-documento-input-error"));

    // Email (identifier-input)
    public static final Target INPUT_EMAIL = Target.the("Input Email").located(By.id("identifier-input"));
    public static final Target MSG_CORREO_OBLIGATORIO = Target.the("Error si falta correo").located(By.id("identifier-input-error"));

    // Teléfono celular (telefono-input)
    public static final Target INPUT_TELEFONO = Target.the("Input Teléfono celular").located(By.id("telefono-input"));
    public static final Target MSG_TELEFONO_OBLIGATORIO = Target.the("Error si falta teléfono").located(By.id("telefono-input-error"));

    // Contraseña de acceso (password-input)
    public static final Target INPUT_CONTRASENA = Target.the("Input Contraseña de acceso").located(By.id("password-input"));
    public static final Target MSG_CONTRASENA_OBLIGATORIA = Target.the("Error si falta contraseña").located(By.id("password-input-error"));

    // Dirección (direccion-input)
    public static final Target INPUT_DIRECCION = Target.the("Input Dirección").located(By.id("direccion-input"));
    public static final Target MSG_DIRECCION_OBLIGATORIA = Target.the("Error si falta dirección").located(By.id("direccion-input-error"));

    // Municipio (municipio-input)
    public static final Target INPUT_MUNICIPIO = Target.the("Input Municipio").located(By.id("municipio-input"));
    public static final Target MSG_MUNICIPIO_OBLIGATORIO = Target.the("Error si falta municipio").located(By.id("municipio-input-error"));

    // Botón Crear Perfil (register-button)
    public static final Target BTN_CREAR_PERFIL = Target.the("Botón Crear Perfil").located(By.id("register-button"));

    // Botón para desplegar el formulario de registro
    public static final Target BTN_REGISTRARSE = Target.the("Botón para renderizar el formulario de registro").located(By.id("btn-registrarme"));

    // Botones para tipo de registro
    public static final Target BTN_REGISTRO_AGRICULTOR = Target.the("Botón para el registro de los agricultores").located(By.id("agricultor"));
    public static final Target BTN_REGISTRO_PERSONA = Target.the("Botón para el registro de las personas").located(By.id("persona"));
    public static final Target BTN_REGISTRO_NEGOCIO = Target.the("Botón para el registro de las personas").located(By.id("negocio"));
}