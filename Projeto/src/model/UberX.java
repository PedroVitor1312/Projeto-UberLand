package model;

/**
 * Classe UberX - categoria básica.
 */
public class UberX extends Veiculo {

    // Valores fixos da categoria UberX
    private static final double TARIFA_MINIMA = 5.0;
    private static final double CUSTO_POR_KM = 2.0;

    private boolean arCondicionado;
    private boolean confortoBasico; // ✅ CORRIGIDO: era "espacoExtra"

    /**
     * Construtor do UberX.
     */
    public UberX(String placa, String chassi, String cor, int ano, String marca,
                 String modelo, int capacidade, boolean arCondicionado, boolean confortoBasico) {
        super(placa, chassi, cor, ano, marca, modelo, capacidade);
        this.arCondicionado = arCondicionado;
        this.confortoBasico = confortoBasico; // ✅ CORRIGIDO
    }

    @Override
    public double calcularTarifa(double distancia) {
        double valor = TARIFA_MINIMA + (distancia * CUSTO_POR_KM);

        // ✅ CORRIGIDO: Se possui ar condicionado OU conforto básico
        if (arCondicionado || confortoBasico) {
            valor += TARIFA_MINIMA;
        }

        return valor;
    }

    // ====== GETTERS ======
    public boolean isArCondicionado() {
        return arCondicionado;
    }

    public boolean isConfortoBasico() { // ✅ CORRIGIDO
        return confortoBasico;
    }
}