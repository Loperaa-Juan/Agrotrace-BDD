package interaction;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import net.serenitybdd.screenplay.targets.Target;

public class MoverDeslizador implements Interaction {
    private final Target deslizador;
    private final int valor;

    public MoverDeslizador(Target deslizador, int valor) {
        this.deslizador = deslizador;
        this.valor = valor;
    }

    public static MoverDeslizador hasta(Target deslizador, int valor) {
        return Tasks.instrumented(MoverDeslizador.class, deslizador, valor);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebElement slider = deslizador.resolveFor(actor);

        JavascriptExecutor js = (JavascriptExecutor)
                BrowseTheWeb.as(actor).getDriver();

        // React no se entera si se asigna .value directamente (guarda el último valor y cree que no cambió).
        // Con el setter nativo del navegador React sí detecta el cambio y llama a onChange.
        js.executeScript(
                "var setter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set;" +
                        "setter.call(arguments[0], arguments[1]);" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                slider,
                String.valueOf(valor)
        );
    }
}
