package task;

import interaction.Escribir;
import models.UsuarioRegistro;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import utils.Textos;

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
        actor.attemptsTo(Escribir.elValor(usuario.getNombreCompleto()).en(INPUT_NOMBRE));
        seleccionarTipoDocumento(actor, usuario.getTipoDocumento());
        actor.attemptsTo(
                Escribir.elValor(usuario.getNumeroDocumento()).en(INPUT_NUMERO_DOCUMENTO),
                Escribir.elValor(usuario.getCorreo()).en(INPUT_EMAIL),
                Escribir.elValor(usuario.getTelefono()).en(INPUT_TELEFONO),
                Escribir.elValor(usuario.getContrasena()).en(INPUT_CONTRASENA),
                Escribir.elValor(usuario.getDireccion()).en(INPUT_DIRECCION),
                Escribir.elValor(usuario.getMunicipio()).en(INPUT_MUNICIPIO)
        );
    }

    private void seleccionarTipoDocumento(Actor actor, String tipo) {
        if(Textos.tieneValor(tipo)) {
            actor.attemptsTo(SelectFromOptions.byValue(tipo).from(SELECT_TIPO_DOCUMENTO));
        }
    }
}
