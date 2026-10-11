package logistica;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    private final String nombre;
    private final List<Envio> enviosActivos;

    public Sucursal(String nombre) {
        this.nombre = nombre;
        this.enviosActivos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void recibirEnvio(Envio envio) {
        validarEnvio(envio);
        if (tieneRegistrado(envio)) {
            throw new IllegalStateException("El envío ya está registrado en " + nombre);
        }
        envio.recibirEnSucursal(this);
        enviosActivos.add(envio);
    }

    public void despacharEnvio(Envio envio) {
        validarEnvio(envio);
        if (!tieneRegistrado(envio)) {
            throw new IllegalStateException("El envío no se encuentra en " + nombre);
        }
        envio.despacharDesdeSucursal(this);
        enviosActivos.remove(envio);
    }

    private boolean tieneRegistrado(Envio envio) {
        return enviosActivos.contains(envio);
    }

    private void validarEnvio(Envio envio) {
        if (envio == null) {
            throw new IllegalArgumentException("El envío indicado no existe.");
        }
    }
}
