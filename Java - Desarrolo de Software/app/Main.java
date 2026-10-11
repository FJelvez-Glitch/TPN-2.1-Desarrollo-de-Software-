package app;

import logistica.*;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("CREACIÓN DE CLIENTE Y SUCURSALES");
            Cliente cliente = new Cliente("CLI-001", "Carlos Gómez");

            Sucursal viedma = new Sucursal("Sucursal Viedma");
            Sucursal bahia = new Sucursal("Sucursal Bahía Blanca");
            Sucursal bsas = new Sucursal("Sucursal Buenos Aires");

            System.out.println("CREACIÓN DE PAQUETES Y ENVÍO");
            Paquete paquete1 = new Paquete("PH-001", "Paquete 1", 2.5);
            Paquete paquete2 = new Paquete("PH-002", "Paquete 2", 1.0);

            Envio envio = new EnvioEstandar("Buenos Aires");
            envio.agregarPaquete(paquete1);
            envio.agregarPaquete(paquete2);
            cliente.agregarEnvio(envio);

            System.out.println("Cliente registrado: " + cliente.getNombre());
            System.out.println("Envíos del cliente: " + cliente.getEnvios().size());

            System.out.println("\nRECORRIDO POR SUCURSALES");

            viedma.recibirEnvio(envio);
            viedma.despacharEnvio(envio);

            bahia.recibirEnvio(envio);
            bahia.despacharEnvio(envio);

            bsas.recibirEnvio(envio);
            bsas.despacharEnvio(envio);
            envio.finalizarRecorrido();

            System.out.println("\nCONSULTAS DE HISTORIAL");
            mostrarHistorial(envio);
            System.out.println();
            mostrarSucursalesVisitadas(envio);
            System.out.println();
            mostrarUltimoMovimiento(envio);

            System.out.println("\nPRUEBA DE VALIDACIONES");

            probar("despachar un envío ya entregado",
                    () -> bsas.despacharEnvio(envio));

            probar("recibir un envío ya entregado",
                    () -> viedma.recibirEnvio(envio));

            probar("agregar un paquete a un envío que ya salió",
                    () -> envio.agregarPaquete(new Paquete(0.5)));

            probar("registrar un envío sin paquetes",
                    () -> cliente.agregarEnvio(new EnvioEstandar("Córdoba")));

            probar("operar sobre un envío inexistente",
                    () -> viedma.recibirEnvio(null));

            Envio envioLleno = new EnvioExpress("Rosario");
            envioLleno.agregarPaquete(new Paquete(1.0));
            envioLleno.agregarPaquete(new Paquete(1.0));
            envioLleno.agregarPaquete(new Paquete(1.0));
            probar("agregar un cuarto paquete a un envío",
                    () -> envioLleno.agregarPaquete(new Paquete(1.0)));

            Envio envioEnSucursal = new EnvioEstandar("Neuquén");
            envioEnSucursal.agregarPaquete(new Paquete(2.0));
            viedma.recibirEnvio(envioEnSucursal);

            probar("entregar un envío que todavía no fue despachado",
                    () -> envioEnSucursal.finalizarRecorrido());

            probar("recibir un envío que se encuentra en otra etapa",
                    () -> bahia.recibirEnvio(envioEnSucursal));

        } catch (IllegalStateException e) {
            System.err.println("Error de validación capturado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error de argumento capturado: " + e.getMessage());
        }
    }

    // App solo muestra lo que el envío le devuelve.

    private static void mostrarHistorial(Envio envio) {
        System.out.println("--- Historial del Envío con destino a: " + envio.getDestino() + " ---");
        for (RegistroSeguimiento registro : envio.obtenerHistorialCompleto()) {
            System.out.println(registro.getDetalle());
        }
    }

    private static void mostrarSucursalesVisitadas(Envio envio) {
        System.out.println("Sucursales visitadas en trayecto a " + envio.getDestino() + ":");
        for (Sucursal sucursal : envio.obtenerSucursalesVisitadas()) {
            System.out.println("- " + sucursal.getNombre());
        }
    }

    private static void mostrarUltimoMovimiento(Envio envio) {
        RegistroSeguimiento ultimo = envio.obtenerUltimoMovimiento();
        System.out.println("Último movimiento: " + ultimo.getDetalle());
    }

    private static void probar(String descripcion, Runnable operacion) {
        System.out.println("Intentando " + descripcion + "...");
        try {
            operacion.run();
            System.out.println("  ERROR: la operación no fue impedida.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("  Excepción capturada: " + e.getMessage());
        }
    }
}
