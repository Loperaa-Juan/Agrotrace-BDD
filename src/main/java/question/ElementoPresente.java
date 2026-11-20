package question;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

// Úsala siempre con un matcher: seeThat(ElementoPresente.en(X), equalTo(true)).
// Sin matcher, Serenity 5.4.2 pierde el error original y orComplainWith termina en un NullPointerException.
public class ElementoPresente implements Question<Boolean> {

    private final Target target;

    private ElementoPresente(Target target) {
        this.target = target;
    }

    public static ElementoPresente en(Target target) {
        return new ElementoPresente(target);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return target.resolveFor(actor).isPresent();
    }
}
