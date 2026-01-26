package model;

public class UberComfort extends Veiculo {

    public UberComfort(String placa, String modelo, int capacidade) {
        super(placa, modelo, capacidade);
    }

    @Override
    public double calcularTarifa(double distancia) {
        return 6.0 + (distancia * 2.2);
    }
}