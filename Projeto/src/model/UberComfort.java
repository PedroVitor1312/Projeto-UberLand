package model;

/**
 * Classe UberComfort - categoria intermediária.
 */
public class UberComfort extends Veiculo {

    // Valores fixos da categoria UberComfort
    private static final double TARIFA_MINIMA = 7.0;
    private static final double CUSTO_POR_KM = 2.5;

    private boolean espacoExtra;
    private boolean bancoReclinavel;
    private boolean arCondicionadoDualZone;

    /**
     * Construtor do UberComfort.
     */
    public UberComfort(String placa, String chassi, String cor, int ano, String marca,
                       String modelo, int capacidade, boolean espacoExtra,
                       boolean bancoReclinavel, boolean arCondicionadoDualZone) {
        super(placa, chassi, cor, ano, marca, modelo, capacidade);
        this.espacoExtra = espacoExtra;
        this.bancoReclinavel = bancoReclinavel;
        this.arCondicionadoDualZone = arCondicionadoDualZone;
    }

    @Override
    public double calcularTarifa(double distancia) {
        double valor = TARIFA_MINIMA + (distancia * CUSTO_POR_KM);

        // Se tiver dual zone OU espaço extra, soma 2x tarifa mínima
        if (arCondicionadoDualZone || espacoExtra) {
            valor += 2 * TARIFA_MINIMA;
        }

        return valor;
    }

    // ====== GETTERS ======
    public boolean isEspacoExtra() {
        return espacoExtra;
    }

    public boolean isBancoReclinavel() {
        return bancoReclinavel;
    }

    public boolean isArCondicionadoDualZone() {
        return arCondicionadoDualZone;
    }
}