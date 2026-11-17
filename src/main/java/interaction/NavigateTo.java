package interaction;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import userInterface.RegistroUI;

public class NavigateTo {

    // Abre la URL definida en @DefaultUrl de BaseUI
    public static Performable page() {
        return Task.where("{0} abre el cliente (Frontend) de Agrotrace",
                Open.browserOn().the(RegistroUI.class));
    }
}
