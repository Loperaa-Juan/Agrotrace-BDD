package userInterface;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

@DefaultUrl("http://localhost:5173/publicar")
public class PublicarOfertaUI {
    // Botón que lleva al siguiente paso del formulario
    public static final Target BTN_SIGUIENTE = Target.the("Botón para continuar con el resto de datos").located(By.id("btn-siguiente"));

    // Mensaje de error que aparece cuando no se elige el producto
    public static final Target MSG_ERROR_PRODUCTO = Target.the("Mensaje de error de producto").located(By.id("producto-error"));

    // Mensaje de error que aparece cuando no se escribe la cantidad
    public static final Target MSG_ERROR_CANTIDAD = Target.the("Mensaje de error de cantidad").located(By.id("cantidad-error"));

    // Mensaje de error que aparece cuando no se escribe el precio
    public static final Target MSG_ERROR_PRECIO = Target.the("Mensaje de error de precio").located(By.id("precio-error"));

    // Mensaje de error que aparece cuando no se sube la foto
    public static final Target MSG_ERROR_FOTO = Target.the("Mensaje de error de foto").located(By.id("foto-error"));

    // Input donde se sube la foto de la fruta
    public static final Target INPUT_FOTO = Target.the("Drag and drop para subir las imagenes").located(By.id("foto"));

    // Select donde se elige el producto a publicar
    public static final Target SELECT_PRODUCTO = Target.the("Select para elegir el producto").located(By.id("producto"));

    // Input donde se escribe la cantidad de la cosecha
    public static final Target INPUT_CANTIDAD = Target.the("Input para la cantidad de la cosecha").located(By.id("cantidad"));

    // Input donde se escribe el precio de la cosecha
    public static final Target INPUT_PRECIO = Target.the("Input para el precio de la cosecha").located(By.id("precio"));

    // Input donde se escribe la descripción de la cosecha
    public static final Target INPUT_DESCRIPCION = Target.the("Input para la descripción de la cosecha").located(By.id("descripcion"));

    // Input donde se escribe la fecha de la cosecha
    public static final Target INPUT_FECHA = Target.the("Input para ingresar la fecha de la cosecha").located(By.id("fecha"));

    // Botón para revisar la información de la cosecha antes de publicar
    public static final Target BTN_REVISAR_INFO = Target.the("Botón para revisar la información de la cosecha").located(By.id("btn-revisar-info"));

    // Botón para publicar la cosecha
    public static final Target BTN_PUBLICAR_COSECHA = Target.the("Botón para publicar la cosecha").located(By.id("btn-publicar-cosecha"));

    // Letrero que muestra la calidad asignada por la IA a la cosecha
    public static final Target TXT_CALIDAD_IA = Target.the("Letrero de la calidad asignada por IA").located(By.id("calidad-ia"));

    // Mensaje que aparece cuando la cosecha quedó publicada
    public static final Target MSG_COSECHA_PUBLICADA = Target.the("Mensaje de cosecha publicada").located(By.id("mensaje-publicada"));

    // Botón para volver al perfil después de publicar la cosecha
    public static final Target BTN_VOLVER_PERFIL = Target.the("Botón para volver al perfil").located(By.id("btn-volver-perfil"));

    // Letrero de alerta que aparece cuando la cosecha no es aceptada por ser clasificada como calidad C
    public static final Target MSG_ALERTA_ERROR = Target.the("Alerta de cosecha rechazada por calidad C").located(By.id("alerta-error"));

    // Botón para volver atrás a los datos de la cosecha
    public static final Target BTN_VOLVER_DATOS = Target.the("Botón para volver a los datos de la cosecha").located(By.id("btn-volver-datos"));

    // Botón para volver de la revisión al paso de la fecha. No tiene id: es el botón que está antes del de publicar
    public static final Target BTN_VOLVER_REVISION = Target.the("Botón para volver de la revisión a la fecha de la cosecha").located(By.xpath("//button[@id='btn-publicar-cosecha']/preceding-sibling::button"));
}
