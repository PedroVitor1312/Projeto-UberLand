package model;

import enums.StatusVeiculo;

/**
 * Classe abstrata base para todos os veículos da UberLand.
 * UberX, UberComfort e UberBlack herdam dela.
 */
public abstract class Veiculo {

    private String placa;
    private String chassi;
    private String cor;
    private int ano;
    private String marca;
    private String modelo;
    private int capacidade;
    private StatusVeiculo status;
    private Motorista motoristaAssociado; // Referência adicional para o motorista associado

    /**
     * Construtor base do veículo.
     */
    public Veiculo(String placa, String chassi, String cor, int ano, String marca,
                   String modelo, int capacidade) {
        this.placa = placa;
        this.chassi = chassi;
        this.cor = cor;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.status = StatusVeiculo.DISPONIVEL;
        this.motoristaAssociado = null; // Inicialmente, nenhum motorista está associado
    }

    // ====== GETTERS ======
    public String getPlaca() {
        return placa;
    }

    public String getChassi() {
        return chassi;
    }

    public String getCor() {
        return cor;
    }

    public int getAno() {
        return ano;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public Motorista getMotoristaAssociado() {
        return motoristaAssociado;
    }

    // ====== SETTERS ======
    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    /**
     * Associa um motorista ao veículo.
     */
    public void associarMotorista(Motorista motorista) {
        if (motorista == null) {
            throw new IllegalArgumentException("Motorista não pode ser nulo.");
        }
        this.motoristaAssociado = motorista;
        System.out.println("Motorista associado ao veículo com sucesso!");
    }

    /**
     * Calcula a tarifa da corrida, depende do tipo de veículo.
     */
    public abstract double calcularTarifa(double distancia);
}