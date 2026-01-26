package model;

import java.time.LocalDate;
import enums.FormaPagamento;

/**
 * Representa um passageiro do sistema.
 * Herda dados básicos da classe Pessoa.
 */
public class Passageiro extends Pessoa {

    // Forma de pagamento preferida do passageiro
    private FormaPagamento formaPagamento;

    // Nota média (0 a 5)
    private double notaMedia;

    // Número total de corridas realizadas
    private int numeroCorridas;

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

    // ====== MÉTODOS ======

    /**
     * Atualiza a nota média após receber nova avaliação.
     */
    public void registrarAvaliacao(double novaNota) {
        if (novaNota < 0 || novaNota > 5) return;

        // média ponderada simples
        double somaNotas = this.notaMedia * this.numeroCorridas;
        this.numeroCorridas++;
        this.notaMedia = (somaNotas + novaNota) / this.numeroCorridas;
    }

    /**
     * Registra uma nova corrida feita pelo passageiro.
     */
    public void registrarCorrida() {
        this.numeroCorridas++;
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

    // ====== SETTERS ======
    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}