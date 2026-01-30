package model;

import java.time.LocalDateTime;
import enums.FormaPagamento;
import enums.StatusCorrida;

public class Corrida {
    private Passageiro passageiro;
    private Motorista motorista;
    private Veiculo veiculo;
    private String origem;
    private String destino;
    private double distancia;
    private double valor;
    private double valorMotorista;
    private double valorUberLand;
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime horaChegadaOrigem;
    private LocalDateTime horaChegadaDestino;
    private int duracaoMinutos;
    private StatusCorrida status;
    private QuemCancelou quemCancelou; // Enum específico
    private FormaPagamento formaPagamento;
    private String id; // ID único para a corrida

    // ENUM interno para quem cancelou
    public enum QuemCancelou {
        CLIENTE,
        MOTORISTA,
        SISTEMA,
        NENHUM
    }

    // CONSTANTE: Porcentagem fixa para UberLand (Requisito n)
    private static final double PORCENTAGEM_UBERLAND = 0.60; // 60%
    private static int contadorId = 1; // Para gerar IDs únicos

    /**
     * Construtor completo (Requisito 2c).
     */
    public Corrida(Passageiro passageiro, Motorista motorista, Veiculo veiculo,
                   String origem, String destino, double distancia) {
        this.passageiro = passageiro;
        this.motorista = motorista;
        this.veiculo = veiculo;
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
        this.dataHoraSolicitacao = LocalDateTime.now();
        this.status = StatusCorrida.REALIZADA;
        this.formaPagamento = passageiro.getFormaPagamento();
        this.quemCancelou = QuemCancelou.NENHUM;
        this.id = "CR" + String.format("%04d", contadorId++);
        this.horaChegadaOrigem = null;
        this.horaChegadaDestino = null;
        this.duracaoMinutos = 0;
        
        // Calcular todos os valores
        calcularValores();
    }

    /**
     * Construtor alternativo (para compatibilidade).
     */
    public Corrida(Passageiro passageiro, Motorista motorista, Veiculo veiculo, double distancia) {
        this(passageiro, motorista, veiculo, "Origem não informada", 
             "Destino não informado", distancia);
    }

    /**
     * Calcula todos os valores da corrida.
     */
    private void calcularValores() {
        // Valor base da corrida
        this.valor = veiculo.calcularTarifa(distancia);
        
        // Aplicar desconto VIP se for o caso
        if (passageiro instanceof ClienteVip) {
            ClienteVip vip = (ClienteVip) passageiro;
            this.valor = vip.aplicarDesconto(this.valor);
        }
        
        // Divisão 60/40 entre UberLand e motorista (Requisito n)
        this.valorUberLand = this.valor * PORCENTAGEM_UBERLAND;
        this.valorMotorista = this.valor - this.valorUberLand;
    }

    /**
     * Registra a chegada do motorista na origem.
     */
    public void registrarChegadaOrigem() {
        this.horaChegadaOrigem = LocalDateTime.now();
        System.out.println("✅ Motorista chegou na origem às: " + horaChegadaOrigem);
    }

    /**
     * Finaliza a corrida com chegada ao destino.
     */
    public void finalizarCorrida() {
        this.horaChegadaDestino = LocalDateTime.now();
        
        // Calcular duração em minutos
        if (horaChegadaOrigem != null) {
            this.duracaoMinutos = (int) java.time.Duration
                .between(horaChegadaOrigem, horaChegadaDestino)
                .toMinutes();
        }
        
        this.status = StatusCorrida.REALIZADA;
        
        // Atualizar status do veículo para "FINALIZANDO" 5 minutos antes do fim
        // (simulação - em sistema real seria com timer)
        System.out.println("⚠️  Atenção: 5 minutos para o fim da viagem");
        veiculo.setStatus(enums.StatusVeiculo.FINALIZANDO);
        
        // Após finalizar, veículo volta a ficar disponível
        // (em sistema real seria após confirmação de pagamento)
    }

    /**
     * Libera o veículo após conclusão da corrida.
     */
    public void liberarVeiculo() {
        veiculo.setStatus(enums.StatusVeiculo.DISPONIVEL);
        System.out.println("✅ Veículo liberado e disponível para novas corridas");
    }

    /**
     * Cancela a corrida antes do início.
     */
    public void cancelarAntes(QuemCancelou quemCancelou) {
        this.status = StatusCorrida.CANCELADA_ANTES;
        this.quemCancelou = quemCancelou;
        
        // VIPs não pagam taxa de cancelamento (benefício VIP)
        if (!(passageiro instanceof ClienteVip)) {
            this.valor = 5.0; // Taxa de cancelamento fixa
            calcularValores(); // Recalcular divisão
        } else {
            this.valor = 0.0; // VIPs isentos
            this.valorUberLand = 0.0;
            this.valorMotorista = 0.0;
        }
        
        // Liberar veículo imediatamente
        veiculo.setStatus(enums.StatusVeiculo.DISPONIVEL);
        
        System.out.println("❌ Corrida cancelada ANTES do início por: " + quemCancelou);
    }

    /**
     * Cancela a corrida durante o trajeto.
     */
    public void cancelarDurante(QuemCancelou quemCancelou) {
        this.status = StatusCorrida.CANCELADA_DURANTE;
        this.quemCancelou = quemCancelou;
        
        // Cobrar proporcionalmente ao que foi percorrido
        if (duracaoMinutos > 0) {
            // Supondo que uma corrida média dura 30 minutos
            double percentualPercorrido = Math.min(duracaoMinutos / 30.0, 1.0);
            this.valor *= percentualPercorrido;
            calcularValores(); // Recalcular divisão
        }
        
        // Liberar veículo
        veiculo.setStatus(enums.StatusVeiculo.DISPONIVEL);
        
        System.out.println("❌ Corrida cancelada DURANTE o trajeto por: " + quemCancelou);
    }

    /**
     * Simula o início da viagem (para teste).
     */
    public void iniciarViagem() {
        registrarChegadaOrigem();
        veiculo.setStatus(enums.StatusVeiculo.EM_VIAGEM);
        System.out.println("🚗 Viagem iniciada!");
    }

    /**
     * Simula que faltam 5 minutos para o fim (para teste).
     */
    public void simular5MinutosParaFim() {
        veiculo.setStatus(enums.StatusVeiculo.FINALIZANDO);
        System.out.println("⚠️  Status do veículo alterado para: FINALIZANDO");
    }

    // ====== GETTERS COMPLETOS ======
    public Passageiro getPassageiro() {
        return passageiro;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public double getValor() {
        return valor;
    }

    public double getValorMotorista() {
        return valorMotorista;
    }

    public double getValorUberLand() {
        return valorUberLand;
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public LocalDateTime getHoraChegadaOrigem() {
        return horaChegadaOrigem;
    }

    public LocalDateTime getHoraChegadaDestino() {
        return horaChegadaDestino;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public StatusCorrida getStatus() {
        return status;
    }

    public QuemCancelou getQuemCancelou() {
        return quemCancelou;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public String getId() {
        return id;
    }

    public static double getPorcentagemUberland() {
        return PORCENTAGEM_UBERLAND;
    }

    // ====== SETTERS COMPLETOS ======
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
        calcularValores(); // Recalcular valores com nova distância
    }

    public void setValor(double valor) {
        this.valor = valor;
        calcularValores(); // Recalcular divisão
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public void setHoraChegadaOrigem(LocalDateTime horaChegadaOrigem) {
        this.horaChegadaOrigem = horaChegadaOrigem;
    }

    public void setHoraChegadaDestino(LocalDateTime horaChegadaDestino) {
        this.horaChegadaDestino = horaChegadaDestino;
        // Recalcular duração
        if (horaChegadaOrigem != null) {
            this.duracaoMinutos = (int) java.time.Duration
                .between(horaChegadaOrigem, horaChegadaDestino)
                .toMinutes();
        }
    }

    public void setStatus(StatusCorrida status) {
        this.status = status;
    }

    public void setQuemCancelou(QuemCancelou quemCancelou) {
        this.quemCancelou = quemCancelou;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    // ====== MÉTODOS ÚTEIS ======

    /**
     * Retorna informações resumidas da corrida.
     */
    public String getInfoResumida() {
        return String.format("[%s] %s → %s | R$ %.2f | %s",
                id, origem, destino, valor, status);
    }

    /**
     * Retorna informações detalhadas da corrida.
     */
    public String getInfoDetalhada() {
        StringBuilder sb = new StringBuilder();
        sb.append("📋 DETALHES DA CORRIDA 📋\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Passageiro: ").append(passageiro.getNome()).append("\n");
        sb.append("Motorista: ").append(motorista.getNome()).append("\n");
        sb.append("Veículo: ").append(veiculo.getModelo()).append(" (").append(veiculo.getPlaca()).append(")\n");
        sb.append("Trajeto: ").append(origem).append(" → ").append(destino).append("\n");
        sb.append("Distância: ").append(String.format("%.1f", distancia)).append(" km\n");
        sb.append("Valor total: R$ ").append(String.format("%.2f", valor)).append("\n");
        sb.append("→ UberLand (60%): R$ ").append(String.format("%.2f", valorUberLand)).append("\n");
        sb.append("→ Motorista (40%): R$ ").append(String.format("%.2f", valorMotorista)).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Forma de pagamento: ").append(formaPagamento).append("\n");
        
        if (horaChegadaOrigem != null) {
            sb.append("Chegada na origem: ").append(horaChegadaOrigem.format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        }
        
        if (horaChegadaDestino != null) {
            sb.append("Chegada no destino: ").append(horaChegadaDestino.format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
            sb.append("Duração: ").append(duracaoMinutos).append(" minutos\n");
        }
        
        if (quemCancelou != QuemCancelou.NENHUM) {
            sb.append("Cancelado por: ").append(quemCancelou).append("\n");
        }
        
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format(
            "Corrida{id=%s, passageiro=%s, motorista=%s, origem='%s', destino='%s', " +
            "valor=%.2f, status=%s, formaPagamento=%s}",
            id, passageiro.getNome(), motorista.getNome(), origem, destino, 
            valor, status, formaPagamento
        );
    }

    /**
     * Verifica se a corrida está em andamento.
     */
    public boolean isEmAndamento() {
        return status == StatusCorrida.REALIZADA && 
               horaChegadaOrigem != null && 
               horaChegadaDestino == null;
    }

    /**
     * Verifica se a corrida pode ser cancelada.
     */
    public boolean podeSerCancelada() {
        return horaChegadaDestino == null; // Só pode cancelar se não terminou
    }
}