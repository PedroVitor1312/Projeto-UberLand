package model;

import java.time.LocalDate;
import enums.FormaPagamento;

/**
 * ClienteVip é um Passageiro especial com prioridade e desconto.
 * Herda de Passageiro e adiciona funcionalidades exclusivas para VIPs.
 */
public class ClienteVip extends Passageiro {

    // Percentual de desconto em decimal, ex: 0.15 = 15%
    private double percentualDesconto;
    
    // Data em que se tornou VIP
    private LocalDate dataPromocao;
    
    // Nível do VIP (pode ser usado para benefícios extras)
    private int nivelVIP;
    
    // Prioridade na escolha de motoristas (true = tem prioridade)
    private boolean prioridadeMotoristas;

    // ====== CONSTRUTORES ======

    /**
     * Construtor principal - promove um Passageiro para VIP.
     * Inclui regra confidencial de desconto.
     */
    public ClienteVip(Passageiro passageiro) {
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
        
        // Copiar atributos específicos do passageiro
        this.setNotaMedia(passageiro.getNotaMedia());
        this.setNumeroCorridas(passageiro.getNumeroCorridas());
        
        // Aplicar REGRA CONFIDENCIAL DA UBERLAND
        this.percentualDesconto = calcularDescontoConfidencial(passageiro);
        this.dataPromocao = LocalDate.now();
        this.nivelVIP = determinarNivelVIP(passageiro);
        this.prioridadeMotoristas = true; // VIPs sempre têm prioridade
    }

    /**
     * Construtor alternativo com percentual personalizado.
     */
    public ClienteVip(Passageiro passageiro, double percentualDesconto) {
        this(passageiro); // Chama o construtor principal
        this.percentualDesconto = percentualDesconto; // Sobrescreve o desconto
    }

    // ====== REGRA CONFIDENCIAL DA UBERLAND (Requisito j) ======

    /**
     * REGRA CONFIDENCIAL DA UBERLAND:
     * Calcula o desconto baseado em múltiplos fatores:
     * 1. Nota média do passageiro (peso 60%)
     * 2. Número de corridas realizadas (peso 30%)
     * 3. Fidelidade (tempo como usuário) (peso 10%)
     * 
     * Fórmula secreta não divulgada aos usuários.
     */
    private double calcularDescontoConfidencial(Passageiro passageiro) {
        double desconto = 0.0;
        
        // FATOR 1: Nota média (0-5) → 0-15% de desconto
        double fatorNota = passageiro.getNotaMedia() * 0.03; // 5*0.03 = 15%
        
        // FATOR 2: Número de corridas (a cada 10 corridas +0.5%)
        double fatorCorridas = (passageiro.getNumeroCorridas() / 10.0) * 0.005;
        
        // FATOR 3: Tempo como usuário (simulação)
        // Para simplificar, consideraremos 1% fixo por enquanto
        double fatorFidelidade = 0.01;
        
        // FATOR 4: Bônus por avaliações consistentes
        double fatorConsistencia = 0.0;
        if (passageiro.getNotaMedia() >= 4.5) {
            fatorConsistencia = 0.02; // +2% para usuários bem avaliados
        }
        
        // Cálculo final do desconto
        desconto = fatorNota + fatorCorridas + fatorFidelidade + fatorConsistencia;
        
        // Limitar desconto máximo a 25%
        if (desconto > 0.25) {
            desconto = 0.25;
        }
        
        // Garantir desconto mínimo de 2%
        if (desconto < 0.02) {
            desconto = 0.02;
        }
        
        return desconto;
    }

    /**
     * Determina o nível VIP baseado no desempenho do passageiro.
     */
    private int determinarNivelVIP(Passageiro passageiro) {
        int nivel = 1; // Nível básico
        
        if (passageiro.getNumeroCorridas() >= 50) {
            nivel = 2; // VIP Prata
        }
        if (passageiro.getNumeroCorridas() >= 100 && passageiro.getNotaMedia() >= 4.5) {
            nivel = 3; // VIP Ouro
        }
        if (passageiro.getNumeroCorridas() >= 200 && passageiro.getNotaMedia() >= 4.8) {
            nivel = 4; // VIP Diamante
        }
        
        return nivel;
    }

    // ====== MÉTODOS ESPECÍFICOS DE VIP ======

    /**
     * Aplica desconto VIP ao valor total da corrida.
     * VIPs também têm isenção de taxa de cancelamento.
     */
    public double aplicarDesconto(double valor) {
        double valorComDesconto = valor - (valor * percentualDesconto);
        
        // VIPs nível 3+ têm desconto adicional em corridas noturnas
        if (nivelVIP >= 3 && isCorridaNoturna()) {
            valorComDesconto *= 0.95; // +5% de desconto
        }
        
        return valorComDesconto;
    }

    /**
     * Verifica se a corrida é noturna (para descontos especiais).
     * Método simulado para exemplo.
     */
    private boolean isCorridaNoturna() {
        // Em implementação real, verificaria a hora atual
        // Por enquanto, retorna false como placeholder
        return false;
    }

    /**
     * VIPs podem cancelar corridas sem taxa.
     */
    public boolean podeCancelarSemTaxa() {
        return true; // Todos os VIPs cancelam sem taxa
    }

    /**
     * VIPs têm prioridade na escolha de motoristas.
     * Este método seleciona os motoristas melhor avaliados.
     */
    public boolean temPrioridadeMotoristas() {
        return prioridadeMotoristas;
    }

    /**
     * Atualiza o nível VIP baseado em novas corridas e avaliações.
     */
    public void atualizarNivelVIP() {
        this.nivelVIP = determinarNivelVIP(this);
        
        // Aumenta desconto conforme sobe de nível
        if (nivelVIP >= 3) {
            percentualDesconto += 0.02; // +2% de desconto
        }
        if (nivelVIP >= 4) {
            percentualDesconto += 0.03; // +3% adicional
        }
    }

    /**
     * Benefício exclusivo: solicitar motorista específico.
     */
    public boolean podeSolicitarMotoristaEspecifico() {
        return nivelVIP >= 3; // VIP Ouro ou superior
    }

    // ====== GETTERS ======
    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    public LocalDate getDataPromocao() {
        return dataPromocao;
    }

    public int getNivelVIP() {
        return nivelVIP;
    }

    public String getNivelVIPString() {
        switch (nivelVIP) {
            case 1: return "VIP";
            case 2: return "VIP Prata";
            case 3: return "VIP Ouro";
            case 4: return "VIP Diamante";
            default: return "VIP";
        }
    }

    public boolean isPrioridadeMotoristas() {
        return prioridadeMotoristas;
    }

    // ====== SETTERS ======
    public void setPercentualDesconto(double percentualDesconto) {
        if (percentualDesconto >= 0 && percentualDesconto <= 0.5) { // Máximo 50%
            this.percentualDesconto = percentualDesconto;
        }
    }

    public void setPrioridadeMotoristas(boolean prioridadeMotoristas) {
        this.prioridadeMotoristas = prioridadeMotoristas;
    }

    // ====== TO STRING ======
    @Override
    public String toString() {
        return String.format("ClienteVIP{nome='%s', nivel=%s, desconto=%.1f%%, desde=%s}",
                getNome(), getNivelVIPString(), percentualDesconto * 100, dataPromocao);
    }

    /**
     * Retorna informações detalhadas do VIP.
     */
    public String getInfoDetalhada() {
        return String.format(
            "👑 CLIENTE VIP 👑\n" +
            "Nome: %s\n" +
            "Nível: %s\n" +
            "Desconto: %.1f%%\n" +
            "Data da promoção: %s\n" +
            "Corridas realizadas: %d\n" +
            "Nota média: %.1f/5.0\n" +
            "Prioridade em motoristas: %s\n" +
            "Cancelamento sem taxa: %s",
            getNome(),
            getNivelVIPString(),
            percentualDesconto * 100,
            dataPromocao,
            getNumeroCorridas(),
            getNotaMedia(),
            prioridadeMotoristas ? "SIM" : "NÃO",
            podeCancelarSemTaxa() ? "SIM" : "NÃO"
        );
    }
}