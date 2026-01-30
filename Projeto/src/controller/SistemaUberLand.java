package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import enums.FormaPagamento;
import model.ClienteVip;
import model.Corrida;
import model.Motorista;
import model.Passageiro;
import model.UberX;
import model.UberComfort;
import model.UberBlack;
import model.Veiculo;
import util.ValidacaoCPF;
import util.ClassificadorVeiculo;

public class SistemaUberLand {

    private final List<Passageiro> passageiros;
    private final List<Motorista> motoristas;
    private final List<Veiculo> veiculos;
    private final List<Corrida> corridas;
    private final List<ClienteVip> clientesVip;
    
    // Passageiro e motorista selecionados para corrida
    private Passageiro passageiroSelecionado;
    private Motorista motoristaSelecionado;

    // Porcentagem fixa para UberLand (Requisito n)
    private static final double PORCENTAGEM_UBERLAND = 0.60; // 60%

    public SistemaUberLand() {
        this.passageiros = new ArrayList<>();
        this.motoristas = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.corridas = new ArrayList<>();
        this.clientesVip = new ArrayList<>();
        this.passageiroSelecionado = null;
        this.motoristaSelecionado = null;
    }

    // ====== MÉTODOS PARA CONSOLE ======
    public void cadastrarPassageiro(Scanner sc) {
        System.out.println("=== Cadastro de Passageiro ===");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        String cpf;
        do {
            System.out.print("CPF: ");
            cpf = sc.nextLine();
            if (!ValidacaoCPF.isCPFValido(cpf)) {
                System.out.println("CPF inválido! Tente novamente.");
            }
        } while (!ValidacaoCPF.isCPFValido(cpf));

        System.out.print("Data de nascimento (dd/MM/yyyy): ");
        String dataStr = sc.nextLine();
        LocalDate dataNascimento = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Sexo: ");
        String sexo = sc.nextLine();

        System.out.print("Celular: ");
        String celular = sc.nextLine();

        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        System.out.print("Forma de pagamento (DINHEIRO, CARTAO, PIX): ");
        FormaPagamento formaPagamento = FormaPagamento.valueOf(sc.nextLine().toUpperCase());

        Passageiro passageiro = new Passageiro(
                nome, cpf, dataNascimento, email, sexo, celular, endereco, formaPagamento
        );

        passageiros.add(passageiro);

        System.out.println(" Passageiro cadastrado com sucesso!");
    }

    public void cadastrarMotorista(Scanner sc) {
        System.out.println("=== Cadastro de Motorista ===");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        String cpf;
        do {
            System.out.print("CPF: ");
            cpf = sc.nextLine();
            if (!ValidacaoCPF.isCPFValido(cpf)) {
                System.out.println("CPF inválido! Tente novamente.");
            }
        } while (!ValidacaoCPF.isCPFValido(cpf));

        System.out.print("Data de nascimento (dd/MM/yyyy): ");
        String dataStr = sc.nextLine();
        LocalDate dataNascimento = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Sexo: ");
        String sexo = sc.nextLine();

        System.out.print("Celular: ");
        String celular = sc.nextLine();

        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        System.out.print("CNH: ");
        String cnh = sc.nextLine();

        // Para console, usar nome como nomeSocial
        Motorista motorista = new Motorista(
                nome, cpf, dataNascimento, email, sexo, celular, endereco, cnh, nome
        );

        motoristas.add(motorista);

        System.out.println(" Motorista cadastrado com sucesso!");
    }

    public void solicitarCorrida(Scanner sc) {
        System.out.println("=== Solicitar Corrida ===");

        if (passageiros.isEmpty() || motoristas.isEmpty()) {
            System.out.println("Cadastre ao menos 1 passageiro e 1 motorista antes.");
            return;
        }

        System.out.print("Selecionar passageiro (número): ");
        for (int i = 0; i < passageiros.size(); i++) {
            System.out.println(i + " - " + passageiros.get(i).getNome());
        }
        int idxPassageiro = lerInteiro(sc);
        if (idxPassageiro < 0 || idxPassageiro >= passageiros.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        System.out.print("Selecionar motorista (número): ");
        for (int i = 0; i < motoristas.size(); i++) {
            System.out.println(i + " - " + motoristas.get(i).getNome());
        }
        int idxMotorista = lerInteiro(sc);
        if (idxMotorista < 0 || idxMotorista >= motoristas.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        Passageiro passageiro = passageiros.get(idxPassageiro);
        Motorista motorista = motoristas.get(idxMotorista);

        System.out.print("Origem: ");
        String origem = sc.nextLine();

        System.out.print("Destino: ");
        String destino = sc.nextLine();

        System.out.print("Distância (km): ");
        double distancia = lerDouble(sc);

        // Veículo padrão se não tiver
        if (motorista.getVeiculos().isEmpty()) {
            UberX veiculo = new UberX("ABC-1234", "Gol", "Prata", 2022, "Volkswagen", "Gol", 4, true, false);
            motorista.adicionarVeiculo(veiculo);
        }

        Veiculo veiculo = motorista.getVeiculos().get(0);
        Corrida corrida = new Corrida(passageiro, motorista, veiculo, distancia);
        corridas.add(corrida);

        System.out.println(" Corrida registrada!");
        System.out.println("Passageiro: " + passageiro.getNome());
        System.out.println("Motorista: " + motorista.getNome());
        System.out.println("Valor: R$ " + corrida.getValor());
    }

    public void listarPassageiros() {
        System.out.println("=== Lista de Passageiros ===");
        if (passageiros.isEmpty()) {
            System.out.println("Nenhum passageiro cadastrado.");
            return;
        }

        for (Passageiro p : passageiros) {
            System.out.println("- " + p.getNome() + " | CPF: " + p.getCpf() + " | Corridas: " + p.getNumeroCorridas());
        }
    }

    public void listarMotoristas() {
        System.out.println("=== Lista de Motoristas ===");
        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista cadastrado.");
            return;
        }

        for (Motorista m : motoristas) {
            System.out.println("- " + m.getNome() + " | CNH: " + m.getCnh() + " | Ativo: " + (m.isAtivo() ? "Sim" : "Não"));
        }
    }

    // ====== MÉTODOS GUI COMPLETOS ======

    // 1. CADASTRO DE PASSAGEIRO (GUI)
    public boolean cadastrarPassageiroGUI(String nome, String cpf, String email,
                                         String celular, String formaPagamentoStr) {
        try {
            // Validar CPF
            if (!ValidacaoCPF.isCPFValido(cpf)) {
                return false;
            }
            
            // Converter string para enum
            FormaPagamento formaPagamento;
            try {
                formaPagamento = FormaPagamento.valueOf(formaPagamentoStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                formaPagamento = FormaPagamento.CARTAO; // Padrão
            }
            
            // Criar passageiro
            Passageiro passageiro = new Passageiro(
                nome,                   // nome
                cpf,                    // cpf
                LocalDate.now(),        // dataNascimento (simplificado)
                email,                  // email
                "N/A",                  // sexo (simplificado)
                celular,                // celular
                "Endereço não informado", // endereco
                formaPagamento          // formaPagamento
            );
            
            passageiros.add(passageiro);
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar passageiro: " + e.getMessage());
            return false;
        }
    }

    // 2. CADASTRO DE MOTORISTA (GUI) - COM NOME SOCIAL
    public boolean cadastrarMotoristaGUI(String nome, String cpf, String email,
                                        String celular, String cnh) {
        try {
            // Validar CPF
            if (!ValidacaoCPF.isCPFValido(cpf)) {
                return false;
            }
            
            // Criar motorista com nomeSocial igual ao nome
            Motorista motorista = new Motorista(
                nome,                   // nome
                cpf,                    // cpf
                LocalDate.now(),        // dataNascimento
                email,                  // email
                "N/A",                  // sexo
                celular,                // celular
                "Endereço não informado", // endereco
                cnh,                    // cnh
                nome                    // nomeSocial (igual ao nome)
            );
            
            motoristas.add(motorista);
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar motorista: " + e.getMessage());
            return false;
        }
    }

    // 3. CADASTRO DE MOTORISTA COMPLETO (GUI) - COM NOME SOCIAL SEPARADO
    public boolean cadastrarMotoristaGUICompleto(String nome, String nomeSocial, String cpf,
                                                String email, String celular, String cnh) {
        try {
            if (!ValidacaoCPF.isCPFValido(cpf)) {
                return false;
            }
            
            Motorista motorista = new Motorista(
                nome,                   // nome
                cpf,                    // cpf
                LocalDate.now(),        // dataNascimento
                email,                  // email
                "N/A",                  // sexo
                celular,                // celular
                "Endereço não informado", // endereco
                cnh,                    // cnh
                nomeSocial              // nomeSocial
            );
            
            motoristas.add(motorista);
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar motorista: " + e.getMessage());
            return false;
        }
    }

    // 4. MÉTODOS PARA SELEÇÃO DE PASSAGEIRO/MOTORISTA
    public void selecionarPassageiro(Passageiro passageiro) {
        this.passageiroSelecionado = passageiro;
    }
    
    public void selecionarMotorista(Motorista motorista) {
        this.motoristaSelecionado = motorista;
    }
    
    public Passageiro getPassageiroSelecionado() {
        return passageiroSelecionado;
    }
    
    public Motorista getMotoristaSelecionado() {
        return motoristaSelecionado;
    }

    // 5. SOLICITAR CORRIDA (GUI) - SIMPLES
    public boolean solicitarCorridaGUI(double distancia) {
        try {
            if (passageiroSelecionado == null || motoristaSelecionado == null) {
                return false;
            }
            
            // Verificar se motorista está ativo
            if (!motoristaSelecionado.isAtivo()) {
                return false;
            }
            
            // Verificar se motorista tem veículo
            if (motoristaSelecionado.getVeiculos().isEmpty()) {
                // Criar um veículo padrão
                UberX veiculoPadrao = new UberX(
                    "ABC-1234",         // placa
                    "9BWZZZ377VT004251", // chassi
                    "Prata",            // cor
                    2022,               // ano
                    "Volkswagen",       // marca
                    "Gol",              // modelo
                    4,                  // capacidade
                    true,               // arCondicionado
                    false               // confortoBasico
                );
                motoristaSelecionado.adicionarVeiculo(veiculoPadrao);
                veiculos.add(veiculoPadrao);
            }
            
            Veiculo veiculo = motoristaSelecionado.getVeiculos().get(0);
            
            // Criar corrida
            Corrida corrida = new Corrida(
                passageiroSelecionado,
                motoristaSelecionado,
                veiculo,
                distancia
            );
            
            corridas.add(corrida);
            
            // Registrar corrida no passageiro
            passageiroSelecionado.registrarCorrida();
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao solicitar corrida: " + e.getMessage());
            return false;
        }
    }

    // 6. SOLICITAR CORRIDA COMPLETA (GUI)
    public boolean solicitarCorridaGUICompleto(String cpfPassageiro, String cpfMotorista, 
                                              String origem, String destino, double distancia) {
        try {
            // Encontrar passageiro
            Passageiro passageiro = null;
            for (Passageiro p : passageiros) {
                if (p.getCpf().equals(cpfPassageiro)) {
                    passageiro = p;
                    break;
                }
            }
            
            if (passageiro == null) {
                return false;
            }
            
            // Encontrar motorista
            Motorista motorista = null;
            for (Motorista m : motoristas) {
                if (m.getCpf().equals(cpfMotorista) && m.isAtivo()) {
                    motorista = m;
                    break;
                }
            }
            
            if (motorista == null) {
                return false;
            }
            
            // Verificar se motorista tem veículo
            if (motorista.getVeiculos().isEmpty()) {
                return false;
            }
            
            Veiculo veiculo = motorista.getVeiculos().get(0);
            
            // Criar corrida - usando construtor existente (sem origem/destino)
            Corrida corrida = new Corrida(
                passageiro,
                motorista,
                veiculo,
                distancia
            );
            
            corridas.add(corrida);
            
            // Registrar corrida
            passageiro.registrarCorrida();
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Erro ao solicitar corrida: " + e.getMessage());
            return false;
        }
    }

    // 7. BUSCAR PASSAGEIRO (GUI)
    public Passageiro buscarPassageiroPorCPF(String cpf) {
        for (Passageiro p : passageiros) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    // 8. BUSCAR MOTORISTA (GUI)
    public Motorista buscarMotoristaPorCPF(String cpf) {
        for (Motorista m : motoristas) {
            if (m.getCpf().equals(cpf)) {
                return m;
            }
        }
        return null;
    }

    // 9. LISTAR PASSAGEIROS (GUI)
    public List<String> getListaPassageirosGUI() {
        List<String> lista = new ArrayList<>();
        for (Passageiro p : passageiros) {
            String info = String.format("%s | CPF: %s | Corridas: %d | Nota: %.1f",
                    p.getNome(), p.getCpf(), p.getNumeroCorridas(), p.getNotaMedia());
            lista.add(info);
        }
        return lista;
    }

    // 10. LISTAR MOTORISTAS (GUI)
    public List<String> getListaMotoristasGUI() {
        List<String> lista = new ArrayList<>();
        for (Motorista m : motoristas) {
            String status = m.isAtivo() ? "✅ Ativo" : "❌ Inativo";
            String info = String.format("%s (%s) | CNH: %s | Nota: %.1f | %s",
                    m.getNome(), m.getNomeSocial(), m.getCnh(), 
                    m.getNotaMedia(), status);
            lista.add(info);
        }
        return lista;
    }

    // 11. LISTAR CORRIDAS (GUI)
    public List<String> getListaCorridasGUI() {
        List<String> lista = new ArrayList<>();
        for (Corrida c : corridas) {
            String info = String.format("%s → %s | R$ %.2f | %s",
                    c.getPassageiro().getNome(), c.getMotorista().getNome(), 
                    c.getValor(), c.getStatus());
            lista.add(info);
        }
        return lista;
    }

    // 12. LISTAR CLIENTES VIP (GUI) - Simplificado
    public List<String> getListaClientesVipGUI() {
        List<String> lista = new ArrayList<>();
        for (ClienteVip vip : clientesVip) {
            String info = String.format("👑 %s | Desconto: %.1f%%",
                    vip.getNome(), vip.getPercentualDesconto() * 100);
            lista.add(info);
        }
        return lista;
    }

    // 13. PROMOVER PARA VIP (GUI) - Simplificado
    public String promoverParaVIPGUI(String cpf) {
        try {
            Passageiro passageiro = buscarPassageiroPorCPF(cpf);
            if (passageiro == null) {
                return "Passageiro não encontrado!";
            }
            
            // Verificar se já é VIP
            for (ClienteVip vip : clientesVip) {
                if (vip.getCpf().equals(cpf)) {
                    return "Já é cliente VIP!";
                }
            }
            
            // Criar cliente VIP com 10% de desconto
            ClienteVip vip = new ClienteVip(passageiro, 0.10);
            clientesVip.add(vip);
            
            return String.format("Promovido a VIP! Desconto: %.1f%%",
                    vip.getPercentualDesconto() * 100);
            
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    // 14. ATIVAR/DESATIVAR MOTORISTA (GUI)
    public String ativarDesativarMotoristaGUI(String cpf, boolean ativar) {
        Motorista motorista = buscarMotoristaPorCPF(cpf);
        if (motorista == null) {
            return "Motorista não encontrado!";
        }
        
        if (ativar) {
            motorista.ativar();
            return "Motorista ativado com sucesso!";
        } else {
            motorista.desativar();
            return "Motorista desativado com sucesso!";
        }
    }

    // 15. FINALIZAR CORRIDA (GUI) - Simplificado
    public String finalizarCorridaGUI(int indexCorrida, double notaMotorista, double notaPassageiro) {
        try {
            if (indexCorrida < 0 || indexCorrida >= corridas.size()) {
                return "Corrida não encontrada!";
            }
            
            Corrida corrida = corridas.get(indexCorrida);
            
            // Registrar avaliações
            if (notaMotorista >= 0 && notaMotorista <= 5) {
                corrida.getMotorista().registrarAvaliacao(notaMotorista);
            }
            
            if (notaPassageiro >= 0 && notaPassageiro <= 5) {
                corrida.getPassageiro().registrarAvaliacao(notaPassageiro);
            }
            
            return String.format("Corrida finalizada! Valor: R$ %.2f",
                    corrida.getValor());
            
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    // 16. CANCELAR CORRIDA (GUI) - Simplificado
    public String cancelarCorridaGUI(int indexCorrida, String quemCancelou) {
        try {
            if (indexCorrida < 0 || indexCorrida >= corridas.size()) {
                return "Corrida não encontrada!";
            }
            
            Corrida corrida = corridas.get(indexCorrida);
            
            if (quemCancelou.equalsIgnoreCase("CLIENTE")) {
                corrida.cancelarAntes();
            } else if (quemCancelou.equalsIgnoreCase("MOTORISTA")) {
                corrida.cancelarDurante();
            } else {
                return "Tipo de cancelamento inválido!";
            }
            
            return "Corrida cancelada com sucesso!";
            
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    // ====== MÉTODOS PARA CADASTRO DE VEÍCULOS COM CLASSIFICAÇÃO AUTOMÁTICA ======

    /**
     * Método para cadastrar veículo para um motorista com classificação automática
     */
    public boolean cadastrarVeiculoParaMotorista(String cpfMotorista, 
            String placa, String chassi, String cor, int ano, 
            String marca, String modelo, int capacidade,
            boolean arCondicionado, boolean direcaoHidraulica,
            boolean vidroEletrico, boolean travasEletricas,
            boolean airbag, boolean abs, boolean controleTracao,
            boolean bancosCouro, boolean tetoSolar,
            boolean sistemaSomPremium, boolean rodasLigaLeve,
            boolean cameraRe, boolean sensorEstacionamento,
            boolean cambioAutomatico, boolean pilotoAutomatico,
            boolean wifi, boolean carregadorWireless) {
        
        try {
            // Encontrar motorista
            Motorista motorista = null;
            for (Motorista m : motoristas) {
                if (m.getCpf().equals(cpfMotorista)) {
                    motorista = m;
                    break;
                }
            }
            
            if (motorista == null) {
                System.out.println("❌ Motorista não encontrado!");
                return false;
            }
            
            // Verificar se placa já existe
            for (Motorista m : motoristas) {
                for (Veiculo v : m.getVeiculos()) {
                    if (v.getPlaca().equalsIgnoreCase(placa)) {
                        System.out.println("❌ Placa já cadastrada no sistema!");
                        return false;
                    }
                }
            }
            
            // Classificar e criar veículo automaticamente
            Veiculo veiculo = ClassificadorVeiculo.classificarECriarVeiculo(
                placa, chassi, cor, ano, marca, modelo, capacidade,
                arCondicionado, direcaoHidraulica, vidroEletrico, travasEletricas,
                airbag, abs, controleTracao, bancosCouro, tetoSolar,
                sistemaSomPremium, rodasLigaLeve, cameraRe, sensorEstacionamento,
                cambioAutomatico, pilotoAutomatico, wifi, carregadorWireless
            );
            
            // Adicionar veículo ao motorista
            motorista.adicionarVeiculo(veiculo);
            veiculos.add(veiculo);
            
            // Determinar categoria
            String categoria = ClassificadorVeiculo.classificarVeiculo(veiculo);
            String emoji = ClassificadorVeiculo.getEmojiCategoria(categoria);
            
            System.out.println("\n✅ VEÍCULO CADASTRADO COM SUCESSO!");
            System.out.println(emoji + " Categoria: " + ClassificadorVeiculo.getNomeCategoria(categoria));
            System.out.println("📋 Placa: " + placa);
            System.out.println("🚗 Modelo: " + marca + " " + modelo);
            System.out.println("👨‍✈️ Motorista: " + motorista.getNome());
            
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao cadastrar veículo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para listar veículos de um motorista
     */
    public List<String> listarVeiculosDoMotorista(String cpfMotorista) {
        List<String> lista = new ArrayList<>();
        
        Motorista motorista = buscarMotoristaPorCPF(cpfMotorista);
        if (motorista == null) {
            lista.add("❌ Motorista não encontrado!");
            return lista;
        }
        
        try {
            if (motorista.getVeiculos().isEmpty()) {
                lista.add("📭 Nenhum veículo cadastrado para este motorista.");
                return lista;
            }
        } catch (Exception e) {
            lista.add("Erro ao acessar veículos do motorista.");
            return lista;
        }
        
        int contador = 1;
        for (Veiculo veiculo : motorista.getVeiculos()) {
            String categoria = ClassificadorVeiculo.classificarVeiculo(veiculo);
            String emoji = ClassificadorVeiculo.getEmojiCategoria(categoria);
            
            String info = String.format("%d. %s %s %s - %s\n" +
                                       "   📍 Placa: %s | Ano: %d | Categoria: %s\n" +
                                       "   👥 Capacidade: %d pessoas",
                contador,
                emoji,
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getPlaca(),
                veiculo.getAno(),
                ClassificadorVeiculo.getNomeCategoria(categoria),
                veiculo.getCapacidade()
            );
            
            lista.add(info);
            contador++;
        }
        
        return lista;
    }

    /**
     * Método para remover veículo de um motorista
     */
    public boolean removerVeiculoDoMotorista(String cpfMotorista, String placa) {
        try {
            Motorista motorista = buscarMotoristaPorCPF(cpfMotorista);
            if (motorista == null) {
                return false;
            }
            
            // Buscar veículo
            Veiculo veiculoRemover = null;
            for (Veiculo v : motorista.getVeiculos()) {
                if (v.getPlaca().equalsIgnoreCase(placa)) {
                    veiculoRemover = v;
                    break;
                }
            }
            
            if (veiculoRemover == null) {
                return false;
            }
            
            // Remover do motorista
            boolean removido = motorista.removerVeiculo(veiculoRemover);
            if (removido) {
                // Remover também da lista geral de veículos
                veiculos.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
                System.out.println("✅ Veículo removido com sucesso!");
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao remover veículo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método para obter informações detalhadas de um veículo
     */
    public String getInfoVeiculo(String placa) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                String categoria = ClassificadorVeiculo.classificarVeiculo(veiculo);
                
                StringBuilder info = new StringBuilder();
                info.append("🚗 INFORMAÇÕES DO VEÍCULO 🚗\n\n");
                info.append("Placa: ").append(veiculo.getPlaca()).append("\n");
                info.append("Marca/Modelo: ").append(veiculo.getMarca()).append(" ").append(veiculo.getModelo()).append("\n");
                info.append("Cor: ").append(veiculo.getCor()).append("\n");
                info.append("Ano: ").append(veiculo.getAno()).append("\n");
                info.append("Capacidade: ").append(veiculo.getCapacidade()).append(" pessoas\n");
                info.append("Categoria: ").append(ClassificadorVeiculo.getNomeCategoria(categoria)).append("\n");
                info.append("Emoji: ").append(ClassificadorVeiculo.getEmojiCategoria(categoria)).append("\n\n");
                info.append("📊 Tarifas:\n");
                info.append("• Tarifa mínima: R$ ").append(String.format("%.2f", ClassificadorVeiculo.getTarifaMinima(categoria))).append("\n");
                info.append("• Por km: R$ ").append(String.format("%.2f", ClassificadorVeiculo.getTarifaBasePorKm(categoria))).append("\n\n");
                info.append(ClassificadorVeiculo.getDescricaoCategoria(categoria));
                
                // Adicionar motorista se houver
                try {
                    if (veiculo.getMotoristaAssociado() != null) {
                        info.append("\n\n👨‍✈️ Motorista: ").append(veiculo.getMotoristaAssociado().getNome());
                    }
                } catch (Exception e) {
                    // Ignora se não conseguir obter motorista
                }
                
                return info.toString();
            }
        }
        
        return "❌ Veículo não encontrado!";
    }

    /**
     * Método para listar todos os veículos do sistema
     */
    public List<String> getListaVeiculosCompleta() {
        List<String> lista = new ArrayList<>();
        
        if (veiculos.isEmpty()) {
            lista.add("📭 Nenhum veículo cadastrado no sistema.");
            return lista;
        }
        
        int contador = 1;
        for (Veiculo veiculo : veiculos) {
            String categoria = ClassificadorVeiculo.classificarVeiculo(veiculo);
            String emoji = ClassificadorVeiculo.getEmojiCategoria(categoria);
            
            String motoristaInfo = "Sem motorista";
            try {
                if (veiculo.getMotoristaAssociado() != null) {
                    motoristaInfo = veiculo.getMotoristaAssociado().getNome();
                }
            } catch (Exception e) {
                // Ignora erro
            }
            
            String info = String.format("%d. %s %s %s (%s)\n" +
                                       "   📍 Placa: %s | Motorista: %s\n" +
                                       "   🏷️  Categoria: %s | 🎨 Cor: %s | 📅 Ano: %d",
                contador,
                emoji,
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getPlaca(),
                motoristaInfo,
                ClassificadorVeiculo.getNomeCategoria(categoria),
                veiculo.getCor(),
                veiculo.getAno()
            );
            
            lista.add(info);
            contador++;
        }
        
        return lista;
    }

    /**
     * Método para verificar se um motorista pode adicionar mais veículos
     */
    public boolean motoristaPodeAdicionarVeiculo(String cpfMotorista) {
        Motorista motorista = buscarMotoristaPorCPF(cpfMotorista);
        if (motorista == null) return false;
        
        // Limite de 3 veículos por motorista
        try {
            return motorista.getVeiculos().size() < 3;
        } catch (Exception e) {
            return true; // Se não conseguir verificar, permite
        }
    }

    // ====== GETTERS PARA ESTATÍSTICAS ======
    public int getTotalPassageiros() {
        return passageiros.size();
    }

    public int getTotalMotoristas() {
        return motoristas.size();
    }

    public int getTotalCorridas() {
        return corridas.size();
    }

    public int getTotalClientesVip() {
        return clientesVip.size();
    }

    public int getTotalVeiculos() {
        return veiculos.size();
    }

    public double getFaturamentoTotal() {
        double total = 0;
        for (Corrida c : corridas) {
            total += c.getValor();
        }
        return total * PORCENTAGEM_UBERLAND; // 60% para UberLand
    }

    public List<Passageiro> getPassageiros() {
        return new ArrayList<>(passageiros);
    }

    public List<Motorista> getMotoristas() {
        return new ArrayList<>(motoristas);
    }

    public List<Veiculo> getVeiculos() {
        return new ArrayList<>(veiculos);
    }

    public List<Corrida> getCorridas() {
        return new ArrayList<>(corridas);
    }

    public List<ClienteVip> getClientesVip() {
        return new ArrayList<>(clientesVip);
    }

    // ====== MÉTODOS UTILITÁRIOS ======
    private int lerInteiro(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Digite um número válido:");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine(); // Consumir newline
        return valor;
    }

    private double lerDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.println("Digite um número válido:");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine(); // Consumir newline
        return valor;
    }
}

