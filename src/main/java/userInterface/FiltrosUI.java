package userInterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class FiltrosUI {
    // Input donde se escribe el nombre del producto que se quiere buscar
    public static final Target INPUT_BUSCAR_PRODUCTO = Target.the("Input para buscar productos por nombre").located(By.id("input-filtro"));

    // Botón que busca los productos con el nombre escrito
    public static final Target BTN_BUSCAR = Target.the("Botón para buscar los productos").located(By.id("btn-search"));

    // Label del checkbox que filtra las cosechas de calidad A (el checkbox está oculto, se hace clic en su label)
    public static final Target CHECKBOX_CALIDAD_A = Target.the("Checkbox de cosechas de calidad A").located(By.xpath("//label[input[@id='filtro-calidad-A']]"));

    // Label del checkbox que filtra las cosechas de calidad B (el checkbox está oculto, se hace clic en su label)
    public static final Target CHECKBOX_CALIDAD_B = Target.the("Checkbox de cosechas de calidad B").located(By.xpath("//label[input[@id='filtro-calidad-B']]"));
}
