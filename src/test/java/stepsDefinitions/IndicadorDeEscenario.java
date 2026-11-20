package stepsDefinitions;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import net.serenitybdd.annotations.Managed;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// Muestra en la página un letrero con el feature y el escenario que se están ejecutando.
// Sirve para seguir el recorrido encadenado en el navegador y aparece en las capturas de Serenity.
public class IndicadorDeEscenario {

    @Managed
    WebDriver driver;

    private String feature;
    private String escenario;

    @Before
    public void recordarEscenario(Scenario scenario) {
        feature = tituloDelFeature(scenario.getUri());
        escenario = scenario.getName();
    }

    // Se vuelve a poner en cada paso porque varios pasos recargan la página y la recarga lo borra
    @BeforeStep
    @AfterStep
    public void mostrarLetrero() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "var letrero = document.getElementById('indicador-escenario') || document.createElement('div');" +
                    "letrero.id = 'indicador-escenario';" +
                    // pointer-events:none para que los clics lo atraviesen; abajo a la izquierda para no tapar el menú ni los botones
                    "letrero.style.cssText = 'position:fixed;bottom:12px;left:12px;z-index:99999;pointer-events:none;" +
                    "max-width:420px;padding:8px 12px;border-radius:8px;white-space:pre-line;" +
                    "background:rgba(17,24,39,.85);color:#fff;font:13px/1.4 sans-serif';" +
                    "letrero.textContent = arguments[0];" +
                    "document.body.appendChild(letrero);",
                    "Feature: " + feature + "\nEscenario: " + escenario);
        } catch (RuntimeException e) {
            // El letrero es solo visual: si la página está recargando, se pone en el siguiente paso
        }
    }

    // "classpath:features/02_Catalog.feature" -> "Publicación de cosechas en Agrotrace" (la línea "Feature:" del archivo)
    private String tituloDelFeature(URI uri) {
        try {
            List<String> lineas = leerLineas(uri);
            for (String linea : lineas) {
                if (linea.trim().startsWith("Feature:")) {
                    return linea.trim().substring("Feature:".length()).trim();
                }
            }
        } catch (Exception e) {
            // Si no se puede leer el archivo, se muestra su nombre
        }
        String ruta = uri.toString();
        return ruta.substring(ruta.lastIndexOf('/') + 1);
    }

    private List<String> leerLineas(URI uri) throws Exception {
        if ("classpath".equals(uri.getScheme())) {
            String recurso = uri.getSchemeSpecificPart().replaceFirst("^/", "");
            try (InputStream entrada = getClass().getClassLoader().getResourceAsStream(recurso);
                 BufferedReader lector = new BufferedReader(new InputStreamReader(entrada, StandardCharsets.UTF_8))) {
                return lector.lines().toList();
            }
        }
        return Files.readAllLines(Paths.get(uri), StandardCharsets.UTF_8);
    }
}
