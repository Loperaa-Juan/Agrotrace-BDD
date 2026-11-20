package task;

import interaction.Escribir;
import models.CredencialesLogin;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
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
        actor.attemptsTo(
                Escribir.elValor(usuario.getCorreo()).en(LoginUI.INPUT_EMAIL),
                Escribir.elValor(usuario.getContrasena()).en(LoginUI.INPUT_CONTRASENA)
        );
    }
}
