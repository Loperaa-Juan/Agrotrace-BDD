package task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;

// Deja un checkbox de filtro marcado o desmarcado.
public class InteractuarFiltrosTask implements Task {
    private final Target label;
    private final Target checkbox;
    private final Boolean debeQuedarMarcado;

    public InteractuarFiltrosTask(Target label, Target checkbox, Boolean debeQuedarMarcado) {
        this.label = label;
        this.checkbox = checkbox;
        this.debeQuedarMarcado = debeQuedarMarcado;
    }

    // Hace clic en el label solo si el checkbox no está marcado
    public static Performable marcar(Target label, Target checkbox) {
        return Tasks.instrumented(InteractuarFiltrosTask.class, label, checkbox, Boolean.TRUE);
    }

    // Hace clic en el label solo si el checkbox está marcado
    public static Performable desmarcar(Target label, Target checkbox) {
        return Tasks.instrumented(InteractuarFiltrosTask.class, label, checkbox, Boolean.FALSE);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        boolean estaMarcado = checkbox.resolveFor(actor).isSelected();

        if (estaMarcado != debeQuedarMarcado) {
            actor.attemptsTo(Click.on(label));
        }
    }
}
