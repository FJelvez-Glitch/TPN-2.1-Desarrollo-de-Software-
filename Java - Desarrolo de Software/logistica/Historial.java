package logistica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Historial {
    private final List<RegistroSeguimiento> movimientos = new ArrayList<>();

    public void agregarMovimiento(AccionSeguimiento accion, Sucursal sucursal) {
        validarQuePuedeRegistrar(accion);
        movimientos.add(new RegistroSeguimiento(accion, sucursal));
    }

    public void validarQuePuedeRegistrar(AccionSeguimiento accion) {
        if (accion == null) {
            throw new IllegalArgumentException("Un movimiento necesita una acción.");
        }
        if (!admite(accion)) {
            throw new IllegalStateException("Movimiento incoherente: no se puede registrar '"
                    + accion.getDescripcion() + "' " + describirContexto() + ".");
        }
    }

    public boolean estaVacio() {
        return movimientos.isEmpty();
    }

    public List<RegistroSeguimiento> obtenerHistorialCompleto() {
        return Collections.unmodifiableList(movimientos);
    }

    public List<Sucursal> obtenerSucursalesVisitadas() {
        Set<Sucursal> visitadas = new LinkedHashSet<>();
        for (RegistroSeguimiento reg : movimientos) {
            Sucursal sucursal = reg.getSucursal();
            if (sucursal != null) {
                visitadas.add(sucursal);
            }
        }
        return new ArrayList<>(visitadas);
    }

    public RegistroSeguimiento obtenerUltimoMovimiento() {
        if (estaVacio()) {
            throw new IllegalStateException("El envío todavía no registra movimientos.");
        }
        int indiceUltimo = movimientos.size() - 1;
        return movimientos.get(indiceUltimo);
    }

    private boolean admite(AccionSeguimiento accion) {
        if (estaVacio()) {
            return accion.puedeSerPrimera();
        }
        RegistroSeguimiento ultimo = obtenerUltimoMovimiento();
        AccionSeguimiento accionAnterior = ultimo.getAccion();
        return accion.puedeSeguirA(accionAnterior);
    }

    private String describirContexto() {
        if (estaVacio()) {
            return "como primer movimiento";
        }
        RegistroSeguimiento ultimo = obtenerUltimoMovimiento();
        AccionSeguimiento accionAnterior = ultimo.getAccion();
        return "luego de '" + accionAnterior.getDescripcion() + "'";
    }
}
