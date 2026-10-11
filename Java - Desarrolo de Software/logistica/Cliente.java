package logistica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {
    private final String nombre;
    private final List<Envio> envios;

    public Cliente(String id, String nombre) {
        this.nombre = nombre;
        this.envios = new ArrayList<>();
    }

    public void agregarEnvio(Envio envio) {
        if (envio == null) {
            throw new IllegalArgumentException("No se puede registrar un envío inexistente.");
        }
        if (!envio.tienePaquetes()) {
            throw new IllegalStateException("No se puede registrar un envío sin paquetes.");
        }
        if (!envios.contains(envio)) {
            this.envios.add(envio);
        }
    }

    public List<Envio> getEnvios() {
        return Collections.unmodifiableList(envios);
    }

    public String getNombre() { return nombre; }
}
