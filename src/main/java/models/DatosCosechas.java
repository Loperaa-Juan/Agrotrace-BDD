package models;

import java.time.LocalDate;

// Crea cosechas de prueba con datos válidos para el formulario de publicación.

public class DatosCosechas {


    public static DatosCosecha nueva(String producto, String foto) {
        return new DatosCosecha(
                producto,
                100,
                5000,
                foto,
                "Cosecha de prueba creada por la automatización",
                LocalDate.now());
    }
}
