package stepsDefinitions;
import interaction.NavigateTo;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static org.hamcrest.CoreMatchers.equalTo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import models.DatosUsuarios;
import models.UsuarioRegistro;
import models.UsuariosRegistrados;
import question.ElementoPresente;
import question.TextoElemento;
import task.LlenarDatosRegistroTask;
import userInterface.CatalogoUI;
import userInterface.PerfilUI;
import userInterface.RegistroUI;

import net.serenitybdd.screenplay.targets.Target;
import java.util.Map;

public class RegistroUsuario {
    private Actor testActor;

    @Managed
    WebDriver driver;

    @Before
    public void setStage(){testActor = Actor.named("Usuario").can(BrowseTheWeb.with(driver));}

    @Given("Un nuevo usuario se encuentra en la página de registro")
    public void IngresaPaginaRegistro() throws InterruptedException {
        testActor.attemptsTo(NavigateTo.page());
    }

    @When("Intenta registrarse sin diligenciar los campos obligatorios")
    public void IngresarDatosAFormulario() {
        testActor.attemptsTo(
                Click.on(RegistroUI.BTN_CREAR_PERFIL),
                // Espera a que el formulario muestre los errores (máximo 10 s, sigue apenas aparezcan)
                WaitUntil.the(RegistroUI.MSG_NOMBRE_OBLIGATORIO, isPresent()).forNoMoreThan(10).seconds()
        );
    }

    @Then("Ve mensajes de campos obligatorios")
    public void ValidaCamposFaltantes() throws InterruptedException {
        testActor.should(
                seeThat(ElementoPresente.en(RegistroUI.MSG_NOMBRE_OBLIGATORIO)),
                seeThat(ElementoPresente.en(RegistroUI.MSG_NUMERO_DOCUMENTO_OBLIGATORIO)),
                seeThat(ElementoPresente.en(RegistroUI.MSG_CORREO_OBLIGATORIO)),
                seeThat(ElementoPresente.en(RegistroUI.MSG_TELEFONO_OBLIGATORIO)),
                seeThat(ElementoPresente.en(RegistroUI.MSG_CONTRASENA_OBLIGATORIA)),
                seeThat(ElementoPresente.en(RegistroUI.MSG_DIRECCION_OBLIGATORIA)),
                seeThat(ElementoPresente.en(RegistroUI.MSG_MUNICIPIO_OBLIGATORIO))
        );
    }

    private static final Map<String, Target> BOTON_POR_ROL = Map.of(
            "agricultor",    RegistroUI.BTN_REGISTRO_AGRICULTOR,
            "persona",       RegistroUI.BTN_REGISTRO_PERSONA,
            "negocio",       RegistroUI.BTN_REGISTRO_NEGOCIO
    );

    @When("Se registra como {string} con datos válidos")
    public void SeRegistraComo(String tipo) {
        UsuarioRegistro usuario = DatosUsuarios.nuevo(tipo);
        // Se guarda para los tests siguientes del recorrido (correo / NIT repetido)
        UsuariosRegistrados.guardar(tipo, usuario);
        Target botonRol = BOTON_POR_ROL.get(tipo);

        testActor.attemptsTo(
                Click.on(botonRol),
                LlenarDatosRegistroTask.con(usuario),
                Click.on(RegistroUI.BTN_CREAR_PERFIL)
        );
    }

    @Then("Es redirigido a su perfil")
    public void ValidaRedireccionAPerfil() {
        // Espera a que cargue el perfil (máximo 10 s, sigue apenas aparezca)
        testActor.attemptsTo(
                WaitUntil.the(PerfilUI.CONTENEDOR_PERFIL, isPresent()).forNoMoreThan(10).seconds()
        );
        testActor.should(
                seeThat(ElementoPresente.en(PerfilUI.CONTENEDOR_PERFIL))
        );
    }

    @Then("Valida su rol de {string}")
    public void ValidaRol(String rol) {
        testActor.should(
                seeThat(TextoElemento.de(PerfilUI.TXT_ROL), equalTo(rol))
        );
    }

    @Given("Cierra sesión y vuelve a la página de registro")
    public void CierraSesion() {
        testActor.attemptsTo(
                Click.on(PerfilUI.BTN_MENU),
                Click.on(PerfilUI.BTN_CERRAR_SESION),
                Click.on(RegistroUI.BTN_REGISTRARSE)
        );
    }

    @When("Intenta registrarse con el correo del {string} recién registrado")
    public void IntentaRegistrarseConEmailDuplicado(String tipo) {
        String EmailUltimoUsuarioRegistrado = UsuariosRegistrados.obtener(tipo).getCorreo();
        UsuarioRegistro NuevoUsuarioConMismoEmail = DatosUsuarios.nuevoConCorreo(tipo, EmailUltimoUsuarioRegistrado);

        testActor.attemptsTo(
                LlenarDatosRegistroTask.con(NuevoUsuarioConMismoEmail),
                Click.on(RegistroUI.BTN_CREAR_PERFIL)
        );
    }

    @Then("Valida el mensaje {string}")
    public void ValidaErrorEnCampoEmail(String error) {
        testActor.should(
                seeThat(ElementoPresente.en(RegistroUI.MSG_CORREO_OBLIGATORIO)),
                seeThat(the(RegistroUI.MSG_CORREO_OBLIGATORIO), containsText(error))
        );
    }

    // ESCENARIO: Registro exitoso de un negocio
    @Then("Ve la lista de cosechas disponibles en la plataforma")
    public void ValidaLaListaDeProductosDisponibles() {
        testActor.should(
                seeThat(ElementoPresente.en(CatalogoUI.LISTA_PRODUCTOS))
        );
    }

    // ESCENARIO: Un negocio intenta registrarse con el NIT del negocio recién registrado
    @When("Intenta registrarse como {string} con el NIT del negocio recién registrado")
    public void IntentaRegistrarseConNITDuplicado(String tipo) {
        String EmailUltimoNegocioRegistrado = UsuariosRegistrados.obtener(tipo).getNumeroDocumento();
        UsuarioRegistro NuevoNegocioConMismoNIT = DatosUsuarios.nuevoConDocumento(tipo, EmailUltimoNegocioRegistrado);
        Target botonRol = BOTON_POR_ROL.get(tipo);

        testActor.attemptsTo(
                Click.on(botonRol),
                LlenarDatosRegistroTask.con(NuevoNegocioConMismoNIT),
                Click.on(RegistroUI.BTN_CREAR_PERFIL)
        );
    }

    @Then("Ve el mensaje {string}")
    public void ValidaErrorEnElCampoNIT(String error) {
        testActor.should(
                seeThat(ElementoPresente.en(RegistroUI.MSG_NUMERO_DOCUMENTO_OBLIGATORIO)),
                seeThat(the(RegistroUI.MSG_NUMERO_DOCUMENTO_OBLIGATORIO), containsText(error))
        );
    }

    // ESCENARIO: Un usuario intenta registrarse con un correo con formato inválido
    @When("Intenta registrarse como {string} con el correo {string}")
    public void IntentaRegistrarseConCorreoInvalido(String tipo, String correo) {
        // Datos válidos excepto el correo, que llega desde el feature.
        // No se guarda en UsuariosRegistrados: este usuario no llega a registrarse.
        UsuarioRegistro UsuarioConCorreoInvalido = DatosUsuarios.nuevoConCorreo(tipo, correo);

        testActor.attemptsTo(
                Click.on(BOTON_POR_ROL.get(tipo)),
                LlenarDatosRegistroTask.con(UsuarioConCorreoInvalido),
                Click.on(RegistroUI.BTN_CREAR_PERFIL)
        );
    }

}