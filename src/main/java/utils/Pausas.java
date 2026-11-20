package utils;

import net.thucydides.model.environment.SystemEnvironmentVariables;

// Pausas cortas para que la ejecución se parezca a la de una persona.
public class Pausas {

    private Pausas() {
    }

    // Después de cada paso del feature (Given / When / Then)
    public static void despuesDePaso() {
        esperar("agrotrace.pausa.paso");
    }

    // Después de llenar cada campo de un formulario
    public static void despuesDeCampo() {
        esperar("agrotrace.pausa.campo");
    }

    private static void esperar(String propiedad) {
        // Si la propiedad no está en serenity.conf, no se pausa
        int milisegundos = SystemEnvironmentVariables.currentEnvironmentVariables()
                .getPropertyAsInteger(propiedad, 0);
        if (milisegundos <= 0) {
            return;
        }
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
