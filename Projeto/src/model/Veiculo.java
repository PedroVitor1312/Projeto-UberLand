package model;

import enums.StatusVeiculo;

public abstract class Veiculo {
    private String placa;
    private String modelo;
    private int capacidade;
    private StatusVeiculo status;

    public Veiculo(String placa, String modelo, int capacidade) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidade = capacidade;
        this.status = StatusVeiculo.DISPONIVEL;
    }

    public String getPlaca() {
        return placa;
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

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public abstract double calcularTarifa(double distancia);
}