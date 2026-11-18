package models;

import java.time.LocalDate;

// Datos del formulario de publicación de una cosecha.
public class DatosCosecha {

    private final String producto;
    private final int cantidad;
    private final int precio;
    private final String foto;
    private final String descripcion;
    private final LocalDate fechaCosecha;

    public DatosCosecha(String producto, int cantidad, int precio,
                        String foto, String descripcion, LocalDate fechaCosecha) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.foto = foto;
        this.descripcion = descripcion;
        this.fechaCosecha = fechaCosecha;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public String getFoto() {
        return foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaCosecha() {
        return fechaCosecha;
    }
}
