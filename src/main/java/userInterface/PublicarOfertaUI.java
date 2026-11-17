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
}
