package model;

import java.time.LocalDate;

import enums.FormaPagamento;

public class Passageiro extends Pessoa {

    private FormaPagamento formaPagamento;

    public Passageiro(String nome, String cpf, LocalDate dataNascimento, String email, String sexo, String celular, String endereco, FormaPagamento formaPagamento) {
        super(nome, cpf, dataNascimento, email, sexo, celular, endereco);
        this.formaPagamento = formaPagamento;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}