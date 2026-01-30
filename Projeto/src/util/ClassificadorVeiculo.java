package util;

import model.UberX;
import model.UberComfort;
import model.UberBlack;
import model.Veiculo;

public class ClassificadorVeiculo {
    
    /**
     * Classifica e cria um veículo baseado em suas características.
     * Retorna a categoria (UberX, UberComfort, UberBlack) correta.
     */
    public static Veiculo classificarECriarVeiculo(
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
        
        // Calcula pontuação para determinar categoria
        int pontuacao = calcularPontuacao(
            arCondicionado, direcaoHidraulica, vidroEletrico, travasEletricas,
            airbag, abs, controleTracao, bancosCouro, tetoSolar,
            sistemaSomPremium, rodasLigaLeve, cameraRe, sensorEstacionamento,
            cambioAutomatico, pilotoAutomatico, wifi, carregadorWireless,
            capacidade, ano
        );
        
        // Determina categoria baseado na pontuação
        String categoria = determinarCategoria(pontuacao, capacidade, ano);
        
        // Cria o veículo conforme a categoria
        switch (categoria) {
            case "UBER_BLACK":
                return criarUberBlack(
                    placa, chassi, cor, ano, marca, modelo, capacidade,
                    bancosCouro, tetoSolar, sistemaSomPremium, rodasLigaLeve,
                    cameraRe, sensorEstacionamento, cambioAutomatico,
                    pilotoAutomatico, wifi, carregadorWireless
                );
                
            case "UBER_COMFORT":
                return criarUberComfort(
                    placa, chassi, cor, ano, marca, modelo, capacidade,
                    arCondicionado, direcaoHidraulica, vidroEletrico,
                    travasEletricas, airbag, abs, controleTracao,
                    bancosCouro, tetoSolar
                );
                
            default: // UBER_X
                return criarUberX(
                    placa, chassi, cor, ano, marca, modelo, capacidade,
                    arCondicionado, direcaoHidraulica, vidroEletrico,
                    travasEletricas, airbag, abs
                );
        }
    }
    
    /**
     * Calcula pontuação do veículo baseado em características.
     */
    private static int calcularPontuacao(
            boolean arCondicionado, boolean direcaoHidraulica,
            boolean vidroEletrico, boolean travasEletricas,
            boolean airbag, boolean abs, boolean controleTracao,
            boolean bancosCouro, boolean tetoSolar,
            boolean sistemaSomPremium, boolean rodasLigaLeve,
            boolean cameraRe, boolean sensorEstacionamento,
            boolean cambioAutomatico, boolean pilotoAutomatico,
            boolean wifi, boolean carregadorWireless,
            int capacidade, int ano) {
        
        int pontuacao = 0;
        
        // Características básicas (1 ponto cada)
        if (arCondicionado) pontuacao += 1;
        if (direcaoHidraulica) pontuacao += 1;
        if (vidroEletrico) pontuacao += 1;
        if (travasEletricas) pontuacao += 1;
        
        // Segurança (2 pontos cada)
        if (airbag) pontuacao += 2;
        if (abs) pontuacao += 2;
        if (controleTracao) pontuacao += 2;
        
        // Conforto (3 pontos cada)
        if (bancosCouro) pontuacao += 3;
        if (tetoSolar) pontuacao += 3;
        if (sistemaSomPremium) pontuacao += 3;
        
        // Tecnologia (4 pontos cada)
        if (cameraRe) pontuacao += 4;
        if (sensorEstacionamento) pontuacao += 4;
        if (cambioAutomatico) pontuacao += 4;
        if (pilotoAutomatico) pontuacao += 4;
        if (wifi) pontuacao += 4;
        if (carregadorWireless) pontuacao += 4;
        if (rodasLigaLeve) pontuacao += 4;
        
        // Capacidade (pontos extras para veículos maiores)
        if (capacidade >= 6) pontuacao += 5;
        else if (capacidade >= 4) pontuacao += 2;
        
        // Idade do veículo (veículos mais novos ganham mais pontos)
        int anoAtual = java.time.Year.now().getValue();
        int idade = anoAtual - ano;
        
        if (idade <= 2) pontuacao += 10;
        else if (idade <= 5) pontuacao += 5;
        else if (idade <= 10) pontuacao += 2;
        
        return pontuacao;
    }
    
    /**
     * Determina a categoria do veículo.
     */
    private static String determinarCategoria(int pontuacao, int capacidade, int ano) {
        // UberBlack: Alta pontuação, veículos premium
        if (pontuacao >= 25 && capacidade >= 4 && ano >= 2018) {
            return "UBER_BLACK";
        }
        
        // UberComfort: Pontuação média, bom conforto
        if (pontuacao >= 15) {
            return "UBER_COMFORT";
        }
        
        // UberX: Básico/Standard
        return "UBER_X";
    }
    
    /**
     * Cria um veículo UberX.
     */
    private static UberX criarUberX(
            String placa, String chassi, String cor, int ano, 
            String marca, String modelo, int capacidade,
            boolean arCondicionado, boolean direcaoHidraulica,
            boolean vidroEletrico, boolean travasEletricas,
            boolean airbag, boolean abs) {
        
        // Para UberX, consideramos apenas conforto básico
        boolean confortoBasico = arCondicionado || direcaoHidraulica;
        
        return new UberX(
            placa, chassi, cor, ano, marca, modelo, capacidade,
            arCondicionado, confortoBasico
        );
    }
    
    /**
     * Cria um veículo UberComfort.
     */
    private static UberComfort criarUberComfort(
            String placa, String chassi, String cor, int ano, 
            String marca, String modelo, int capacidade,
            boolean arCondicionado, boolean direcaoHidraulica,
            boolean vidroEletrico, boolean travasEletricas,
            boolean airbag, boolean abs, boolean controleTracao,
            boolean bancosCouro, boolean tetoSolar) {
        
        // Para UberComfort, ar condicionado dual zone é padrão se tiver ar condicionado
        boolean arCondicionadoDualZone = arCondicionado;
        boolean espacoExtra = capacidade >= 5;
        boolean bancoReclinavel = bancosCouro; // Assumimos que bancos de couro são reclináveis
        
        return new UberComfort(
            placa, chassi, cor, ano, marca, modelo, capacidade,
            espacoExtra, bancoReclinavel, arCondicionadoDualZone
        );
    }
    
    /**
     * Cria um veículo UberBlack.
     */
    private static UberBlack criarUberBlack(
            String placa, String chassi, String cor, int ano, 
            String marca, String modelo, int capacidade,
            boolean bancosCouro, boolean tetoSolar,
            boolean sistemaSomPremium, boolean rodasLigaLeve,
            boolean cameraRe, boolean sensorEstacionamento,
            boolean cambioAutomatico, boolean pilotoAutomatico,
            boolean wifi, boolean carregadorWireless) {
        
        boolean interiorPremium = bancosCouro && sistemaSomPremium;
        int numeroMalas = capacidade >= 6 ? 3 : (capacidade >= 4 ? 2 : 1);
        
        return new UberBlack(
            placa, chassi, cor, ano, marca, modelo, capacidade,
            interiorPremium, rodasLigaLeve, numeroMalas
        );
    }
    
    /**
     * Classifica um veículo existente.
     */
    public static String classificarVeiculo(Veiculo veiculo) {
        // Simples classificação baseada na classe do veículo
        if (veiculo instanceof UberBlack) {
            return "UBER_BLACK";
        } else if (veiculo instanceof UberComfort) {
            return "UBER_COMFORT";
        } else if (veiculo instanceof UberX) {
            return "UBER_X";
        } else {
            return "DESCONHECIDO";
        }
    }
    
    /**
     * Retorna emoji para a categoria.
     */
    public static String getEmojiCategoria(String categoria) {
        switch (categoria) {
            case "UBER_BLACK": return "⚫";
            case "UBER_COMFORT": return "🔵";
            case "UBER_X": return "🟢";
            default: return "❓";
        }
    }
    
    /**
     * Retorna nome amigável da categoria.
     */
    public static String getNomeCategoria(String categoria) {
        switch (categoria) {
            case "UBER_BLACK": return "Uber Black";
            case "UBER_COMFORT": return "Uber Comfort";
            case "UBER_X": return "Uber X";
            default: return "Categoria Desconhecida";
        }
    }
    
    /**
     * Retorna descrição da categoria.
     */
    public static String getDescricaoCategoria(String categoria) {
        switch (categoria) {
            case "UBER_BLACK":
                return "Veículos premium de luxo com motorista profissional. Ideal para ocasiões especiais.";
            case "UBER_COMFORT":
                return "Veículos espaçosos e confortáveis para viagens mais longas ou grupos pequenos.";
            case "UBER_X":
                return "Opção econômica e prática para o dia a dia. Carros compactos e eficientes.";
            default:
                return "Categoria não definida.";
        }
    }
    
    /**
     * Retorna tarifa base por km para cada categoria.
     */
    public static double getTarifaBasePorKm(String categoria) {
        switch (categoria) {
            case "UBER_BLACK": return 4.0;
            case "UBER_COMFORT": return 2.8;
            case "UBER_X": return 2.2;
            default: return 2.0;
        }
    }
    
    /**
     * Retorna tarifa mínima para cada categoria.
     */
    public static double getTarifaMinima(String categoria) {
        switch (categoria) {
            case "UBER_BLACK": return 12.0;
            case "UBER_COMFORT": return 8.0;
            case "UBER_X": return 6.0;
            default: return 5.0;
        }
    }
}