package stepsDefinitions;

import io.cucumber.java.AfterStep;
import utils.Pausas;

// Pausa después de cada paso del feature para que se alcance a ver su resultado en el navegador.
public class PausaEntrePasos {

    // Los @AfterStep corren de mayor a menor order. Con order = 0 la pausa va de última
    @AfterStep(order = 0)
    public void pausar() {
        Pausas.despuesDePaso();
    }
}
