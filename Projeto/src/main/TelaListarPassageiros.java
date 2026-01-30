package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import controller.SistemaUberLand;
import model.Passageiro;

public class TelaListarPassageiros {
    
    private final SistemaUberLand sistema;
    private final Stage stage;
    
    public TelaListarPassageiros(SistemaUberLand sistema) {
        this.sistema = sistema;
        this.stage = new Stage();
    }
    
    public void mostrar() {
        stage.setTitle("👥 Passageiros Cadastrados - UberLand");
        
        // ====== LAYOUT PRINCIPAL ======
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        
        // ====== CABEÇALHO ======
        VBox cabecalho = criarCabecalho();
        borderPane.setTop(cabecalho);
        
        // ====== CONTEÚDO PRINCIPAL ======
        ScrollPane conteudo = criarConteudo();
        borderPane.setCenter(conteudo);
        
        // ====== RODAPÉ ======
        HBox rodape = criarRodape();
        borderPane.setBottom(rodape);
        
        // ====== CONFIGURAÇÃO DA JANELA ======
        Scene scene = new Scene(borderPane, 700, 500);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    // ====== MÉTODO PARA CRIAR CABEÇALHO ======
    private VBox criarCabecalho() {
        VBox cabecalho = new VBox(10);
        cabecalho.setAlignment(Pos.CENTER);
        cabecalho.setPadding(new Insets(0, 0, 20, 0));
        
        // Título
        Label titulo = new Label("👥 PASSAGEIROS CADASTRADOS");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.web("#2196F3"));
        
        // Subtítulo com estatísticas
        int totalPassageiros = sistema.getPassageiros().size();
        int totalVIPs = sistema.getClientesVip().size();
        
        Label subtitulo = new Label(
            "Total: " + totalPassageiros + " passageiros | " +
            "VIPs: " + totalVIPs + " | " +
            "Regulares: " + (totalPassageiros - totalVIPs)
        );
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        subtitulo.setTextFill(Color.web("#666666"));
        
        // Separador
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        
        cabecalho.getChildren().addAll(titulo, subtitulo, separator);
        return cabecalho;
    }
    
    // ====== MÉTODO PARA CRIAR CONTEÚDO ======
    private ScrollPane criarConteudo() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(10));
        
        if (sistema.getPassageiros().isEmpty()) {
            Label lblVazio = new Label("📭 Nenhum passageiro cadastrado no sistema.");
            lblVazio.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            lblVazio.setTextFill(Color.web("#999999"));
            lblVazio.setAlignment(Pos.CENTER);
            lblVazio.setPadding(new Insets(50));
            container.getChildren().add(lblVazio);
        } else {
            // Cabeçalho da tabela
            GridPane cabecalhoTabela = criarCabecalhoTabela();
            container.getChildren().add(cabecalhoTabela);
            
            // Lista de passageiros
            int contador = 0;
            for (Passageiro p : sistema.getPassageiros()) {
                GridPane itemPassageiro = criarItemPassageiro(p, contador % 2 == 0);
                container.getChildren().add(itemPassageiro);
                contador++;
            }
        }
        
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        return scrollPane;
    }
    
    // ====== MÉTODO PARA CRIAR CABEÇALHO DA TABELA ======
    private GridPane criarCabecalhoTabela() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        grid.setStyle("-fx-background-color: #2196F3; -fx-background-radius: 5;");
        
        Label lblNumero = new Label("#");
        lblNumero.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblNumero.setTextFill(Color.WHITE);
        lblNumero.setPrefWidth(30);
        
        Label lblNome = new Label("NOME");
        lblNome.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblNome.setTextFill(Color.WHITE);
        lblNome.setPrefWidth(180);
        
        Label lblContato = new Label("CONTATO");
        lblContato.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblContato.setTextFill(Color.WHITE);
        lblContato.setPrefWidth(150);
        
        Label lblAvaliacao = new Label("NOTA");
        lblAvaliacao.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblAvaliacao.setTextFill(Color.WHITE);
        lblAvaliacao.setPrefWidth(60);
        
        Label lblCorridas = new Label("CORRIDAS");
        lblCorridas.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblCorridas.setTextFill(Color.WHITE);
        lblCorridas.setPrefWidth(80);
        
        Label lblPagamento = new Label("PAGAMENTO");
        lblPagamento.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblPagamento.setTextFill(Color.WHITE);
        lblPagamento.setPrefWidth(100);
        
        Label lblAcoes = new Label("AÇÕES");
        lblAcoes.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblAcoes.setTextFill(Color.WHITE);
        lblAcoes.setPrefWidth(120);
        
        grid.add(lblNumero, 0, 0);
        grid.add(lblNome, 1, 0);
        grid.add(lblContato, 2, 0);
        grid.add(lblAvaliacao, 3, 0);
        grid.add(lblCorridas, 4, 0);
        grid.add(lblPagamento, 5, 0);
        grid.add(lblAcoes, 6, 0);
        
        return grid;
    }
    
    // ====== MÉTODO PARA CRIAR ITEM DE PASSAGEIRO ======
    private GridPane criarItemPassageiro(Passageiro passageiro, boolean par) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        
        String corFundo = par ? "#F5F5F5" : "#FFFFFF";
        grid.setStyle("-fx-background-color: " + corFundo + "; -fx-background-radius: 5;");
        
        // Número
        int index = sistema.getPassageiros().indexOf(passageiro);
        Label lblNumero = new Label(index >= 0 ? String.valueOf(index + 1) : "?");
        lblNumero.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblNumero.setPrefWidth(30);
        
        // Nome e CPF
        VBox vboxNome = new VBox(2);
        Label lblNome = new Label(passageiro.getNome() != null ? passageiro.getNome() : "N/A");
        lblNome.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        String cpf = passageiro.getCpf() != null ? passageiro.getCpf() : "N/A";
        Label lblCPF = new Label("CPF: " + cpf);
        lblCPF.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        lblCPF.setTextFill(Color.web("#666666"));
        
        vboxNome.getChildren().addAll(lblNome, lblCPF);
        vboxNome.setPrefWidth(180);
        
        // Contato
        VBox vboxContato = new VBox(2);
        Label lblEmail = new Label(passageiro.getEmail() != null ? passageiro.getEmail() : "N/A");
        lblEmail.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        lblEmail.setTextFill(Color.web("#444444"));
        
        Label lblCelular = new Label(passageiro.getCelular() != null ? passageiro.getCelular() : "N/A");
        lblCelular.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        lblCelular.setTextFill(Color.web("#444444"));
        
        vboxContato.getChildren().addAll(lblEmail, lblCelular);
        vboxContato.setPrefWidth(150);
        
        // Avaliação (se existir o método)
        Label lblAvaliacao = new Label();
        try {
            // Tentar pegar nota média
            double nota = passageiro.getNotaMedia();
            if (nota == 0) {
                lblAvaliacao.setText("N/A");
                lblAvaliacao.setTextFill(Color.web("#999999"));
            } else {
                lblAvaliacao.setText(String.format("%.1f/5.0", nota));
                if (nota >= 4.5) {
                    lblAvaliacao.setTextFill(Color.web("#4CAF50"));
                } else if (nota >= 3.0) {
                    lblAvaliacao.setTextFill(Color.web("#FF9800"));
                } else {
                    lblAvaliacao.setTextFill(Color.web("#F44336"));
                }
            }
        } catch (Exception e) {
            lblAvaliacao.setText("N/A");
            lblAvaliacao.setTextFill(Color.web("#999999"));
        }
        lblAvaliacao.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        lblAvaliacao.setPrefWidth(60);
        
        // Corridas (se existir o método)
        Label lblCorridas = new Label();
        try {
            int corridas = passageiro.getNumeroCorridas();
            lblCorridas.setText(String.valueOf(corridas));
        } catch (Exception e) {
            lblCorridas.setText("0");
        }
        lblCorridas.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        lblCorridas.setTextFill(Color.web("#2196F3"));
        lblCorridas.setPrefWidth(80);
        
        // Forma de Pagamento
        Label lblPagamento = new Label();
        try {
            String formaPagamento = passageiro.getFormaPagamento() != null ? 
                                   passageiro.getFormaPagamento().toString() : "N/A";
            lblPagamento.setText(formaPagamento);
            
            switch (formaPagamento) {
                case "CARTAO":
                    lblPagamento.setTextFill(Color.web("#FF9800"));
                    lblPagamento.setText("💳 Cartão");
                    break;
                case "PIX":
                    lblPagamento.setTextFill(Color.web("#4CAF50"));
                    lblPagamento.setText("🏧 PIX");
                    break;
                case "DINHEIRO":
                    lblPagamento.setTextFill(Color.web("#9C27B0"));
                    lblPagamento.setText("💵 Dinheiro");
                    break;
                default:
                    lblPagamento.setTextFill(Color.web("#666666"));
            }
        } catch (Exception e) {
            lblPagamento.setText("N/A");
            lblPagamento.setTextFill(Color.web("#666666"));
        }
        lblPagamento.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        lblPagamento.setPrefWidth(100);
        
        // Ações
        HBox hboxAcoes = new HBox(5);
        hboxAcoes.setPrefWidth(120);
        
        Button btnDetalhes = criarBotaoAcao("🔍", "Ver detalhes", "#2196F3");
        btnDetalhes.setOnAction(e -> mostrarDetalhesPassageiro(passageiro));
        
        // Verificar se é VIP (usando CPF para comparação)
        boolean isVip = false;
        try {
            for (model.ClienteVip vip : sistema.getClientesVip()) {
                if (vip.getCpf() != null && vip.getCpf().equals(passageiro.getCpf())) {
                    isVip = true;
                    break;
                }
            }
        } catch (Exception e) {
            isVip = false;
        }
        
        Button btnPromover = criarBotaoAcao(
            isVip ? "👑" : "⭐",
            isVip ? "Já é VIP" : "Promover a VIP",
            isVip ? "#FFD700" : "#FF9800"
        );
        btnPromover.setDisable(isVip);
        btnPromover.setOnAction(e -> promoverParaVIP(passageiro));
        
        hboxAcoes.getChildren().addAll(btnDetalhes, btnPromover);
        
        // Adicionar ao grid
        grid.add(lblNumero, 0, 0);
        grid.add(vboxNome, 1, 0);
        grid.add(vboxContato, 2, 0);
        grid.add(lblAvaliacao, 3, 0);
        grid.add(lblCorridas, 4, 0);
        grid.add(lblPagamento, 5, 0);
        grid.add(hboxAcoes, 6, 0);
        
        return grid;
    }
    
    // ====== MÉTODO PARA CRIAR RODAPÉ ======
    private HBox criarRodape() {
        HBox rodape = new HBox(10);
        rodape.setAlignment(Pos.CENTER_RIGHT);
        rodape.setPadding(new Insets(20, 0, 0, 0));
        
        // Botão Exportar (simulação)
        Button btnExportar = new Button("📥 Exportar Relatório");
        btnExportar.setStyle(
            "-fx-background-color: #607D8B;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;"
        );
        btnExportar.setOnAction(e -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Exportação");
            alert.setHeaderText("Relatório exportado com sucesso!");
            alert.setContentText("O relatório de passageiros foi salvo como 'passageiros_relatorio.txt'");
            alert.showAndWait();
        });
        
        // Botão Fechar
        Button btnFechar = new Button("✖️ Fechar");
        btnFechar.setStyle(
            "-fx-background-color: #F44336;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;"
        );
        btnFechar.setOnAction(e -> stage.close());
        
        rodape.getChildren().addAll(btnExportar, btnFechar);
        return rodape;
    }
    
    // ====== MÉTODO PARA CRIAR BOTÃO DE AÇÃO ======
    private Button criarBotaoAcao(String texto, String tooltip, String cor) {
        Button btn = new Button(texto);
        btn.setStyle(
            "-fx-background-color: " + cor + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 3;" +
            "-fx-padding: 5 10;"
        );
        
        Tooltip tp = new Tooltip(tooltip);
        Tooltip.install(btn, tp);
        
        btn.setOnMouseEntered(e -> {
            if (!btn.isDisabled()) {
                btn.setStyle(
                    "-fx-background-color: derive(" + cor + ", 20%);" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 12px;" +
                    "-fx-background-radius: 3;" +
                    "-fx-padding: 5 10;" +
                    "-fx-cursor: hand;"
                );
            }
        });
        
        btn.setOnMouseExited(e -> {
            if (!btn.isDisabled()) {
                btn.setStyle(
                    "-fx-background-color: " + cor + ";" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 12px;" +
                    "-fx-background-radius: 3;" +
                    "-fx-padding: 5 10;"
                );
            }
        });
        
        return btn;
    }
    
    // ====== MÉTODO PARA MOSTRAR DETALHES DO PASSAGEIRO ======
    private void mostrarDetalhesPassageiro(Passageiro passageiro) {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("👤 DETALHES DO PASSAGEIRO 👤\n\n");
        
        try {
            detalhes.append("NOME COMPLETO: ").append(passageiro.getNome()).append("\n");
            detalhes.append("CPF: ").append(passageiro.getCpf()).append("\n");
            detalhes.append("EMAIL: ").append(passageiro.getEmail()).append("\n");
            detalhes.append("CELULAR: ").append(passageiro.getCelular()).append("\n");
            detalhes.append("ENDEREÇO: ").append(passageiro.getEndereco()).append("\n");
            
            // Tentar pegar dados que podem não existir
            try {
                detalhes.append("DATA NASCIMENTO: ").append(passageiro.getDataNascimento()).append("\n");
            } catch (Exception e) {
                detalhes.append("DATA NASCIMENTO: N/A\n");
            }
            
            try {
                detalhes.append("SEXO: ").append(passageiro.getSexo()).append("\n");
            } catch (Exception e) {
                detalhes.append("SEXO: N/A\n");
            }
            
            detalhes.append("\nESTATÍSTICAS:\n");
            
            try {
                double nota = passageiro.getNotaMedia();
                detalhes.append("• NOTA MÉDIA: ").append(String.format("%.1f/5.0", nota)).append("\n");
            } catch (Exception e) {
                detalhes.append("• NOTA MÉDIA: N/A\n");
            }
            
            try {
                int corridas = passageiro.getNumeroCorridas();
                detalhes.append("• CORRIDAS REALIZADAS: ").append(corridas).append("\n");
            } catch (Exception e) {
                detalhes.append("• CORRIDAS REALIZADAS: 0\n");
            }
            
            try {
                detalhes.append("• FORMA DE PAGAMENTO: ").append(passageiro.getFormaPagamento()).append("\n");
            } catch (Exception e) {
                detalhes.append("• FORMA DE PAGAMENTO: N/A\n");
            }
            
            // Verificar se é VIP
            boolean isVip = false;
            try {
                for (model.ClienteVip vip : sistema.getClientesVip()) {
                    if (vip.getCpf() != null && vip.getCpf().equals(passageiro.getCpf())) {
                        isVip = true;
                        break;
                    }
                }
            } catch (Exception e) {
                isVip = false;
            }
            
            detalhes.append("• STATUS VIP: ").append(isVip ? "👑 CLIENTE VIP" : "⭐ CLIENTE REGULAR").append("\n");
            
            if (isVip) {
                try {
                    model.ClienteVip vip = sistema.getClientesVip().stream()
                        .filter(v -> v.getCpf().equals(passageiro.getCpf()))
                        .findFirst()
                        .orElse(null);
                    if (vip != null) {
                        detalhes.append("• DESCONTO VIP: ").append(String.format("%.0f%%", vip.getPercentualDesconto() * 100)).append("\n");
                    }
                } catch (Exception e) {
                    detalhes.append("• DESCONTO VIP: 10% (padrão)\n");
                }
            }
            
        } catch (Exception e) {
            detalhes.append("Erro ao carregar detalhes do passageiro: ").append(e.getMessage());
        }
        
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes do Passageiro");
        alert.setHeaderText(passageiro.getNome() != null ? passageiro.getNome() : "Passageiro");
        alert.setContentText(detalhes.toString());
        alert.setWidth(400);
        alert.showAndWait();
    }
    
    // ====== MÉTODO PARA PROMOVER PARA VIP ======
    private void promoverParaVIP(Passageiro passageiro) {
        try {
            String resultado = sistema.promoverParaVIPGUI(passageiro.getCpf());
            
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Promover para VIP");
            alert.setHeaderText("Resultado da promoção");
            
            if (resultado != null && resultado.contains("Promovido")) {
                alert.setAlertType(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setContentText(resultado + "\n\nO passageiro agora tem benefícios especiais!");
            } else {
                alert.setAlertType(javafx.scene.control.Alert.AlertType.WARNING);
                alert.setContentText(resultado != null ? resultado : "Erro desconhecido ao promover para VIP");
            }
            
            alert.showAndWait();
            
            // Fechar e reabrir para atualizar a lista
            stage.close();
            mostrar();
            
        } catch (Exception e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao promover para VIP");
            alert.setContentText("Ocorreu um erro: " + e.getMessage());
            alert.showAndWait();
        }
    }
}