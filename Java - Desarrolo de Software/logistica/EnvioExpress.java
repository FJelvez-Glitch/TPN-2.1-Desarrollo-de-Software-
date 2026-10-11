package logistica;

public class EnvioExpress extends Envio {

    private static final double COSTO_POR_KG = 900.0;
    private static final double RECARGO_URGENCIA = 1500.0;

    public EnvioExpress(String destino) {
        super(destino);
    }

    @Override
    public double calcularCosto() {
        return (calcularPesoTotal() * COSTO_POR_KG) + RECARGO_URGENCIA;
    }
}
