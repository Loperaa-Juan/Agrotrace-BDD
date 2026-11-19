package userInterface;

import models.DatosCosecha;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import java.text.NumberFormat;
import java.util.Locale;

public class CatalogoUI {
    // Lista de productos disponibles en Agrotrace
    public static final Target LISTA_PRODUCTOS = Target.the("Lista de productos disponibles en la plataforma").located(By.id("cosechas"));

    // Tarjeta que muestra una cosecha con producto {0}, descripción {1}, precio {2} y cantidad {3}.
    // Sirve en el catálogo y en la página de filtros, que pintan la misma tarjeta.
    public static final Target TARJETA_COSECHA = Target.the("Tarjeta de la cosecha de {0}").locatedBy(
            "//li["
                    + ".//h2[normalize-space()='{0}'] and "
                    + ".//p[normalize-space()='{1}'] and "
                    + ".//p[starts-with(normalize-space(), '$ {2} COP')] and "
                    + ".//p[starts-with(normalize-space(), '{3} ') and contains(., 'disponibles')]]");

    // Tarjeta de cualquier cosecha cuya calidad sea distinta de {0} (por ejemplo, distinta de "Calidad Buena")
    public static final Target TARJETA_OTRA_CALIDAD = Target.the("Tarjeta de una cosecha con calidad distinta de {0}").locatedBy(
            "//li[.//h2 and .//span[starts-with(normalize-space(), 'Calidad ') and normalize-space() != '{0}']]");

    // Precio de cada tarjeta visible, con el formato "$ 5.000 COP / unidad"
    public static final Target PRECIOS_TARJETAS = Target.the("Precios de las tarjetas de cosechas").locatedBy(
            "//li[.//h2]//p[starts-with(normalize-space(), '$ ') and contains(., 'COP')]");

    // Tarjeta de una cosecha concreta, con los números como los muestra la app (toLocaleString("es-CO"): 5000 -> "5.000")
    public static Target tarjetaDe(DatosCosecha cosecha) {
        NumberFormat formato = NumberFormat.getIntegerInstance(Locale.forLanguageTag("es-CO"));

        return TARJETA_COSECHA.of(
                cosecha.getProducto(),
                cosecha.getDescripcion(),
                formato.format(cosecha.getPrecio()),
                formato.format(cosecha.getCantidad()));
    }
}
