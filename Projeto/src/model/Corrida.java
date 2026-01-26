package model;

import java.time.LocalDateTime;

import enums.StatusCorrida;

public class Corrida {
    private Passageiro passageiro;
    private Motorista motorista;
    private Veiculo veiculo;
    private double distancia;
    private double valor;
    private LocalDateTime dataHora;
    private StatusCorrida status;

    public Corrida(Passageiro passageiro, Motorista motorista, Veiculo veiculo, double distancia) {
        this.passageiro = passageiro;
        this.motorista = motorista;
        this.veiculo = veiculo;
        this.distancia = distancia;
        this.dataHora = LocalDateTime.now();
        this.valor = veiculo.calcularTarifa(distancia);
        this.status = StatusCorrida.REALIZADA;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public double getDistancia() {
        return distancia;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusCorrida getStatus() {
        return status;
    }

    public void cancelarAntes() {
        this.status = StatusCorrida.CANCELADA_ANTES;
    }

    public void cancelarDurante() {
        this.status = StatusCorrida.CANCELADA_DURANTE;
    }
}