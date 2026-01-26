package model;

import java.time.LocalDate;

public class Motorista extends Pessoa {

    private String cnh;
    private Veiculo veiculo; // Pode ser associado depois

    public Motorista(String nome, String cpf, LocalDate dataNascimento, String email, String sexo, String celular, String endereco, String cnh) {
        super(nome, cpf, dataNascimento, email, sexo, celular, endereco);
        this.cnh = cnh;
    }

    public String getCnh() {
        return cnh;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    // Se não tiver veículo, aplica tarifa base
    public double calcularTarifa(double distancia) {
        if (veiculo != null) {
            return veiculo.calcularTarifa(distancia);
        }
        return 5.0 + (distancia * 2.0);
    }
}