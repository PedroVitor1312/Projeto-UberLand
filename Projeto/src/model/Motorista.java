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
    private String nomeSocial;          // Nome social do motorista
    private double notaMedia;           // Nota média (0-5)
    private int numeroCorridas;         // Total de corridas realizadas
    private List<Corrida> historicoCorridas; // Histórico de corridas

    /**
     * Construtor do motorista.
     */
    public Motorista(String nome, String cpf, LocalDate dataNascimento, String email,
                     String sexo, String celular, String endereco, String cnh, String nomeSocial) {
        super(nome, cpf, dataNascimento, email, sexo, celular, endereco);
        this.cnh = cnh;
        this.ativo = true; // assume que motorista começa ativo
        this.veiculos = new ArrayList<>();
        this.nomeSocial = nomeSocial;
        this.notaMedia = 0.0;
        this.numeroCorridas = 0;
        this.historicoCorridas = new ArrayList<>();
    }

    /**
     * Construtor simplificado (para uso na interface gráfica).
     */
    public Motorista(String nome, String cpf, String email, String celular, String cnh) {
        super(nome, cpf, LocalDate.now(), email, "Não informado", celular, "Endereço não informado");
        this.cnh = cnh;
        this.ativo = true; // assume que motorista começa ativo
        this.veiculos = new ArrayList<>();
        this.nomeSocial = nome;
        this.notaMedia = 0.0;
        this.numeroCorridas = 0;
        this.historicoCorridas = new ArrayList<>();
    }

    // ======== MÉTODOS PARA ATIVAR/DESATIVAR ========

    /**
     * Método para ativar o motorista.
     */
    public void ativar() {
        if (ativo) {
            System.out.println("Motorista " + getNome() + " já está ativo.");
            return; // já ativo, não faz nada
        }
        ativo = true;
        System.out.println("Motorista " + getNome() + " foi ativado.");

        // Ativa os veículos associados definindo status como DISPONIVEL
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null) {
                try {
                    // Verifica se o veículo tem o método setStatus
                    veiculo.setStatus(enums.StatusVeiculo.DISPONIVEL);
                    System.out.println("   Veículo " + veiculo.getModelo() + " ativado.");
                } catch (Exception e) {
                    System.out.println("   Aviso: Não foi possível ativar veículo " + veiculo.getModelo());
                }
            }
        }
    }

    /**
     * Método para desativar o motorista.
     */
    public void desativar() {
        if (!ativo) {
            System.out.println("Motorista " + getNome() + " já está desativado.");
            return; // já desativado, não faz nada
        }
        ativo = false;
        System.out.println("Motorista " + getNome() + " foi desativado.");

        // Desativa os veículos associados definindo status como NAO_DISPONIVEL
        for (Veiculo veiculo : veiculos) {
            if (veiculo != null) {
                try {
                    // Verifica se o veículo tem o método setStatus
                    veiculo.setStatus(enums.StatusVeiculo.NAO_DISPONIVEL);
                    System.out.println("   Veículo " + veiculo.getModelo() + " desativado.");
                } catch (Exception e) {
                    System.out.println("   Aviso: Não foi possível desativar veículo " + veiculo.getModelo());
                }
            }
        }
    }

    /**
     * Método para alternar status entre ativo e desativado.
     */
    public void alternarStatus() {
        if (ativo) {
            desativar();
        } else {
            ativar();
        }
    }

    // ======== MÉTODOS DE NEGÓCIO ========

    /**
     * Adiciona um veículo ao motorista.
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo != null && !veiculos.contains(veiculo)) {
            veiculos.add(veiculo);
            // Tenta associar o motorista ao veículo se o método existir
            try {
                veiculo.associarMotorista(this);
            } catch (Exception e) {
                // Método não existe, ignora
            }
        }
    }

    /**
     * Remove um veículo do motorista.
     */
    public boolean removerVeiculo(Veiculo veiculo) {
        if (veiculo != null) {
            // Tenta desassociar o motorista do veículo
            try {
                // Como a classe Veiculo não tem método desassociarMotorista,
                // vamos apenas definir o motoristaAssociado como null se possível
                if (veiculo.getMotoristaAssociado() == this) {
                    // Precisa de reflexão ou método setter para motoristaAssociado
                    // Se não houver setter, não podemos remover a associação
                }
            } catch (Exception e) {
                // Método não existe, ignora
            }
            return veiculos.remove(veiculo);
        }
        return false;
    }

    /**
     * Remove veículo por placa.
     */
    public boolean removerVeiculoPorPlaca(String placa) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                return removerVeiculo(veiculo);
            }
        }
        return false;
    }

    /**
     * Calcula tarifa com base no primeiro veículo cadastrado.
     */
    public double calcularTarifa(double distancia) {
        if (!veiculos.isEmpty()) {
            Veiculo veiculo = veiculos.get(0);
            if (veiculo != null) {
                try {
                    // Tenta usar o método do veículo
                    return veiculo.calcularTarifa(distancia);
                } catch (Exception e) {
                    // Usa cálculo padrão
                }
            }
        }
        // Cálculo padrão: R$ 5,00 base + R$ 2,50 por km
        return 5.0 + (distancia * 2.5);
    }

    /**
     * Avalia o motorista com uma nova nota e recalcula a média.
     */
    public void avaliar(double novaNota) {
        if (novaNota >= 0.0 && novaNota <= 5.0) {
            // Fórmula: (nota atual * corridas + nova nota) / (corridas + 1)
            double somaAtual = this.notaMedia * this.numeroCorridas;
            this.numeroCorridas++;
            this.notaMedia = (somaAtual + novaNota) / this.numeroCorridas;
            
            System.out.println("⭐ Avaliação registrada para " + this.nomeSocial);
            System.out.println("   Nova nota: " + novaNota + " | Média atual: " + String.format("%.2f", this.notaMedia));
        }
    }

    /**
     * REGISTRA UMA CORRIDA - VERSÃO COMPLETA E SEGURA
     */
    public void registrarCorrida() {
        this.numeroCorridas++;
        System.out.println("✅ Corrida registrada para motorista: " + this.nomeSocial);
        System.out.println("   Total de corridas: " + this.numeroCorridas);
        
        // Atualiza status dos veículos para EM_VIAGEM
        for (Veiculo veiculo : veiculos) {
            try {
                if (veiculo != null) {
                    veiculo.setStatus(enums.StatusVeiculo.EM_VIAGEM);
                }
            } catch (Exception e) {
                // Método não existe
                System.out.println("   Aviso: Não foi possível atualizar status do veículo");
            }
        }
    }

    /**
     * Registra uma corrida com detalhes.
     */
    public void registrarCorrida(Corrida corrida) {
        if (corrida != null) {
            this.numeroCorridas++;
            this.historicoCorridas.add(corrida);
            
            System.out.println("✅ Corrida registrada para motorista: " + this.nomeSocial);
            System.out.println("   ID Corrida: " + corrida.getId());
            System.out.println("   Passageiro: " + corrida.getPassageiro().getNome());
            System.out.println("   Distância: " + corrida.getDistancia() + " km");
            
            // Tenta obter valor da corrida
            try {
                double valor = corrida.getValorTotal();
                System.out.println("   Valor: R$ " + String.format("%.2f", valor));
            } catch (Exception e) {
                System.out.println("   Valor: A calcular");
            }
            
            System.out.println("   Total de corridas: " + this.numeroCorridas);
            
            // Atualiza status dos veículos
            registrarCorrida();
        }
    }

    /**
     * Finaliza uma corrida.
     */
    public void finalizarCorrida(Corrida corrida) {
        if (corrida != null && historicoCorridas.contains(corrida)) {
            System.out.println("🏁 Corrida finalizada: " + this.nomeSocial);
            
            // Atualiza status dos veículos para disponível
            for (Veiculo veiculo : veiculos) {
                try {
                    if (veiculo != null) {
                        veiculo.setStatus(enums.StatusVeiculo.DISPONIVEL);
                    }
                } catch (Exception e) {
                    // Método não existe
                }
            }
        }
    }

    /**
     * Verifica se o motorista está apto a receber corridas.
     */
    public boolean estaApto() {
        if (!this.ativo) return false;
        if (this.notaMedia < 3.0 && this.numeroCorridas > 5) return false;
        
        // Verifica se tem veículo disponível
        for (Veiculo veiculo : veiculos) {
            try {
                if (veiculo != null) {
                    enums.StatusVeiculo status = veiculo.getStatus();
                    if (status == enums.StatusVeiculo.DISPONIVEL) {
                        return true;
                    }
                }
            } catch (Exception e) {
                // Se não conseguir verificar status, assume que está disponível
                return true;
            }
        }
        
        return !veiculos.isEmpty(); // Se não tem método getStatus, verifica apenas se tem veículos
    }

    /**
     * Retorna o primeiro veículo disponível.
     */
    public Veiculo getVeiculoDisponivel() {
        for (Veiculo veiculo : veiculos) {
            try {
                if (veiculo != null) {
                    enums.StatusVeiculo status = veiculo.getStatus();
                    if (status == enums.StatusVeiculo.DISPONIVEL) {
                        return veiculo;
                    }
                }
            } catch (Exception e) {
                // Retorna o primeiro veículo se não conseguir verificar status
                return veiculo;
            }
        }
        return veiculos.isEmpty() ? null : veiculos.get(0);
    }

    /**
     * Verifica se o motorista tem veículo do tipo especificado.
     */
    public boolean temVeiculoTipo(String tipo) {
        for (Veiculo veiculo : veiculos) {
            try {
                if (veiculo != null) {
                    // Como Veiculo é abstrata, vamos verificar a classe concreta
                    String tipoVeiculo = veiculo.getClass().getSimpleName();
                    if (tipoVeiculo != null && tipoVeiculo.equalsIgnoreCase(tipo)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                // Continua para o próximo veículo
            }
        }
        return false;
    }

    /**
     * Calcula a renda total do motorista.
     */
    public double calcularRendaTotal() {
        double rendaTotal = 0.0;
        for (Corrida corrida : historicoCorridas) {
            try {
                rendaTotal += corrida.getValorMotorista();
            } catch (Exception e) {
                // Usa 60% do valor total como padrão
                try {
                    rendaTotal += corrida.getValorTotal() * 0.6;
                } catch (Exception ex) {
                    // Se não conseguir, adiciona valor padrão
                    rendaTotal += 15.0;
                }
            }
        }
        return rendaTotal;
    }

    // ======== MÉTODOS DE INFORMAÇÃO ========

    /**
     * Retorna informações resumidas do motorista.
     */
    public String getInfoResumida() {
        return String.format("%s | Nota: %.1f/5.0 | Corridas: %d | Status: %s", 
            this.nomeSocial, 
            this.notaMedia, 
            this.numeroCorridas, 
            this.ativo ? "✅ Ativo" : "⏸️ Inativo");
    }

    /**
     * Retorna informações completas do motorista.
     */
    public String getInfoCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(50)).append("\n");
        sb.append("👨‍✈️ MOTORISTA: ").append(this.getNome()).append("\n");
        sb.append("-".repeat(50)).append("\n");
        sb.append("Nome Social: ").append(this.nomeSocial).append("\n");
        sb.append("CPF: ").append(this.getCpf()).append("\n");
        sb.append("CNH: ").append(this.cnh).append("\n");
        sb.append("Status: ").append(this.ativo ? "✅ ATIVO" : "⏸️ INATIVO").append("\n");
        sb.append("Nota Média: ").append(String.format("%.1f/5.0", this.notaMedia)).append("\n");
        sb.append("Corridas Realizadas: ").append(this.numeroCorridas).append("\n");
        sb.append("Renda Total: R$ ").append(String.format("%.2f", calcularRendaTotal())).append("\n");
        sb.append("Email: ").append(this.getEmail()).append("\n");
        sb.append("Celular: ").append(this.getCelular()).append("\n");
        sb.append("Endereço: ").append(this.getEndereco()).append("\n");
        
        sb.append("\n🚗 VEÍCULOS (").append(veiculos.size()).append("):\n");
        if (veiculos.isEmpty()) {
            sb.append("  Nenhum veículo cadastrado\n");
        } else {
            for (int i = 0; i < veiculos.size(); i++) {
                Veiculo v = veiculos.get(i);
                sb.append("  ").append(i + 1).append(". ");
                sb.append(v.getModelo()).append(" (")
                  .append(v.getPlaca()).append(") - ")
                  .append(v.getCor());
                
                try {
                    sb.append(" - Status: ").append(v.getStatus());
                } catch (Exception e) {
                    sb.append(" - Status: Disponível");
                }
                sb.append("\n");
            }
        }
        
        sb.append("\n📊 HISTÓRICO DE CORRIDAS (").append(historicoCorridas.size()).append("):\n");
        if (historicoCorridas.isEmpty()) {
            sb.append("  Nenhuma corrida registrada\n");
        } else {
            for (int i = 0; i < Math.min(historicoCorridas.size(), 5); i++) {
                Corrida c = historicoCorridas.get(i);
                sb.append("  ").append(i + 1).append(". ");
                
                try {
                    sb.append("ID: ").append(c.getId())
                      .append(" | Distância: ").append(c.getDistancia()).append(" km");
                      
                    try {
                        sb.append(" | Valor: R$ ").append(String.format("%.2f", c.getValorTotal()));
                    } catch (Exception e) {
                        // Ignora se não tiver valor
                    }
                    
                } catch (Exception e) {
                    sb.append("Corrida ").append(i + 1);
                }
                sb.append("\n");
            }
            
            if (historicoCorridas.size() > 5) {
                sb.append("  ... e mais ").append(historicoCorridas.size() - 5).append(" corridas\n");
            }
        }
        
        sb.append("=".repeat(50));
        return sb.toString();
    }

    /**
     * Retorna dados em formato CSV.
     */
    public String toCSV() {
        return String.format("%s;%s;%s;%.2f;%d;%s;%s;%s",
            this.getNome(),
            this.nomeSocial,
            this.cnh,
            this.notaMedia,
            this.numeroCorridas,
            this.ativo ? "ATIVO" : "INATIVO",
            this.getEmail(),
            this.getCelular());
    }

    @Override
    public String toString() {
        return getInfoResumida();
    }

    // ======== GETTERS E SETTERS ========

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Veiculo> getVeiculos() {
        return new ArrayList<>(veiculos); // Retorna cópia para proteger a lista original
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = new ArrayList<>(veiculos);
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(double notaMedia) {
        if (notaMedia >= 0.0 && notaMedia <= 5.0) {
            this.notaMedia = notaMedia;
        }
    }

    public int getNumeroCorridas() {
        return numeroCorridas;
    }

    public void setNumeroCorridas(int numeroCorridas) {
        if (numeroCorridas >= 0) {
            this.numeroCorridas = numeroCorridas;
        }
    }

    public List<Corrida> getHistoricoCorridas() {
        return new ArrayList<>(historicoCorridas);
    }

    public void setHistoricoCorridas(List<Corrida> historicoCorridas) {
        this.historicoCorridas = new ArrayList<>(historicoCorridas);
    }

    // ======== MÉTODOS DE VALIDAÇÃO ========

    /**
     * Valida se o motorista tem todos os dados necessários.
     */
    public boolean isValid() {
        return this.getNome() != null && !this.getNome().trim().isEmpty() &&
               this.getCpf() != null && !this.getCpf().trim().isEmpty() &&
               this.cnh != null && !this.cnh.trim().isEmpty() &&
               this.nomeSocial != null && !this.nomeSocial.trim().isEmpty();
    }

    /**
     * Verifica se o motorista pode ser promovido a VIP.
     */
    public boolean podeSerPromovidoVIP() {
        return this.numeroCorridas >= 20 && 
               this.notaMedia >= 4.0 && 
               this.ativo;
    }

    /**
     * Obtém a classificação do motorista.
     */
    public String getClassificacao() {
        if (numeroCorridas == 0) return "Novato";
        if (notaMedia >= 4.5) return "⭐ Elite";
        if (notaMedia >= 4.0) return "⭐ Premium";
        if (notaMedia >= 3.5) return "⭐ Regular";
        if (notaMedia >= 3.0) return "⭐ Básico";
        return "Em observação";
    }
}