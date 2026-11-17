package userInterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CatalogoUI {
    // Lista de productos disponibles en Agrotrace
    public static final Target LISTA_PRODUCTOS = Target.the("Lista de productos disponibles en la plataforma").located(By.id("cosechas"));
}
