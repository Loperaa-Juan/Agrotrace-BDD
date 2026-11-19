package userInterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CatalogoUI {
    // Lista de productos disponibles en Agrotrace
    public static final Target LISTA_PRODUCTOS = Target.the("Lista de productos disponibles en la plataforma").located(By.id("cosechas"));

    // Tarjeta del catálogo que muestra una cosecha con producto {0}, descripción {1}, precio {2} y cantidad {3}
    public static final Target TARJETA_COSECHA = Target.the("Tarjeta de la cosecha de {0}").locatedBy(
            "//ul[@id='cosechas']/li["
                    + ".//h2[normalize-space()='{0}'] and "
                    + ".//p[normalize-space()='{1}'] and "
                    + ".//p[starts-with(normalize-space(), '$ {2} COP')] and "
                    + ".//p[starts-with(normalize-space(), '{3} ') and contains(., 'disponibles')]]");
}
