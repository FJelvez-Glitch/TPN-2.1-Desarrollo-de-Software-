package logistica;

public class Paquete {
    private String codigo;
    private String descripcion;
    private double peso;
    private EstadoPaquete estado;

    public Paquete(String codigo, String descripcion, double peso) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.peso = peso;
        this.estado = EstadoPaquete.recibido;
    }

    public Paquete(double peso) {
        this("Sin código", "Sin descripción", peso);
    }

    public EstadoPaquete getEstado() {
        return estado;
    }

    public double getPeso() {
        return peso;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public void preparar() {
        cambiarEstado(EstadoPaquete.en_preparacion);
    }

    public void iniciarViaje() {
        cambiarEstado(EstadoPaquete.en_viaje);
    }

    public void despachar() {
        iniciarViaje();
    }

    public void entregar() {
        cambiarEstado(EstadoPaquete.entregado);
    }


    private void cambiarEstado(EstadoPaquete nuevoEstado) {
        if (!estado.puedePasarA(nuevoEstado)) {
            throw new IllegalStateException("Transición inválida de estado: de " + estado + " a " + nuevoEstado);
        }
        this.estado = nuevoEstado;
    }
}
