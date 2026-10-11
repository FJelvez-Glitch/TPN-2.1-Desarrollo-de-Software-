package logistica;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum EstadoPaquete {
    recibido,
    en_preparacion,
    en_viaje,
    salio_a_distribucion,
    entregado;

    // Cada estado conoce a qué estados puede avanzar.
    private static final Map<EstadoPaquete, Set<EstadoPaquete>> TRANSICIONES = new EnumMap<>(EstadoPaquete.class);

    static {
        TRANSICIONES.put(recibido, EnumSet.of(en_preparacion, en_viaje));
        TRANSICIONES.put(en_preparacion, EnumSet.of(en_viaje));
        TRANSICIONES.put(en_viaje, EnumSet.of(en_preparacion, entregado));
        TRANSICIONES.put(salio_a_distribucion, EnumSet.noneOf(EstadoPaquete.class));
        TRANSICIONES.put(entregado, EnumSet.noneOf(EstadoPaquete.class));
    }

    public boolean puedePasarA(EstadoPaquete nuevoEstado) {
        return TRANSICIONES.get(this).contains(nuevoEstado);
    }
}
