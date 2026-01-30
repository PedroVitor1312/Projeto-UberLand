package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import controller.SistemaUberLand;
import model.Motorista;

public class MainApp extends Application {
    
    private SistemaUberLand sistema = new SistemaUberLand();
    private Label lblStatus;
    private Label lblEstatisticas;
    private Stage primaryStage;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        
        // ====== CONFIGURAÇÃO DA JANELA PRINCIPAL ======
        stage.setTitle("🚗 UBERLAND - Sistema de Transporte");
        
        // ====== LAYOUT PRINCIPAL ======
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        
        // ====== CABEÇALHO ======
        VBox cabecalho = criarCabecalho();
        borderPane.setTop(cabecalho);
        
        // ====== MENU PRINCIPAL ======
        GridPane menu = criarMenuPrincipal();
        borderPane.setCenter(menu);
        
        // ====== RODAPÉ ======
        VBox rodape = criarRodape();
        borderPane.setBottom(rodape);
        
        // ====== CENA E EXIBIÇÃO ======
        Scene scene = new Scene(borderPane, 600, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    // ====== MÉTODO PARA CRIAR CABEÇALHO ======
    private VBox criarCabecalho() {
        VBox cabecalho = new VBox(10);
        cabecalho.setAlignment(Pos.CENTER);
        cabecalho.setPadding(new Insets(0, 0, 20, 0));
        
        // Título principal
        Label titulo = new Label("🚗 UBERLAND");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titulo.setTextFill(Color.web("#2E7D32"));
        
        // Subtítulo
        Label subtitulo = new Label("Sistema de Transporte sob Demanda");
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        subtitulo.setTextFill(Color.web("#666666"));
        
        // Separador
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        
        cabecalho.getChildren().addAll(titulo, subtitulo, separator);
        return cabecalho;
    }
    
    // ====== MÉTODO PARA CRIAR MENU PRINCIPAL ======
    private GridPane criarMenuPrincipal() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        
        // ====== BOTÕES PRINCIPAIS ======
        
        // Cadastros
        Button btnCadastrarPassageiro = criarBotaoMenu("👤 Cadastrar Passageiro", "#2196F3");
        Button btnCadastrarMotorista = criarBotaoMenu("🚗 Cadastrar Motorista", "#FF9800");
        Button btnCadastrarVeiculo = criarBotaoMenu("🚙 Cadastrar Veículo", "#4CAF50");
        
        // Listagens
        Button btnListarPassageiros = criarBotaoMenu("📋 Listar Passageiros", "#4CAF50");
        Button btnListarMotoristas = criarBotaoMenu("📋 Listar Motoristas", "#9C27B0");
        Button btnListarVeiculos = criarBotaoMenu("📋 Listar Veículos", "#009688");
        
        // Corridas
        Button btnSolicitarCorrida = criarBotaoMenu("🚕 Solicitar Corrida", "#F44336");
        
        // VIP e Relatórios
        Button btnClientesVIP = criarBotaoMenu("👑 Clientes VIP", "#FFC107");
        Button btnRelatorios = criarBotaoMenu("📊 Relatórios", "#795548");
        
        // Sair
        Button btnSair = criarBotaoMenu("🚪 Sair", "#607D8B");
        
        // ====== POSICIONAMENTO DOS BOTÕES ======
        
        // Linha 0: Cadastros
        grid.add(btnCadastrarPassageiro, 0, 0);
        grid.add(btnCadastrarMotorista, 1, 0);
        grid.add(btnCadastrarVeiculo, 2, 0);
        
        // Linha 1: Listagens
        grid.add(btnListarPassageiros, 0, 1);
        grid.add(btnListarMotoristas, 1, 1);
        grid.add(btnListarVeiculos, 2, 1);
        
        // Linha 2: Corridas e VIP
        grid.add(btnSolicitarCorrida, 0, 2);
        grid.add(btnClientesVIP, 1, 2);
        grid.add(btnRelatorios, 2, 2);
        
        // Linha 3: Sair (centralizado)
        grid.add(btnSair, 1, 3);
        
        // ====== AÇÕES DOS BOTÕES ======
        
        // TELAS EXISTENTES
        btnCadastrarPassageiro.setOnAction(e -> {
            TelaCadastroPassageiro tela = new TelaCadastroPassageiro(sistema);
            tela.mostrar();
            atualizarEstatisticas();
        });
        
        btnCadastrarMotorista.setOnAction(e -> {
            TelaCadastroMotorista tela = new TelaCadastroMotorista(sistema);
            tela.mostrar();
            atualizarEstatisticas();
        });
        
        btnCadastrarVeiculo.setOnAction(e -> {
            abrirTelaCadastroVeiculo();
        });
        
        btnListarPassageiros.setOnAction(e -> {
            TelaListarPassageiros tela = new TelaListarPassageiros(sistema);
            tela.mostrar();
        });
        
        btnListarMotoristas.setOnAction(e -> {
            TelaListarMotoristas tela = new TelaListarMotoristas(sistema);
            tela.mostrar();
        });
        
        btnListarVeiculos.setOnAction(e -> {
            abrirTelaListarVeiculos();
        });
        
        btnSolicitarCorrida.setOnAction(e -> {
            TelaSolicitarCorrida tela = new TelaSolicitarCorrida(sistema);
            tela.mostrar();
            atualizarEstatisticas();
        });
        
        // TELAS EM DESENVOLVIMENTO
        btnClientesVIP.setOnAction(e -> {
            mostrarAlertaInfo("Funcionalidade em Desenvolvimento", 
                "A tela de Clientes VIP está em desenvolvimento.\n" +
                "Por enquanto, use o sistema de promoção automática.\n\n" +
                "Passageiros com 20+ corridas se tornam VIPs automaticamente!");
        });
        
        btnRelatorios.setOnAction(e -> {
            mostrarRelatorioSimples();
        });
        
        btnSair.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Saída");
            alert.setHeaderText("Deseja realmente sair do sistema?");
            alert.setContentText("Todas as alterações serão salvas automaticamente.");
            
            if (alert.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
                primaryStage.close();
            }
        });
        
        return grid;
    }
    
    // ====== MÉTODO PARA ABRIR TELA DE CADASTRO DE VEÍCULO ======
    private void abrirTelaCadastroVeiculo() {
        if (sistema.getMotoristas().isEmpty()) {
            mostrarAlertaInfo("Cadastre um Motorista Primeiro", 
                "É necessário cadastrar pelo menos um motorista antes de adicionar um veículo.\n\n" +
                "Por favor, cadastre um motorista primeiro.");
            return;
        }
        
        // Mostrar diálogo para selecionar motorista
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Selecionar Motorista");
        alert.setHeaderText("Selecione para qual motorista adicionar o veículo:");
        
        // Criar lista de motoristas
        StringBuilder motoristasList = new StringBuilder();
        int index = 1;
        for (Motorista m : sistema.getMotoristas()) {
            motoristasList.append(index).append(". ").append(m.getNome())
                        .append(" (CNH: ").append(m.getCnh()).append(")\n");
            index++;
        }
        
        alert.setContentText("Motoristas disponíveis:\n\n" + motoristasList.toString() + 
                           "\nDeseja abrir a tela de cadastro de veículo?");
        
        if (alert.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            // Abrir tela de seleção de motorista primeiro
            abrirTelaSelecaoMotorista();
        }
    }
    
    // ====== MÉTODO PARA ABRIR TELA DE SELEÇÃO DE MOTORISTA ======
    private void abrirTelaSelecaoMotorista() {
        Stage stageSelecao = new Stage();
        stageSelecao.setTitle("Selecionar Motorista - UberLand");
        
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f7fa;");
        
        Label titulo = new Label("👨‍✈️ SELECIONE O MOTORISTA");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        titulo.setTextFill(Color.web("#FF9800"));
        
        VBox listaMotoristas = new VBox(10);
        listaMotoristas.setAlignment(Pos.CENTER_LEFT);
        listaMotoristas.setPadding(new Insets(20));
        listaMotoristas.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        if (sistema.getMotoristas().isEmpty()) {
            Label lblVazio = new Label("Nenhum motorista cadastrado.");
            lblVazio.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            listaMotoristas.getChildren().add(lblVazio);
        } else {
            for (Motorista m : sistema.getMotoristas()) {
                HBox itemMotorista = new HBox(10);
                itemMotorista.setAlignment(Pos.CENTER_LEFT);
                itemMotorista.setPadding(new Insets(10));
                itemMotorista.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 5; -fx-cursor: hand;");
                
                Label lblInfo = new Label(m.getNome() + " | CNH: " + m.getCnh() + 
                                         " | Veículos: " + m.getVeiculos().size());
                lblInfo.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
                
                // Tornar clicável
                itemMotorista.setOnMouseClicked(e -> {
                    // Abrir tela de cadastro de veículo para este motorista
                    TelaCadastroVeiculo tela = new TelaCadastroVeiculo(sistema, m);
                    tela.mostrar();
                    stageSelecao.close();
                });
                
                itemMotorista.getChildren().add(lblInfo);
                listaMotoristas.getChildren().add(itemMotorista);
            }
        }
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancelar.setOnAction(e -> stageSelecao.close());
        
        root.getChildren().addAll(titulo, listaMotoristas, btnCancelar);
        
        Scene scene = new Scene(root, 400, 400);
        stageSelecao.setScene(scene);
        stageSelecao.show();
    }
    
    // ====== MÉTODO PARA ABRIR TELA DE LISTAR VEÍCULOS ======
    private void abrirTelaListarVeiculos() {
        Stage stageLista = new Stage();
        stageLista.setTitle("🚙 Veículos Cadastrados - UberLand");
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f7fa;");
        
        // Cabeçalho
        VBox cabecalho = new VBox(10);
        cabecalho.setAlignment(Pos.CENTER);
        
        Label titulo = new Label("🚙 VEÍCULOS CADASTRADOS");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.web("#4CAF50"));
        
        int totalVeiculos = sistema.getVeiculos().size();
        Label subtitulo = new Label("Total: " + totalVeiculos + " veículos cadastrados");
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        subtitulo.setTextFill(Color.web("#666666"));
        
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        
        cabecalho.getChildren().addAll(titulo, subtitulo, separator);
        root.setTop(cabecalho);
        
        // Conteúdo
        VBox conteudo = new VBox(10);
        conteudo.setPadding(new Insets(20));
        
        if (totalVeiculos == 0) {
            Label lblVazio = new Label("📭 Nenhum veículo cadastrado no sistema.");
            lblVazio.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            lblVazio.setTextFill(Color.web("#999999"));
            lblVazio.setAlignment(Pos.CENTER);
            lblVazio.setPadding(new Insets(50));
            conteudo.getChildren().add(lblVazio);
        } else {
            for (model.Veiculo v : sistema.getVeiculos()) {
                VBox itemVeiculo = new VBox(5);
                itemVeiculo.setPadding(new Insets(10));
                itemVeiculo.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: #ddd; -fx-border-radius: 8;");
                
                Label lblInfo = new Label(v.getMarca() + " " + v.getModelo() + " | Placa: " + v.getPlaca());
                lblInfo.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                
                Label lblDetalhes = new Label("Cor: " + v.getCor() + " | Ano: " + v.getAno() + " | Capacidade: " + v.getCapacidade() + " pessoas");
                lblDetalhes.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
                lblDetalhes.setTextFill(Color.web("#666666"));
                
                // Determinar motorista
                String motoristaNome = "Sem motorista associado";
                if (v.getMotoristaAssociado() != null) {
                    motoristaNome = v.getMotoristaAssociado().getNome();
                }
                
                Label lblMotorista = new Label("Motorista: " + motoristaNome);
                lblMotorista.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
                lblMotorista.setTextFill(Color.web("#2196F3"));
                
                itemVeiculo.getChildren().addAll(lblInfo, lblDetalhes, lblMotorista);
                conteudo.getChildren().add(itemVeiculo);
            }
        }
        
        ScrollPane scrollPane = new ScrollPane(conteudo);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        root.setCenter(scrollPane);
        
        // Rodapé
        HBox rodape = new HBox(10);
        rodape.setAlignment(Pos.CENTER_RIGHT);
        rodape.setPadding(new Insets(20, 0, 0, 0));
        
        Button btnFechar = new Button("Fechar");
        btnFechar.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-weight: bold;");
        btnFechar.setOnAction(e -> stageLista.close());
        
        rodape.getChildren().add(btnFechar);
        root.setBottom(rodape);
        
        Scene scene = new Scene(root, 500, 400);
        stageLista.setScene(scene);
        stageLista.show();
    }
    
    // ====== MÉTODO PARA CRIAR RODAPÉ ======
    private VBox criarRodape() {
        VBox rodape = new VBox(10);
        rodape.setAlignment(Pos.CENTER);
        rodape.setPadding(new Insets(20, 0, 0, 0));
        
        // Separador
        Separator separator = new Separator();
        
        // Estatísticas em tempo real
        lblEstatisticas = new Label();
        lblEstatisticas.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        atualizarEstatisticas();
        
        // Status do sistema
        lblStatus = new Label("✅ Sistema operacional - " + getDataHoraAtual());
        lblStatus.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        lblStatus.setTextFill(Color.web("#666666"));
        
        // Informações de desenvolvimento
        Label lblDev = new Label("POO1 - UFU 2025 | Trabalho Final - Sistema UberLand");
        lblDev.setFont(Font.font("Arial", FontWeight.NORMAL, 9));
        lblDev.setTextFill(Color.web("#999999"));
        
        rodape.getChildren().addAll(separator, lblEstatisticas, lblStatus, lblDev);
        return rodape;
    }
    
    // ====== MÉTODO PARA CRIAR BOTÕES ESTILIZADOS ======
    private Button criarBotaoMenu(String texto, String cor) {
        Button btn = new Button(texto);
        btn.setPrefWidth(160);
        btn.setPrefHeight(45);
        btn.setStyle(
            "-fx-background-color: " + cor + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 6;" +
            "-fx-border-radius: 6;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 4, 0, 0, 1);"
        );
        
        // Efeito hover
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: derive(" + cor + ", 15%);" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 6;" +
            "-fx-border-radius: 6;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 6, 0, 0, 2);"
        ));
        
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: " + cor + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 6;" +
            "-fx-border-radius: 6;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 4, 0, 0, 1);"
        ));
        
        return btn;
    }
    
    // ====== MÉTODO PARA ATUALIZAR ESTATÍSTICAS ======
    private void atualizarEstatisticas() {
        int totalPassageiros = sistema.getTotalPassageiros();
        int totalMotoristas = sistema.getTotalMotoristas();
        int totalCorridas = sistema.getTotalCorridas();
        int totalVeiculos = sistema.getVeiculos().size();
        
        lblEstatisticas.setText(
            "📊 ESTATÍSTICAS: " +
            "👤 " + totalPassageiros + " | " +
            "🚗 " + totalMotoristas + " | " +
            "🚙 " + totalVeiculos + " | " +
            "🚕 " + totalCorridas
        );
    }
    
    // ====== MÉTODO PARA MOSTRAR RELATÓRIO SIMPLES ======
    private void mostrarRelatorioSimples() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("📈 RELATÓRIO DO SISTEMA 📈\n\n");
        
        relatorio.append("PASSAGEIROS: ").append(sistema.getTotalPassageiros()).append("\n");
        relatorio.append("MOTORISTAS: ").append(sistema.getTotalMotoristas()).append("\n");
        relatorio.append("VEÍCULOS: ").append(sistema.getVeiculos().size()).append("\n");
        relatorio.append("CORRIDAS: ").append(sistema.getTotalCorridas()).append("\n");
        relatorio.append("CLIENTES VIP: ").append(sistema.getTotalClientesVip()).append("\n");
        
        // Calcular corridas por passageiro (média)
        if (sistema.getTotalPassageiros() > 0) {
            double media = (double) sistema.getTotalCorridas() / sistema.getTotalPassageiros();
            relatorio.append(String.format("MÉDIA DE CORRIDAS/PASSAGEIRO: %.1f\n", media));
        }
        
        // Faturamento
        relatorio.append(String.format("FATURAMENTO UBERLAND: R$ %.2f\n", sistema.getFaturamentoTotal()));
        
        relatorio.append("\nFUNCIONALIDADES IMPLEMENTADAS:\n");
        relatorio.append("✅ Cadastro de passageiros\n");
        relatorio.append("✅ Cadastro de motoristas\n");
        relatorio.append("✅ Cadastro de veículos (classificação automática)\n");
        relatorio.append("✅ Solicitação de corridas\n");
        relatorio.append("✅ Sistema de avaliação\n");
        relatorio.append("✅ Sistema VIP automático\n");
        relatorio.append("✅ Interface gráfica completa\n");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Relatório do Sistema");
        alert.setHeaderText("Estatísticas de Uso");
        alert.setContentText(relatorio.toString());
        alert.setWidth(400);
        alert.showAndWait();
    }
    
    // ====== MÉTODOS AUXILIARES ======
    private String getDataHoraAtual() {
        java.time.LocalDateTime agora = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter formatter = 
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return agora.format(formatter);
    }
    
    private void mostrarAlertaInfo(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}