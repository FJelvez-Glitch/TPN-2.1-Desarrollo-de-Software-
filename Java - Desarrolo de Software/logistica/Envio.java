package logistica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Envio {
    private static final int MAX_PAQUETES = 3;

    private final List<Paquete> paquetes;
    private final String destino;
    private final Historial historial = new Historial();

    public Envio(String destino) {
        this.destino = destino;
        this.paquetes = new ArrayList<>();
    }

    public void agregarPaquete(Paquete paquete) {
        if (paquete == null) {
            throw new IllegalArgumentException("No se puede agregar un paquete inexistente.");
        }
        if (!todaviaNoSalio()) {
            throw new IllegalStateException("No se pueden agregar paquetes a un envío que ya inició su recorrido.");
        }
        if (!hayLugar()) {
            throw new IllegalStateException("Un envío no puede transportar más de " + MAX_PAQUETES + " paquetes.");
        }
        this.paquetes.add(paquete);
    }

    public void recibirEnSucursal(Sucursal sucursal) {
        validarSucursal(sucursal);
        validarQueTienePaquetes("No se puede procesar un envío sin paquetes.");
        historial.validarQuePuedeRegistrar(AccionSeguimiento.envio_recibido);
        for (Paquete p : paquetes) {
            p.preparar();
        }
        historial.agregarMovimiento(AccionSeguimiento.envio_recibido, sucursal);
    }

    public void despacharDesdeSucursal(Sucursal sucursal) {
        validarSucursal(sucursal);
        validarQueTienePaquetes("No se puede despachar un envío sin paquetes.");
        historial.validarQuePuedeRegistrar(AccionSeguimiento.envio_despachado);
        for (Paquete p : paquetes) {
            p.despachar();
        }
        historial.agregarMovimiento(AccionSeguimiento.envio_despachado, sucursal);
    }

    public void finalizarRecorrido() {
        validarQueTienePaquetes("No se puede entregar un envío sin paquetes.");
        historial.validarQuePuedeRegistrar(AccionSeguimiento.envio_entregado);
        for (Paquete p : paquetes) {
            p.entregar();
        }
        historial.agregarMovimiento(AccionSeguimiento.envio_entregado, null);
    }

    // Consultas del historial: devuelven datos, no imprimen.

    public List<RegistroSeguimiento> obtenerHistorialCompleto() {
        return historial.obtenerHistorialCompleto();
    }

    public List<Sucursal> obtenerSucursalesVisitadas() {
        return historial.obtenerSucursalesVisitadas();
    }

    public RegistroSeguimiento obtenerUltimoMovimiento() {
        return historial.obtenerUltimoMovimiento();
    }

    public boolean tienePaquetes() {
        return !paquetes.isEmpty();
    }

    // Reglas de negocio con nombre propio.

    private boolean hayLugar() {
        return paquetes.size() < MAX_PAQUETES;
    }

    // "Todavía no salió" = todavía no ingresó a ninguna sucursal: sin movimientos registrados.
    private boolean todaviaNoSalio() {
        return historial.estaVacio();
    }

    private void validarQueTienePaquetes(String mensajeDeError) {
        if (!tienePaquetes()) {
            throw new IllegalStateException(mensajeDeError);
        }
    }

    private void validarSucursal(Sucursal sucursal) {
        if (sucursal == null) {
            throw new IllegalArgumentException("La sucursal indicada no existe.");
        }
    }

    protected double calcularPesoTotal() {
        double pesoTotal = 0;
        for (Paquete p : paquetes) {
            pesoTotal += p.getPeso();
        }
        return pesoTotal;
    }

    public abstract double calcularCosto();

    public List<Paquete> getPaquetes() {
        return Collections.unmodifiableList(paquetes);
    }

    public String getDestino() { return destino; }
}
