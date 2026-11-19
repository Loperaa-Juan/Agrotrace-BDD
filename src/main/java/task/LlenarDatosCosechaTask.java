package task;

import models.DatosCosecha;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import userInterface.PublicarOfertaUI;

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
        escribir(actor, cosecha.getCantidad(), INPUT_CANTIDAD);
        escribir(actor, cosecha.getPrecio(), INPUT_PRECIO);
        cargarFoto(actor, cosecha.getFoto(), INPUT_FOTO);
        escribir(actor, cosecha.getDescripcion(), INPUT_DESCRIPCION);
        actor.attemptsTo(
            Click.on(PublicarOfertaUI.BTN_SIGUIENTE)
        );
        llenarFecha(actor, cosecha.getFechaCosecha(), INPUT_FECHA);
        actor.attemptsTo(
                Click.on(BTN_REVISAR_INFO),
                WaitUntil.the(INPUT_FECHA,isVisible()).forNoMoreThan(10).seconds()
        );

    }

    private boolean tieneValor(String valor) {
        return valor != null && !valor.isBlank();
    }

    private void seleccionarProducto(Actor actor, String tipo) {
        if(tieneValor(tipo)) {
            actor.attemptsTo(SelectFromOptions.byVisibleText(tipo).from(SELECT_PRODUCTO));
        }
    }

    private void escribir(Actor actor, String valor, Target campo) {
        if(tieneValor(valor)) {
            actor.attemptsTo(Enter.theValue(valor).into(campo));
        } else {
            actor.attemptsTo(Clear.field(campo));
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

    private void escribir(Actor actor, Integer valor, Target campo) {
        escribir(actor, valor == null ? null : String.valueOf(valor), campo);
    }
}
