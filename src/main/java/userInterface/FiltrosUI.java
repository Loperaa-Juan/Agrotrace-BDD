package userInterface;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

@DefaultUrl("http://localhost:5173/filtros")
public class FiltrosUI extends PageObject {
    // Input donde se escribe el nombre del producto que se quiere buscar
    public static final Target INPUT_BUSCAR_PRODUCTO = Target.the("Input para buscar productos por nombre").located(By.id("input-filtro"));

    // Botón que busca los productos con el nombre escrito
    public static final Target BTN_BUSCAR = Target.the("Botón para buscar los productos").located(By.id("btn-search"));

    // Label del checkbox que filtra las cosechas de calidad A (el checkbox está oculto, se hace clic en su label)
    public static final Target CHECKBOX_CALIDAD_A = Target.the("Checkbox de cosechas de calidad A").located(By.xpath("//label[input[@id='filtro-calidad-A']]"));

    // Label del checkbox que filtra las cosechas de calidad B (el checkbox está oculto, se hace clic en su label)
    public static final Target CHECKBOX_CALIDAD_B = Target.the("Checkbox de cosechas de calidad B").located(By.xpath("//label[input[@id='filtro-calidad-B']]"));

    // Checkbox oculto de calidad A: se usa para saber si el filtro está marcado
    public static final Target ESTADO_CALIDAD_A = Target.the("Estado del checkbox de calidad A").located(By.id("filtro-calidad-A"));

    // Checkbox oculto de calidad B: se usa para saber si el filtro está marcado
    public static final Target ESTADO_CALIDAD_B = Target.the("Estado del checkbox de calidad B").located(By.id("filtro-calidad-B"));

    // Texto que muestra la cantidad de ofertas en base al filtro aplicado
    public static final Target TXT_RESULTADO_FILTRO = Target.the("Cantidad de ofertas en base al filtro aplicado").located(By.id("resultado-filtro"));

    // Deslizador para ajustar el precio máximo de los productos a mostrar (al aplicar los filtros)
    public static final Target SLIDER_LIMITE_PRECIO = Target.the("Deslizador para ajustar el precio máximo de los productos a mostrar").located(By.id("filtro-precio-maximo"));

    // Contador "N cosechas activas" cuando ya cargaron las cosechas (al abrir la página muestra 0 mientras carga)
    public static final Target TXT_COSECHAS_CARGADAS = Target.the("Contador de cosechas activas ya cargadas").located(By.xpath("//p[contains(normalize-space(), 'cosechas activas') and not(starts-with(normalize-space(), '0 '))]"));
}
