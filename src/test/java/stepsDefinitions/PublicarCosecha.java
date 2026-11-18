package stepsDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CredencialesLogin;
import models.DatosCosecha;
import models.DatosCosechas;
import models.UsuariosRegistrados;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.WebDriver;
import question.ElementoPresente;
import task.LlenarDatosCosechaTask;
import task.LlenarDatosLoginTask;
import userInterface.LoginUI;
import userInterface.PerfilUI;
import userInterface.PublicarOfertaUI;
import userInterface.RegistroUI;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class PublicarCosecha {
    private Actor testActor;

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

    @When("Publica una cosecha de {string} con datos validos y la imagen {string}")
    public void PublicaCosechaConCamposValidos(String producto, String imagen) {
        DatosCosecha cosecha = DatosCosechas.nueva(producto, imagen);

        testActor.attemptsTo(
                LlenarDatosCosechaTask.con(cosecha),
                Click.on(PublicarOfertaUI.BTN_PUBLICAR_COSECHA)
        );
    }

}
