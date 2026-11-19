package stepsDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CosechasRegistradas;
import models.CredencialesLogin;
import models.DatosCosecha;
import models.DatosCosechas;
import models.UsuariosRegistrados;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import question.ElementoPresente;
import question.TextoElemento;
import task.LlenarDatosCosechaTask;
import task.LlenarDatosLoginTask;
import userInterface.CatalogoUI;
import userInterface.LoginUI;
import userInterface.PerfilUI;
import userInterface.PublicarOfertaUI;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;
import static org.hamcrest.Matchers.equalTo;

public class PublicarCosecha {
    private Actor testActor;

    // Cosecha que se está publicando en el escenario actual.
    // Se guarda en CosechasRegistradas solo cuando la app confirma que quedó publicada.
    private DatosCosecha cosechaEnPublicacion;

    // Calidad que la IA le asignó a esa cosecha; con ella se guarda en CosechasRegistradas
    private String calidadEnPublicacion;

    @Managed
    WebDriver driver;

    @Before
    public void setStage(){testActor = Actor.named("Usuario").can(BrowseTheWeb.with(driver));}

    @Given("El {string} registrado anteriormente inicia sesión")
    public void IniciaSesionComo(String rol) {
        // Extraemos el email y la contraseña del último agricultor que se registró
        String EmailUltimoAgricultorRegistrado = UsuariosRegistrados.obtener(rol).getCorreo();
        String ContrasenaUltimoAgricultorRegistrado = UsuariosRegistrados.obtener(rol).getContrasena();
        CredencialesLogin UsuarioALoguear = new CredencialesLogin(
                EmailUltimoAgricultorRegistrado,
                ContrasenaUltimoAgricultorRegistrado
        );

        testActor.attemptsTo(
                LlenarDatosLoginTask.con(UsuarioALoguear),
                Click.on(LoginUI.LOGIN_BUTTON)
        );
    }

    @When("Intenta publicar una cosecha sin los campos obligatorios")
    public void IntentaPublicarCosechaSinCampos() {
        testActor.attemptsTo(
                Click.on(PerfilUI.BTN_PUBLICAR_COSECHA),
                Click.on(PublicarOfertaUI.BTN_SIGUIENTE)
        );
    }

    @Then("Ve mensajes de error por los campos obligatorios faltantes")
    public void ValidaMensajesDeCamposFaltantes() {
        testActor.should(
                seeThat(ElementoPresente.en(PublicarOfertaUI.MSG_ERROR_PRODUCTO)),
                seeThat(ElementoPresente.en(PublicarOfertaUI.MSG_ERROR_CANTIDAD)),
                seeThat(ElementoPresente.en(PublicarOfertaUI.MSG_ERROR_PRECIO)),
                seeThat(ElementoPresente.en(PublicarOfertaUI.MSG_ERROR_FOTO))
        );
    }

    @When("Intenta continuar con cantidad {string} y precio {string}")
    public void IntentaContinuarConCantidadYPrecio(String cantidad, String precio) {
        testActor.attemptsTo(
                Enter.theValue(cantidad).into(PublicarOfertaUI.INPUT_CANTIDAD),
                Enter.theValue(precio).into(PublicarOfertaUI.INPUT_PRECIO),
                Click.on(PublicarOfertaUI.BTN_SIGUIENTE)
        );
    }

    // El mensaje de error trae un ícono que se lee como texto ("error"), por eso se valida con containsText
    @Then("Debería ver el error de cantidad {string}")
    public void ValidaErrorDeCantidad(String mensaje) {
        testActor.should(
                seeThat(the(PublicarOfertaUI.MSG_ERROR_CANTIDAD), containsText(mensaje))
        );
    }

    @And("Debería ver el error de precio {string}")
    public void ValidaErrorDePrecio(String mensaje) {
        testActor.should(
                seeThat(the(PublicarOfertaUI.MSG_ERROR_PRECIO), containsText(mensaje))
        );
    }

    @When("Publica una cosecha de {string} con datos validos y la imagen {string}")
    public void PublicaCosechaConCamposValidos(String producto, String imagen) {
        DatosCosecha cosecha = DatosCosechas.nueva(producto, imagen);
        cosechaEnPublicacion = cosecha;

        testActor.attemptsTo(
                LlenarDatosCosechaTask.con(cosecha),
                Click.on(PublicarOfertaUI.BTN_PUBLICAR_COSECHA)
        );
    }

    @Then("La cosecha debería ser clasificada con {string}")
    public void ValidaCalidadAsignadaDeCosecha(String calidad) {
        testActor.should(
                seeThat(TextoElemento.de(PublicarOfertaUI.TXT_CALIDAD_IA), equalTo(calidad))
        );
        calidadEnPublicacion = calidad;
    }

    @And("debería ver el mensaje de {string}")
    public void ValidaCosechaPublicada(String mensaje) {
        testActor.should(
                seeThat(TextoElemento.de(PublicarOfertaUI.MSG_COSECHA_PUBLICADA), equalTo(mensaje))
        );
        // La app confirmó la publicación: se guarda para validarla después en el catálogo
        CosechasRegistradas.guardar(calidadEnPublicacion, cosechaEnPublicacion);

        testActor.attemptsTo(
                Click.on(PublicarOfertaUI.BTN_VOLVER_PERFIL)
        );
    }

    @Given("El agricultor se encuentra en el formulario de publicación")
    public void IngresaAlFormularioDePublicacion() {
        testActor.attemptsTo(
                Click.on(PerfilUI.BTN_PUBLICAR_COSECHA)
        );
    }

    @Then("Se debe mostar el mensaje de {string}")
    public void ValidaAlertaDeCosechaRechazada(String mensaje) {
        testActor.should(
                seeThat(TextoElemento.de(PublicarOfertaUI.MSG_ALERTA_ERROR), equalTo(mensaje))
        );
    }

    @And("El botón para publicar la cosecha debe estar desactivado")
    public void ValidaBotonPublicarDesactivado() {
        testActor.should(
                seeThat(the(PublicarOfertaUI.BTN_PUBLICAR_COSECHA), isNotEnabled())
        );
    }

    @Given("El agricultor vuelve a la primera vista del formulario de publicación")
    public void VuelveALaPrimeraVistaDelFormulario() {
        testActor.attemptsTo(
                Click.on(PublicarOfertaUI.BTN_VOLVER_REVISION),
                Click.on(PublicarOfertaUI.BTN_VOLVER_DATOS)
        );
    }

    @When("Intenta publicar una cosecha de {string} con datos validos y la imagen {string}")
    public void IntentaPublicarCosechaConCamposValidos(String producto, String imagen) {
        // Llena el formulario sin publicar: si la imagen no tiene fruta, la app se queda en el paso 2
        DatosCosecha cosecha = DatosCosechas.nueva(producto, imagen);

        testActor.attemptsTo(
                LlenarDatosCosechaTask.con(cosecha)
        );
    }

    @When("Intenta abrir el formulario de publicación")
    public void IntentaAbrirFormularioDePublicacion() {
        // Abre /publicar directamente por URL, sin pasar por el menú
        testActor.attemptsTo(
                Open.browserOn().the(PublicarOfertaUI.class)
        );
    }

    @Then("Es redirigido al inicio de sesión")
    public void ValidaRedireccionAlInicioDeSesion() {
        // La app redirige /publicar al login cuando no hay sesión (máximo 10 s, sigue apenas aparezca)
        testActor.attemptsTo(
                WaitUntil.the(LoginUI.INPUT_EMAIL, isVisible()).forNoMoreThan(10).seconds()
        );
        testActor.should(
                seeThat(ElementoPresente.en(LoginUI.INPUT_EMAIL))
        );
    }

    @Then("debería ver las cosechas publicadas por el agricultor")
    public void ValidaCosechasPublicadasEnCatalogo() {
        // El catálogo carga las cosechas después de abrir la página (máximo 10 s, sigue apenas aparezcan)
        testActor.attemptsTo(
                WaitUntil.the(CatalogoUI.LISTA_PRODUCTOS, isVisible()).forNoMoreThan(10).seconds()
        );

        for (DatosCosecha cosecha : CosechasRegistradas.obtenerTodas()) {
            testActor.should(
                    seeThat(ElementoPresente.en(CatalogoUI.tarjetaDe(cosecha)))
            );
        }
    }

    @When("Despliega el menú de opciones del usuario")
    public void DespliegaMenuDeUsuario() {
        testActor.attemptsTo(
                Click.on(PerfilUI.BTN_MENU)
        );
    }

    @Then("No debe ver la opción de publicar cosecha")
    public void noSeDebeRenderizar() {
        testActor.should(
                seeThat(the(PerfilUI.BTN_PUBLICAR_COSECHA), isNotPresent())
        );
    }
}
