package logistica;

public class EnvioEstandar extends Envio {

    private static final double COSTO_POR_KG = 500.0;

    public EnvioEstandar(String destino) {
        super(destino);
    }

    @Override
    public double calcularCosto() {
        return calcularPesoTotal() * COSTO_POR_KG;
    }
}
