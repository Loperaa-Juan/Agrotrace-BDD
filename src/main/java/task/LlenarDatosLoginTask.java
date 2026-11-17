package task;

import models.CredencialesLogin;
import models.UsuarioRegistro;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import userInterface.LoginUI;

public class LlenarDatosLoginTask implements Task {
    private final CredencialesLogin usuario;

    public LlenarDatosLoginTask(CredencialesLogin usuario) {
        this.usuario = usuario;
    }

    public static Performable con(CredencialesLogin usuario) {
        return Tasks.instrumented(LlenarDatosLoginTask.class, usuario);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        escribir(actor, usuario.getCorreo(), LoginUI.INPUT_EMAIL);
        escribir(actor, usuario.getContrasena(), LoginUI.INPUT_CONTRASENA);
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
}

