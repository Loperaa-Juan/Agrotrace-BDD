package interaction;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import utils.Textos;

// Escribe un valor en un campo del formulario. Si el valor viene vacío, limpia el campo.
// Uso: Escribir.elValor(usuario.getCorreo()).en(LoginUI.INPUT_EMAIL)
public class Escribir implements Interaction {
    private final String valor;
    private final Target campo;

    public Escribir(String valor, Target campo) {
        this.valor = valor;
        this.campo = campo;
    }

    public static ConValor elValor(String valor) {
        return new ConValor(valor);
    }

    // Para cantidades y precios: el número se escribe como texto
    public static ConValor elValor(Integer valor) {
        return new ConValor(valor == null ? null : String.valueOf(valor));
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (Textos.tieneValor(valor)) {
            actor.attemptsTo(Enter.theValue(valor).into(campo));
        } else {
            actor.attemptsTo(Clear.field(campo));
        }
    }

    // Guarda el valor mientras se indica el campo con en(...)
    public static class ConValor {
        private final String valor;

        private ConValor(String valor) {
            this.valor = valor;
        }

        public Escribir en(Target campo) {
            // Tasks.instrumented no acepta parámetros null: el texto vacío también hace que se limpie el campo
            return Tasks.instrumented(Escribir.class, valor == null ? "" : valor, campo);
        }
    }
}
