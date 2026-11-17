package question;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

// Devuelve el texto visible de un elemento, sin espacios al inicio ni al final.
public class TextoElemento implements Question<String> {

    private final Target target;

    private TextoElemento(Target target) {
        this.target = target;
    }

    public static TextoElemento de(Target target) {
        return new TextoElemento(target);
    }

    @Override
    public String answeredBy(Actor actor) {
        return target.resolveFor(actor).getText().trim();
    }
}
