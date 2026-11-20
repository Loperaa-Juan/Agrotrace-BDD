package task;

import interaction.Escribir;
import models.DatosCosecha;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import userInterface.PublicarOfertaUI;
import utils.Textos;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static userInterface.PublicarOfertaUI.*;

public class LlenarDatosCosechaTask implements Task {
    private final DatosCosecha cosecha;

    public LlenarDatosCosechaTask(DatosCosecha cosecha) {
        this.cosecha = cosecha;
    }

    public static Performable con(DatosCosecha cosecha) {
        return Tasks.instrumented(LlenarDatosCosechaTask.class, cosecha);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        seleccionarProducto(actor, cosecha.getProducto());
        actor.attemptsTo(
                Escribir.elValor(cosecha.getCantidad()).en(INPUT_CANTIDAD),
                Escribir.elValor(cosecha.getPrecio()).en(INPUT_PRECIO)
        );
        cargarFoto(actor, cosecha.getFoto(), INPUT_FOTO);
        actor.attemptsTo(
                Escribir.elValor(cosecha.getDescripcion()).en(INPUT_DESCRIPCION),
                Click.on(PublicarOfertaUI.BTN_SIGUIENTE)
        );
        llenarFecha(actor, cosecha.getFechaCosecha(), INPUT_FECHA);
        // La IA tarda en clasificar la foto (el botón muestra "Revisando…").
        // Espera hasta 60 s a que termine; sigue apenas aparece la calidad o la alerta de error.
        actor.attemptsTo(
                Click.on(BTN_REVISAR_INFO),
                WaitUntil.the(RESULTADO_REVISION_IA, isVisible()).forNoMoreThan(60).seconds()
        );

    }

    private void seleccionarProducto(Actor actor, String tipo) {
        if(Textos.tieneValor(tipo)) {
            actor.attemptsTo(SelectFromOptions.byVisibleText(tipo).from(SELECT_PRODUCTO));
        }
    }

    private void cargarFoto(Actor actor, String ruta, Target campo) {
        Path rutaFoto = Paths.get("src/test/resources/images", ruta).toAbsolutePath();
        actor.attemptsTo(Upload.theFile(rutaFoto).to(campo));
    }

    private void llenarFecha(Actor actor, LocalDate fecha, Target campo) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMMyyyy");

        actor.attemptsTo(Enter.theValue(fecha.format(formato)).into(campo));
    }
}
