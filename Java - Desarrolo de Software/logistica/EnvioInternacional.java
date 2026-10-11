package logistica;

public class EnvioInternacional extends Envio {

    private static final double COSTO_POR_KG = 1800.0;
    private static final double COSTO_ADUANA = 3000.0;

    public EnvioInternacional(String destino) {
        super(destino);
    }

    @Override
    public double calcularCosto() {
        return (calcularPesoTotal() * COSTO_POR_KG) + COSTO_ADUANA;
    }
}
