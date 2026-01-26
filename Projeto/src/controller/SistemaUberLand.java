package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import enums.FormaPagamento;
import model.Corrida;
import model.Motorista;
import model.Passageiro;
import model.UberX;
import model.Veiculo;
import util.ValidacaoCPF;

public class SistemaUberLand {

    private final List<Passageiro> passageiros;
    private final List<Motorista> motoristas;
    private final List<Corrida> corridas;

    public SistemaUberLand() {
        this.passageiros = new ArrayList<>();
        this.motoristas = new ArrayList<>();
        this.corridas = new ArrayList<>();
    }

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

        Motorista motorista = new Motorista(
                nome, cpf, dataNascimento, email, sexo, celular, endereco, cnh
        );

        motoristas.add(motorista);

        System.out.println(" Motorista cadastrado com sucesso!");
    }

    public void listarPassageiros() {
        System.out.println("=== Lista de Passageiros ===");
        if (passageiros.isEmpty()) {
            System.out.println("Nenhum passageiro cadastrado.");
            return;
        }

        for (Passageiro p : passageiros) {
            System.out.println("- " + p.getNome() + " | CPF: " + p.getCpf());
        }
    }

    public void listarMotoristas() {
        System.out.println("=== Lista de Motoristas ===");
        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista cadastrado.");
            return;
        }

        for (Motorista m : motoristas) {
            System.out.println("- " + m.getNome() + " | CNH: " + m.getCnh());
        }
    }

    public void solicitarCorrida(Scanner sc) {
        System.out.println("=== Solicitar Corrida ===");

        if (passageiros.isEmpty() || motoristas.isEmpty()) {
            System.out.println("Cadastre ao menos 1 passageiro e 1 motorista antes.");
            return;
        }

        Passageiro passageiro = passageiros.get(0);
        Motorista motorista = motoristas.get(0);

        System.out.print("Distância (km): ");
        double distancia = lerDouble(sc);

        // Veículo padrão (exemplo)
        Veiculo veiculo = new UberX("ABC-1234", "Gol", 4, true);
        motorista.setVeiculo(veiculo);

        Corrida corrida = new Corrida(passageiro, motorista, veiculo, distancia);
        corridas.add(corrida);

        System.out.println(" Corrida registrada!");
        System.out.println("Passageiro: " + passageiro.getNome());
        System.out.println("Motorista: " + motorista.getNome());
        System.out.println("Valor: R$ " + corrida.getValor());
    }

    private double lerDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.println("Digite um número válido:");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }
    
    // ADICIONE ESTES MÉTODOS PARA A INTERFACE GRÁFICA:

    public List<Passageiro> getPassageiros() {
        return new ArrayList<>(passageiros); // Retorna cópia
    }

    public List<Motorista> getMotoristas() {
        return new ArrayList<>(motoristas); // Retorna cópia
    }

    public int getTotalPassageiros() {
        return passageiros.size();
    }

    public int getTotalMotoristas() {
        return motoristas.size();
    }

    public int getTotalCorridas() {
        return corridas.size();
    }

    // Versões sem Scanner para a interface gráfica
    public boolean cadastrarPassageiroGUI(String nome, String cpf, String email, 
                                          String celular, String formaPagamento) {
        try {
            // Validar CPF
            if (!ValidacaoCPF.isCPFValido(cpf)) {
                return false;
            }
            
            // Criar passageiro (simplificado para exemplo)
            Passageiro passageiro = new Passageiro(
                nome, cpf, LocalDate.now(), email, "N/A", celular, "N/A", 
                FormaPagamento.valueOf(formaPagamento)
            );
            
            passageiros.add(passageiro);
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    // Método para cadastrar motorista via GUI
    public boolean cadastrarMotoristaGUI(String nome, String cpf, String email, 
                                         String celular, String cnh) {
        try {
            if (!ValidacaoCPF.isCPFValido(cpf)) {
                return false;
            }
            
            // Para simplificar, uso data atual e valores padrão
            Motorista motorista = new Motorista(
                nome, cpf, LocalDate.now(), email, "N/A", 
                celular, "N/A", cnh
            );
            
            motoristas.add(motorista);
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    // Método para solicitar corrida via GUI
    public boolean solicitarCorridaGUI(double distancia) {
        try {
            if (passageiros.isEmpty() || motoristas.isEmpty()) {
                return false;
            }
            
            // Pega o primeiro passageiro e motorista (simplificado)
            Passageiro passageiro = passageiros.get(0);
            Motorista motorista = motoristas.get(0);
            
            // Veículo padrão
            Veiculo veiculo = new UberX("ABC-1234", "Gol", 4, true);
            motorista.setVeiculo(veiculo);
            
            Corrida corrida = new Corrida(passageiro, motorista, veiculo, distancia);
            corridas.add(corrida);
            
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    // Método para listar informações de corridas
    public List<String> getInfoCorridas() {
        List<String> infos = new ArrayList<>();
        for (Corrida c : corridas) {
            String info = String.format(
                "Corrida: %s -> %s | Distância: %.1f km | Valor: R$ %.2f",
                c.getPassageiro().getNome(),
                c.getMotorista().getNome(),
                c.getDistancia(),
                c.getValor()
            );
            infos.add(info);
        }
        return infos;
    }
}