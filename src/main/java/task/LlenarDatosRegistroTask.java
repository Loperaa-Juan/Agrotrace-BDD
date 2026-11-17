package task;

import models.UsuarioRegistro;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.targets.Target;

import static userInterface.RegistroUI.*;

public class LlenarDatosRegistroTask implements Task {
    private final UsuarioRegistro usuario;

    public LlenarDatosRegistroTask(UsuarioRegistro usuario) {
        this.usuario = usuario;
    }

    public static Performable con(UsuarioRegistro usuario) {
        return Tasks.instrumented(LlenarDatosRegistroTask.class, usuario);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        escribir(actor, usuario.getNombreCompleto(), INPUT_NOMBRE);
        seleccionarTipoDocumento(actor, usuario.getTipoDocumento());
        escribir(actor, usuario.getNumeroDocumento(), INPUT_NUMERO_DOCUMENTO);
        escribir(actor, usuario.getCorreo(), INPUT_EMAIL);
        escribir(actor, usuario.getTelefono(), INPUT_TELEFONO);
        escribir(actor, usuario.getContrasena(), INPUT_CONTRASENA);
        escribir(actor, usuario.getDireccion(), INPUT_DIRECCION);
        escribir(actor, usuario.getMunicipio(), INPUT_MUNICIPIO);

    }

    private boolean tieneValor(String valor) {
        return valor != null && !valor.isBlank();
    }

    private void escribir(Actor actor, String valor, Target campo) {
        if(tieneValor(valor)) {
            actor.attemptsTo(Enter.theValue(valor).into(campo));
        } else {
            actor.attemptsTo(Clear.field(campo));
        }
    }

    private void seleccionarTipoDocumento(Actor actor, String tipo) {
        if(tieneValor(tipo)) {
            actor.attemptsTo(SelectFromOptions.byValue(tipo).from(SELECT_TIPO_DOCUMENTO));
        }
    }
}
