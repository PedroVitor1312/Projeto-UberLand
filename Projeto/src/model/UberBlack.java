package model;

/**
 * Classe UberBlack - categoria premium.
 */
public class UberBlack extends Veiculo {

    // Valores fixos da categoria UberBlack
    private static final double TARIFA_MINIMA = 10.0;
    private static final double CUSTO_POR_KM = 3.0;

    private boolean interiorPremium;
    private boolean rodasLigaLeve;
    private int numeroMalas;

    /**
     * Construtor do UberBlack.
     */
    public UberBlack(String placa, String chassi, String cor, int ano, String marca,
                     String modelo, int capacidade, boolean interiorPremium,
                     boolean rodasLigaLeve, int numeroMalas) {
        super(placa, chassi, cor, ano, marca, modelo, capacidade);
        this.interiorPremium = interiorPremium;
        this.rodasLigaLeve = rodasLigaLeve;
        this.numeroMalas = numeroMalas;
    }

    @Override
    public double calcularTarifa(double distancia) {
        double valor = TARIFA_MINIMA + (distancia * CUSTO_POR_KM);

        // Para UberBlack, soma tarifa mínima * número de malas
        valor += TARIFA_MINIMA * numeroMalas;

        return valor;
    }

    // ====== GETTERS ======
    public boolean isInteriorPremium() {
        return interiorPremium;
    }

    public boolean isRodasLigaLeve() {
        return rodasLigaLeve;
    }

    public int getNumeroMalas() {
        return numeroMalas;
    }
}