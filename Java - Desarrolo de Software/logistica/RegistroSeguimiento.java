package logistica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroSeguimiento {
    private final LocalDateTime fechaHora;
    private final AccionSeguimiento accion;
    private final Sucursal sucursal;

    public RegistroSeguimiento(AccionSeguimiento accion, Sucursal sucursal) {
        if (accion == null) {
            throw new IllegalArgumentException("Un movimiento necesita una acción.");
        }
        this.fechaHora = LocalDateTime.now();
        this.accion = accion;
        this.sucursal = sucursal;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public AccionSeguimiento getAccion() {
        return accion;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public String getDetalle() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String nombreSucursal = (sucursal != null) ? sucursal.getNombre() : "Destino/Cliente";
        return "[" + fechaHora.format(formatter) + "] " + accion.getDescripcion() + " en: " + nombreSucursal;
    }
}
