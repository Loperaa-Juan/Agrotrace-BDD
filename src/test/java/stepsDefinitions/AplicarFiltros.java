package stepsDefinitions;

import interaction.MoverDeslizador;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CosechasRegistradas;
import models.DatosCosecha;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import question.ElementoPresente;
import task.InteractuarFiltrosTask;
import userInterface.CatalogoUI;
import userInterface.FiltrosUI;
import userInterface.PerfilUI;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class AplicarFiltros {
    private Actor testActor;

    private static final Map<String, String> CALIDAD_POR_GRADO = Map.of(
            "A", "Calidad Buena",
            "B", "Calidad Regular"
    );

    // Cosecha que se buscó por nombre en el escenario actual; el Then valida que aparezca en los resultados
    private DatosCosecha cosechaBuscada;

    @Managed
    WebDriver driver;

    @Before
    public void setStage(){testActor = Actor.named("Usuario").can(BrowseTheWeb.with(driver));}

    @Given("El negocio entra a la página de busqueda por filtros")
    public void ingresaALaPaginaDeFiltros() {
        testActor.attemptsTo(
                Click.on(PerfilUI.BTN_FILTROS_BUSQUEDA)
        );
    }

    @When("filtra las cosechas por {string}")
    public void filtraPorCalidad(String calidad) {
        // Deja marcada solo la calidad pedida: marca su checkbox y desmarca el de la otra calidad
        switch (calidad) {
            case "calidad A" -> testActor.attemptsTo(
                    InteractuarFiltrosTask.marcar(FiltrosUI.CHECKBOX_CALIDAD_A, FiltrosUI.ESTADO_CALIDAD_A),
                    InteractuarFiltrosTask.desmarcar(FiltrosUI.CHECKBOX_CALIDAD_B, FiltrosUI.ESTADO_CALIDAD_B)
            );
            case "calidad B" -> testActor.attemptsTo(
                    InteractuarFiltrosTask.marcar(FiltrosUI.CHECKBOX_CALIDAD_B, FiltrosUI.ESTADO_CALIDAD_B),
                    InteractuarFiltrosTask.desmarcar(FiltrosUI.CHECKBOX_CALIDAD_A, FiltrosUI.ESTADO_CALIDAD_A)
            );
            default -> throw new IllegalArgumentException("No hay filtro para '" + calidad
                    + "'. Usa \"calidad A\" o \"calidad B\".");
        }
    }

    @Then("debería ver solo cosechas de calidad {string}")
    public void validaSoloCosechasDeCalidad(String grado) {
        String calidad = calidadDe(grado);
        DatosCosecha cosechaEsperada = CosechasRegistradas.obtener(calidad);

        // Espera a que se pinte la cosecha publicada con esa calidad (máximo 10 s, sigue apenas aparezca)
        testActor.attemptsTo(
                WaitUntil.the(CatalogoUI.tarjetaDe(cosechaEsperada), isVisible()).forNoMoreThan(10).seconds()
        );
        // Ninguna de las tarjetas que se ven puede tener otra calidad
        testActor.should(
                seeThat(ElementoPresente.en(CatalogoUI.TARJETA_OTRA_CALIDAD.of(calidad)), equalTo(false))
        );
    }

    @And("no debería ver la cosecha de calidad {string}")
    public void validaQueNoApareceCosechaDeCalidad(String grado) {
        DatosCosecha cosechaExcluida = CosechasRegistradas.obtener(calidadDe(grado));

        testActor.should(
                seeThat(ElementoPresente.en(CatalogoUI.tarjetaDe(cosechaExcluida)), equalTo(false))
        );
    }

    @Given("El negocio limpia los filtros de calidad")
    public void limpiaFiltrosDeCalidad() {
        // Deja marcadas todas las calidades (como al abrir la página) para que el filtro no oculte resultados
        testActor.attemptsTo(
                InteractuarFiltrosTask.marcar(FiltrosUI.CHECKBOX_CALIDAD_A, FiltrosUI.ESTADO_CALIDAD_A),
                InteractuarFiltrosTask.marcar(FiltrosUI.CHECKBOX_CALIDAD_B, FiltrosUI.ESTADO_CALIDAD_B)
        );
    }

    @When("busca el último producto publicado por el agricultor con {string}")
    public void buscaUltimoProductoConCalidad(String calidad) {

        cosechaBuscada = CosechasRegistradas.obtener(calidadDe(calidad.replace("calidad ", "")));
        String ultimoProductoPublicado = cosechaBuscada.getProducto();

        testActor.attemptsTo(
                Enter.theValue(ultimoProductoPublicado).into(FiltrosUI.INPUT_BUSCAR_PRODUCTO),
                Click.on(FiltrosUI.BTN_BUSCAR)
        );
    }

    @Then("debería ver la cosecha de ese producto en los resultados")
    public void validaExistenciaDelProducto() {
        // Espera a que la búsqueda pinte la tarjeta de la cosecha buscada (máximo 10 s, sigue apenas aparezca)
        testActor.attemptsTo(
                WaitUntil.the(CatalogoUI.tarjetaDe(cosechaBuscada), isVisible()).forNoMoreThan(10).seconds()
        );
        testActor.should(
                seeThat(ElementoPresente.en(CatalogoUI.tarjetaDe(cosechaBuscada)))
        );
    }

    @When("busca el producto {string}")
    public void buscaProductoPorNombre(String producto) {
        testActor.attemptsTo(
                Enter.theValue(producto).into(FiltrosUI.INPUT_BUSCAR_PRODUCTO),
                Click.on(FiltrosUI.BTN_BUSCAR)
        );
    }

    @Then("debería ver el mensaje {string}")
    public void validaMensajeError(String error) {
        testActor.should(
                seeThat(the(FiltrosUI.TXT_RESULTADO_FILTRO), containsText(error))
        );
    }

    @Given("el negocio limpia la búsqueda anterior")
    public void limpiaBusquedaAnterior() {
        // La app no deja buscar con el input vacío (el botón Enviar se desactiva),
        // así que se recarga la página de filtros: vuelve sin búsqueda, con A y B marcados y el precio por defecto.
        // La sesión se mantiene porque la app la guarda en localStorage.
        testActor.attemptsTo(
                Open.browserOn().the(FiltrosUI.class)
        );
    }

    @When("filtra las cosechas con precio hasta {string} pesos")
    public void ajustaPrecioHasta(String limite) {
        int precioLimite = Integer.parseInt(limite);

        testActor.attemptsTo(
                MoverDeslizador.hasta(
                        FiltrosUI.SLIDER_LIMITE_PRECIO,
                        precioLimite
                )
        );

    }

    @Then("debería ver solo cosechas con precio hasta {string} pesos")
    public void validaPreciosHastaElLimite(String limite) {
        double precioLimite = Double.parseDouble(limite);

        // Espera a que la página cargue las cosechas (máximo 10 s): con la lista vacía no habría precios que revisar
        testActor.attemptsTo(
                WaitUntil.the(FiltrosUI.TXT_COSECHAS_CARGADAS, isVisible()).forNoMoreThan(10).seconds()
        );

        // Todas las tarjetas que se ven deben tener un precio menor o igual al límite
        Question<List<Double>> preciosVisibles = actor -> CatalogoUI.PRECIOS_TARJETAS.resolveAllFor(actor).stream()
                .map(precio -> precioDe(precio.getText()))
                .toList();

        testActor.should(
                seeThat("los precios de las cosechas visibles", preciosVisibles, everyItem(lessThanOrEqualTo(precioLimite)))
        );
    }

    @Then("debería ver las cosechas publicadas en los resultados")
    public void validaQueAparecenLasCosechasPublicadas() {
        // Espera la tarjeta de cada cosecha publicada en esta ejecución (máximo 10 s, sigue apenas aparezca)
        for (DatosCosecha cosecha : CosechasRegistradas.obtenerTodas()) {
            testActor.attemptsTo(
                    WaitUntil.the(CatalogoUI.tarjetaDe(cosecha), isVisible()).forNoMoreThan(10).seconds()
            );
            testActor.should(
                    seeThat(ElementoPresente.en(CatalogoUI.tarjetaDe(cosecha)))
            );
        }
    }

    @And("no debería ver las cosechas publicadas en los resultados")
    public void validaQueNoAparecenLasCosechasPublicadas() {
        // Las cosechas publicadas en esta ejecución cuestan 5000: con un límite menor el filtro las debe ocultar.
        // Se usa después de "debería ver solo cosechas con precio hasta…", que ya esperó a que cargaran las cosechas.
        for (DatosCosecha cosecha : CosechasRegistradas.obtenerTodas()) {
            testActor.should(
                    seeThat(ElementoPresente.en(CatalogoUI.tarjetaDe(cosecha)), equalTo(false))
            );
        }
    }

    // "$ 5.000 COP / unidad" -> 5000.0 (formato es-CO: punto de miles, coma decimal)
    private double precioDe(String textoPrecio) {
        String numero = textoPrecio.substring(textoPrecio.indexOf('$') + 1, textoPrecio.indexOf("COP")).trim();
        return Double.parseDouble(numero.replace(".", "").replace(",", "."));
    }

    // El filtro habla de grados ("A", "B") y la tarjeta muestra la calidad ("Calidad Buena", "Calidad Regular")
    private String calidadDe(String grado) {
        String calidad = CALIDAD_POR_GRADO.get(grado);
        if (calidad == null) {
            throw new IllegalArgumentException("No hay calidad para el grado '" + grado + "'. Usa \"A\" o \"B\".");
        }
        return calidad;
    }
}
