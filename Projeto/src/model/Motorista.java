package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Motorista representa o usuário que dirige no sistema.
 * Ela herda dados básicos da classe Pessoa.
 */
public class Motorista extends Pessoa {

    private String cnh;                 // CNH do motorista
    private boolean ativo;              // Se o motorista está ativo no sistema
    private List<Veiculo> veiculos;     // Lista de veículos associados

    /**
     * Construtor do motorista.
     */
    public Motorista(String nome, String cpf, LocalDate dataNascimento, String email,
                     String sexo, String celular, String endereco, String cnh) {
        super(nome, cpf, dataNascimento, email, sexo, celular, endereco);
        this.cnh = cnh;
        this.ativo = true;
        this.veiculos = new ArrayList<>();
    }

    // ======== MÉTODOS ========

    /**
     * Adiciona um veículo ao motorista.
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo != null) {
            veiculos.add(veiculo);
        }
    }

    /**
     * Ativa o motorista.
     */
    public void ativar() {
        this.ativo = true;
    }

    /**
     * Desativa o motorista e todos os veículos associados.
     */
    public void desativar() {
        this.ativo = false;
        for (Veiculo veiculo : veiculos) {
            veiculo.setStatus(enums.StatusVeiculo.NAO_DISPONIVEL);
        }
    }

    /**
     * Calcula tarifa com base no primeiro veículo cadastrado.
     */
    public double calcularTarifa(double distancia) {
        if (!veiculos.isEmpty()) {
            return veiculos.get(0).calcularTarifa(distancia);
        }
        return 5.0 + (distancia * 2.0); // tarifa base
    }

    // ======== GETTERS ========

    public String getCnh() {
        return cnh;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }
}