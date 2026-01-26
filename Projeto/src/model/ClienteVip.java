package model;

/**
 * ClienteVip é um Passageiro especial com desconto.
 */
public class ClienteVip extends Passageiro {

    // percentual em decimal, ex: 0.15 = 15%
    private double percentualDesconto;

    /**
     * Construtor que promove um Passageiro para VIP.
     */
    public ClienteVip(Passageiro passageiro, double percentualDesconto) {
        super(
            passageiro.getNome(),
            passageiro.getCpf(),
            passageiro.getDataNascimento(),
            passageiro.getEmail(),
            passageiro.getSexo(),
            passageiro.getCelular(),
            passageiro.getEndereco(),
            passageiro.getFormaPagamento()
        );
        this.percentualDesconto = percentualDesconto;
    }

    /**
     * Aplica desconto ao valor total da corrida.
     */
    public double aplicarDesconto(double valor) {
        return valor - (valor * percentualDesconto);
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }
}