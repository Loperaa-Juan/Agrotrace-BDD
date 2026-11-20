package stepsDefinitions;
import exceptions.MensajeDeErrorNoMostradoException;
import exceptions.RedireccionIncorrectaException;
import exceptions.ValorDelFeatureNoValidoException;
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
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static org.hamcrest.CoreMatchers.equalTo;

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
    public void ingresaPaginaRegistro() throws InterruptedException {
        testActor.attemptsTo(NavigateTo.page());
    }

    @When("Intenta registrarse sin diligenciar los campos obligatorios")
    public void ingresarDatosAFormulario() {
        testActor.attemptsTo(
                Click.on(RegistroUI.BTN_CREAR_PERFIL),
                // Espera a que el formulario muestre los errores (máximo 10 s, sigue apenas aparezcan)
                WaitUntil.the(RegistroUI.MSG_NOMBRE_OBLIGATORIO, isPresent()).forNoMoreThan(10).seconds()
        );
    }

    @Then("Ve mensajes de campos obligatorios")
    public void validaCamposFaltantes() throws InterruptedException {
        testActor.should(
                seeThat(ElementoPresente.en(RegistroUI.MSG_NOMBRE_OBLIGATORIO), equalTo(true))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE),
                seeThat(ElementoPresente.en(RegistroUI.MSG_NUMERO_DOCUMENTO_OBLIGATORIO), equalTo(true))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE),
                seeThat(ElementoPresente.en(RegistroUI.MSG_CORREO_OBLIGATORIO), equalTo(true))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE),
                seeThat(ElementoPresente.en(RegistroUI.MSG_TELEFONO_OBLIGATORIO), equalTo(true))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE),
                seeThat(ElementoPresente.en(RegistroUI.MSG_CONTRASENA_OBLIGATORIA), equalTo(true))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE),
                seeThat(ElementoPresente.en(RegistroUI.MSG_DIRECCION_OBLIGATORIA), equalTo(true))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE),
                seeThat(ElementoPresente.en(RegistroUI.MSG_MUNICIPIO_OBLIGATORIO), equalTo(true))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE)
        );
    }

    private static final Map<String, Target> BOTON_POR_ROL = Map.of(
            "agricultor",    RegistroUI.BTN_REGISTRO_AGRICULTOR,
            "persona",       RegistroUI.BTN_REGISTRO_PERSONA,
            "negocio",       RegistroUI.BTN_REGISTRO_NEGOCIO
    );

    @When("Se registra como {string} con datos válidos")
    public void seRegistraComo(String tipo) {
        UsuarioRegistro usuario = DatosUsuarios.nuevo(tipo);
        // Se guarda para los tests siguientes del recorrido (correo / NIT repetido)
        UsuariosRegistrados.guardar(tipo, usuario);
        Target botonRol = botonDe(tipo);

        testActor.attemptsTo(
                Click.on(botonRol),
                LlenarDatosRegistroTask.con(usuario),
                Click.on(RegistroUI.BTN_CREAR_PERFIL)
        );
    }

    @Then("Es redirigido a su perfil")
    public void validaRedireccionAPerfil() {
        // Espera a que cargue el perfil (máximo 10 s, sigue apenas aparezca)
        testActor.should(
                eventually(seeThat(ElementoPresente.en(PerfilUI.CONTENEDOR_PERFIL), equalTo(true))
                        .orComplainWith(RedireccionIncorrectaException.class, RedireccionIncorrectaException.MENSAJE))
                        .waitingForNoLongerThan(10).seconds()
        );
    }

    @Then("Valida su rol de {string}")
    public void validaRol(String rol) {
        testActor.should(
                seeThat(TextoElemento.de(PerfilUI.TXT_ROL), equalTo(rol))
                        .orComplainWith(RedireccionIncorrectaException.class, RedireccionIncorrectaException.MENSAJE)
        );
    }

    @Then("Cierra sesión")
    public void cierraSesion() {
        testActor.attemptsTo(
                Click.on(PerfilUI.BTN_MENU),
                Click.on(PerfilUI.BTN_CERRAR_SESION)
        );
    }

    @Given("Cierra sesión y vuelve a la página de registro")
    public void cierraSesionYVuelveARegistro() {
        cierraSesion();
        testActor.attemptsTo(Click.on(RegistroUI.BTN_REGISTRARSE));
    }

    @When("Intenta registrarse con el correo del {string} recién registrado")
    public void intentaRegistrarseConEmailDuplicado(String tipo) {
        String emailUltimoUsuarioRegistrado = UsuariosRegistrados.obtener(tipo).getCorreo();
        UsuarioRegistro nuevoUsuarioConMismoEmail = DatosUsuarios.nuevoConCorreo(tipo, emailUltimoUsuarioRegistrado);

        testActor.attemptsTo(
                LlenarDatosRegistroTask.con(nuevoUsuarioConMismoEmail),
                Click.on(RegistroUI.BTN_CREAR_PERFIL)
        );
    }

    @Then("Valida el mensaje {string}")
    public void validaErrorEnCampoEmail(String error) {
        testActor.should(
                seeThat(ElementoPresente.en(RegistroUI.MSG_CORREO_OBLIGATORIO), equalTo(true))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE),
                seeThat(the(RegistroUI.MSG_CORREO_OBLIGATORIO), containsText(error))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE)
        );
    }

    // ESCENARIO: Registro exitoso de un negocio
    @Then("Ve la lista de cosechas disponibles en la plataforma")
    public void validaLaListaDeProductosDisponibles() {
        // Espera a que cargue el catálogo (máximo 10 s, sigue apenas aparezca)
        testActor.should(
                eventually(seeThat(ElementoPresente.en(CatalogoUI.LISTA_PRODUCTOS), equalTo(true))
                        .orComplainWith(RedireccionIncorrectaException.class, RedireccionIncorrectaException.MENSAJE))
                        .waitingForNoLongerThan(10).seconds()
        );
    }

    // ESCENARIO: Un negocio intenta registrarse con el NIT del negocio recién registrado
    @When("Intenta registrarse como {string} con el NIT del negocio recién registrado")
    public void intentaRegistrarseConNitDuplicado(String tipo) {
        String emailUltimoNegocioRegistrado = UsuariosRegistrados.obtener(tipo).getNumeroDocumento();
        UsuarioRegistro nuevoNegocioConMismoNit = DatosUsuarios.nuevoConDocumento(tipo, emailUltimoNegocioRegistrado);
        Target botonRol = botonDe(tipo);

        testActor.attemptsTo(
                Click.on(botonRol),
                LlenarDatosRegistroTask.con(nuevoNegocioConMismoNit),
                Click.on(RegistroUI.BTN_CREAR_PERFIL)
        );
    }

    @Then("Ve el mensaje {string}")
    public void validaErrorEnElCampoNit(String error) {
        testActor.should(
                seeThat(ElementoPresente.en(RegistroUI.MSG_NUMERO_DOCUMENTO_OBLIGATORIO), equalTo(true))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE),
                seeThat(the(RegistroUI.MSG_NUMERO_DOCUMENTO_OBLIGATORIO), containsText(error))
                        .orComplainWith(MensajeDeErrorNoMostradoException.class, MensajeDeErrorNoMostradoException.MENSAJE)
        );
    }

    // ESCENARIO: Un usuario intenta registrarse con un correo con formato inválido
    @When("Intenta registrarse como {string} con el correo {string}")
    public void intentaRegistrarseConCorreoInvalido(String tipo, String correo) {
        // Datos válidos excepto el correo, que llega desde el feature.
        // No se guarda en UsuariosRegistrados: este usuario no llega a registrarse.
        UsuarioRegistro usuarioConCorreoInvalido = DatosUsuarios.nuevoConCorreo(tipo, correo);

        testActor.attemptsTo(
                Click.on(botonDe(tipo)),
                LlenarDatosRegistroTask.con(usuarioConCorreoInvalido),
                Click.on(RegistroUI.BTN_CREAR_PERFIL)
        );
    }

    // Botón del tipo de registro; si el feature trae un rol que no existe, falla con un mensaje claro
    private Target botonDe(String tipo) {
        Target boton = BOTON_POR_ROL.get(tipo);
        if (boton == null) {
            throw new ValorDelFeatureNoValidoException("No hay registro para el rol '" + tipo
                    + "'. Usa \"agricultor\", \"persona\" o \"negocio\".");
        }
        return boton;
    }

}