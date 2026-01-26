package model;

public class UberBlack extends Veiculo {

    public UberBlack(String placa, String modelo, int capacidade) {
        super(placa, modelo, capacidade);
    }

    @Override
    public double calcularTarifa(double distancia) {
        return 10.0 + (distancia * 3.0);
    }
}