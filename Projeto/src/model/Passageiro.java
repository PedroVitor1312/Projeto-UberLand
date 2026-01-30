package model;

import java.time.LocalDate;
import enums.FormaPagamento;

/**
 * Representa um passageiro do sistema.
 * Herda dados básicos da classe Pessoa.
 * Pode ser promovido a VIP após X corridas.
 */
public class Passageiro extends Pessoa {

    // Forma de pagamento preferida do passageiro
    private FormaPagamento formaPagamento;

    // Nota média (0 a 5)
    private double notaMedia;

    // Número total de corridas realizadas
    private int numeroCorridas;

    // CONSTANTE: número de corridas necessárias para virar VIP
    private static final int CORRIDAS_PARA_VIP = 20;

    /**
     * Construtor do Passageiro.
     */
    public Passageiro(String nome, String cpf, LocalDate dataNascimento, String email,
                      String sexo, String celular, String endereco, FormaPagamento formaPagamento) {
        super(nome, cpf, dataNascimento, email, sexo, celular, endereco);
        this.formaPagamento = formaPagamento;
        this.notaMedia = 0.0;
        this.numeroCorridas = 0;
    }

    /**
     * Construtor simplificado (Requisito 2a)
     */
    public Passageiro(String nome, String cpf) {
        super(nome, cpf, LocalDate.now(), "", "N/A", "", "");
        this.formaPagamento = FormaPagamento.CARTAO;
        this.notaMedia = 0.0;
        this.numeroCorridas = 0;
    }

    /**
     * Construtor default (Requisito 2b)
     */
    public Passageiro() {
        super("", "", LocalDate.now(), "", "N/A", "", "");
        this.formaPagamento = FormaPagamento.CARTAO;
        this.notaMedia = 0.0;
        this.numeroCorridas = 0;
    }

    // ====== MÉTODOS PRINCIPAIS ======

    /**
     * Atualiza a nota média após receber nova avaliação.
     */
    public void registrarAvaliacao(double novaNota) {
        if (novaNota < 0 || novaNota > 5) return;

        // média ponderada simples
        double somaNotas = this.notaMedia * this.numeroCorridas;
        this.notaMedia = (somaNotas + novaNota) / (this.numeroCorridas + 1);
    }

    /**
     * Registra uma nova corrida feita pelo passageiro.
     * Retorna TRUE se o passageiro atingiu o critério para virar VIP.
     */
    public boolean registrarCorrida() {
        this.numeroCorridas++;
        return verificarElegibilidadeVIP();
    }

    /**
     * Verifica se o passageiro atingiu o critério para se tornar VIP.
     * Regra: após CORRIDAS_PARA_VIP corridas.
     */
    public boolean verificarElegibilidadeVIP() {
        return this.numeroCorridas >= CORRIDAS_PARA_VIP;
    }

    /**
     * Promove o passageiro para VIP.
     * Retorna um objeto ClienteVip com desconto baseado na regra confidencial.
     */
    public ClienteVip promoverParaVIP() {
        if (!verificarElegibilidadeVIP()) {
            throw new IllegalStateException("Passageiro não atingiu o critério para VIP");
        }

        // REGRA CONFIDENCIAL DA UBERLAND (Requisito j):
        // Desconto baseado na nota média do passageiro
        double percentualDesconto = calcularDescontoVIP();
        
        return new ClienteVip(this, percentualDesconto);
    }

    /**
     * REGRA CONFIDENCIAL DA UBERLAND (Requisito j)
     * Calcula o percentual de desconto baseado na nota média:
     * - Nota 5.0: 15% de desconto
     * - Nota 4.0-4.9: 10% de desconto  
     * - Nota 3.0-3.9: 5% de desconto
     * - Nota < 3.0: 2% de desconto (incentivo)
     */
    private double calcularDescontoVIP() {
        if (this.notaMedia >= 5.0) {
            return 0.15; // 15%
        } else if (this.notaMedia >= 4.0) {
            return 0.10; // 10%
        } else if (this.notaMedia >= 3.0) {
            return 0.05; // 5%
        } else {
            return 0.02; // 2%
        }
    }

    /**
     * Retorna informações resumidas do passageiro.
     */
    public String getInfoResumida() {
        return String.format("%s (CPF: %s) - Corridas: %d - Nota: %.1f",
                getNome(), getCpf(), numeroCorridas, notaMedia);
    }

    // ====== GETTERS ======
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    public int getNumeroCorridas() {
        return numeroCorridas;
    }

    public static int getCorridasParaVIP() {
        return CORRIDAS_PARA_VIP;
    }

    // ====== SETTERS ======
    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void setNotaMedia(double notaMedia) {
        if (notaMedia >= 0 && notaMedia <= 5) {
            this.notaMedia = notaMedia;
        }
    }

    public void setNumeroCorridas(int numeroCorridas) {
        if (numeroCorridas >= 0) {
            this.numeroCorridas = numeroCorridas;
        }
    }

    // ====== TO STRING ======
    @Override
    public String toString() {
        return "Passageiro{" +
                "nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", notaMedia=" + notaMedia +
                ", numeroCorridas=" + numeroCorridas +
                ", elegivelVIP=" + verificarElegibilidadeVIP() +
                '}';
    }
}