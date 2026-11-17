package userInterface;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

@DefaultUrl("http://localhost:5173/publicar")
public class PublicarOfertaUI {
    public static final Target BTN_SIGUIENTE = Target.the("Botón para continuar con el resto de datos").located(By.id("btn-siguiente"));
    public static final Target MSG_ERROR_PRODUCTO = Target.the("Mensaje de error de producto").located(By.id("producto-error"));
    public static final Target MSG_ERROR_CANTIDAD = Target.the("Mensaje de error de cantidad").located(By.id("cantidad-error"));
    public static final Target MSG_ERROR_PRECIO = Target.the("Mensaje de error de precio").located(By.id("precio-error"));
    public static final Target MSG_ERROR_FOTO = Target.the("Mensaje de error de foto").located(By.id("foto-error"));
}
