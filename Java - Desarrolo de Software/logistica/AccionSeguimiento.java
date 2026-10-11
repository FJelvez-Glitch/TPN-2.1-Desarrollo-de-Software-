package logistica;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum AccionSeguimiento {
    envio_recibido("Envío recibido"),
    envio_despachado("Envío despachado"),
    envio_entregado("Envío entregado al cliente");

    // Para cada acción, cuáles son las acciones que pueden haberla precedido.
    private static final Map<AccionSeguimiento, Set<AccionSeguimiento>> ANTERIORES_VALIDAS = new EnumMap<>(AccionSeguimiento.class);

    static {
        ANTERIORES_VALIDAS.put(envio_recibido, EnumSet.of(envio_despachado));
        ANTERIORES_VALIDAS.put(envio_despachado, EnumSet.of(envio_recibido));
        ANTERIORES_VALIDAS.put(envio_entregado, EnumSet.of(envio_despachado));
    }

    private final String descripcion;

    AccionSeguimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean puedeSerPrimera() {
        return this == envio_recibido;
    }

    public boolean puedeSeguirA(AccionSeguimiento anterior) {
        return ANTERIORES_VALIDAS.get(this).contains(anterior);
    }
}
