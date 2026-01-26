package model;

public class UberX extends Veiculo {
    private boolean arCondicionado;

    public UberX(String placa, String modelo, int capacidade, boolean arCondicionado) {
        super(placa, modelo, capacidade);
        this.arCondicionado = arCondicionado;
    }

    @Override
    public double calcularTarifa(double distancia) {
        double base = 4.0;
        if (arCondicionado) {
            base += 2.0;
        }
        return base + (distancia * 1.6);
    }
}