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
import model.Motorista;

public class TelaListarMotoristas {
    
    private final SistemaUberLand sistema;
    private final Stage stage;
    
    public TelaListarMotoristas(SistemaUberLand sistema) {
        this.sistema = sistema;
        this.stage = new Stage();
    }
    
    public void mostrar() {
        stage.setTitle("👨‍✈️ Motoristas Cadastrados - UberLand");
        
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
        Label titulo = new Label("👨‍✈️ MOTORISTAS CADASTRADOS");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.web("#FF9800"));
        
        // Subtítulo com estatísticas
        int totalMotoristas = sistema.getMotoristas().size();
        int ativos = (int) sistema.getMotoristas().stream()
            .filter(Motorista::isAtivo)
            .count();
        
        Label subtitulo = new Label(
            "Total: " + totalMotoristas + " motoristas | " +
            "Ativos: " + ativos + " | " +
            "Inativos: " + (totalMotoristas - ativos)
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
        
        if (sistema.getMotoristas().isEmpty()) {
            Label lblVazio = new Label("📭 Nenhum motorista cadastrado no sistema.");
            lblVazio.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            lblVazio.setTextFill(Color.web("#999999"));
            lblVazio.setAlignment(Pos.CENTER);
            lblVazio.setPadding(new Insets(50));
            container.getChildren().add(lblVazio);
        } else {
            // Cabeçalho da tabela
            GridPane cabecalhoTabela = criarCabecalhoTabela();
            container.getChildren().add(cabecalhoTabela);
            
            // Lista de motoristas
            int contador = 0;
            for (Motorista m : sistema.getMotoristas()) {
                GridPane itemMotorista = criarItemMotorista(m, contador % 2 == 0);
                container.getChildren().add(itemMotorista);
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
        grid.setStyle("-fx-background-color: #FF9800; -fx-background-radius: 5;");
        
        Label lblNumero = new Label("#");
        lblNumero.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblNumero.setTextFill(Color.WHITE);
        lblNumero.setPrefWidth(30);
        
        Label lblNome = new Label("NOME / NOME SOCIAL");
        lblNome.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblNome.setTextFill(Color.WHITE);
        lblNome.setPrefWidth(200);
        
        Label lblStatus = new Label("STATUS");
        lblStatus.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblStatus.setTextFill(Color.WHITE);
        lblStatus.setPrefWidth(80);
        
        Label lblAvaliacao = new Label("NOTA");
        lblAvaliacao.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblAvaliacao.setTextFill(Color.WHITE);
        lblAvaliacao.setPrefWidth(60);
        
        Label lblCorridas = new Label("CORRIDAS");
        lblCorridas.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblCorridas.setTextFill(Color.WHITE);
        lblCorridas.setPrefWidth(80);
        
        Label lblAcoes = new Label("AÇÕES");
        lblAcoes.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblAcoes.setTextFill(Color.WHITE);
        lblAcoes.setPrefWidth(120);
        
        grid.add(lblNumero, 0, 0);
        grid.add(lblNome, 1, 0);
        grid.add(lblStatus, 2, 0);
        grid.add(lblAvaliacao, 3, 0);
        grid.add(lblCorridas, 4, 0);
        grid.add(lblAcoes, 5, 0);
        
        return grid;
    }
    
    // ====== MÉTODO PARA CRIAR ITEM DE MOTORISTA ======
    private GridPane criarItemMotorista(Motorista motorista, boolean par) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        
        String corFundo = par ? "#F5F5F5" : "#FFFFFF";
        grid.setStyle("-fx-background-color: " + corFundo + "; -fx-background-radius: 5;");
        
        // Número
        Label lblNumero = new Label(String.valueOf(sistema.getMotoristas().indexOf(motorista) + 1));
        lblNumero.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblNumero.setPrefWidth(30);
        
        // Nome e informações
        VBox vboxNome = new VBox(2);
        Label lblNome = new Label(motorista.getNome());
        lblNome.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        Label lblNomeSocial = new Label("Social: " + motorista.getNomeSocial());
        lblNomeSocial.setFont(Font.font("Arial", FontWeight.NORMAL, 10));
        lblNomeSocial.setTextFill(Color.web("#666666"));
        
        Label lblInfo = new Label(
            "CNH: " + motorista.getCnh() + " | " +
            "Cel: " + motorista.getCelular()
        );
        lblInfo.setFont(Font.font("Arial", FontWeight.NORMAL, 9));
        lblInfo.setTextFill(Color.web("#888888"));
        
        vboxNome.getChildren().addAll(lblNome, lblNomeSocial, lblInfo);
        vboxNome.setPrefWidth(200);
        
        // Status
        Label lblStatus = new Label();
        if (motorista.isAtivo()) {
            lblStatus.setText("✅ ATIVO");
            lblStatus.setTextFill(Color.web("#4CAF50"));
        } else {
            lblStatus.setText("❌ INATIVO");
            lblStatus.setTextFill(Color.web("#F44336"));
        }
        lblStatus.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        lblStatus.setPrefWidth(80);
        
        // Avaliação
        Label lblAvaliacao = new Label();
        double nota = motorista.getNotaMedia();
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
        lblAvaliacao.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        lblAvaliacao.setPrefWidth(60);
        
        // Corridas
        Label lblCorridas = new Label(String.valueOf(motorista.getNumeroCorridas()));
        lblCorridas.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        lblCorridas.setTextFill(Color.web("#2196F3"));
        lblCorridas.setPrefWidth(80);
        
        // Ações
        HBox hboxAcoes = new HBox(5);
        hboxAcoes.setPrefWidth(120);
        
        Button btnDetalhes = criarBotaoAcao("🔍", "Ver detalhes", "#2196F3");
        btnDetalhes.setOnAction(e -> mostrarDetalhesMotorista(motorista));
        
        Button btnAtivarDesativar = criarBotaoAcao(
            motorista.isAtivo() ? "⏸️" : "▶️",
            motorista.isAtivo() ? "Desativar" : "Ativar",
            motorista.isAtivo() ? "#FF9800" : "#4CAF50"
        );
        btnAtivarDesativar.setOnAction(e -> {
            // Em sistema real, chamaria sistema.ativarDesativarMotorista()
            mostrarMensagemAcao(motorista);
        });
        
        hboxAcoes.getChildren().addAll(btnDetalhes, btnAtivarDesativar);
        
        // Adicionar ao grid
        grid.add(lblNumero, 0, 0);
        grid.add(vboxNome, 1, 0);
        grid.add(lblStatus, 2, 0);
        grid.add(lblAvaliacao, 3, 0);
        grid.add(lblCorridas, 4, 0);
        grid.add(hboxAcoes, 5, 0);
        
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
            alert.setContentText("O relatório de motoristas foi salvo como 'motoristas_relatorio.txt'");
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
        
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: derive(" + cor + ", 20%);" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 3;" +
            "-fx-padding: 5 10;" +
            "-fx-cursor: hand;"
        ));
        
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: " + cor + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;" +
            "-fx-background-radius: 3;" +
            "-fx-padding: 5 10;"
        ));
        
        return btn;
    }
    
    // ====== MÉTODO PARA MOSTRAR DETALHES DO MOTORISTA ======
    private void mostrarDetalhesMotorista(Motorista motorista) {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("👨‍✈️ DETALHES DO MOTORISTA 👨‍✈️\n\n");
        
        detalhes.append("NOME COMPLETO: ").append(motorista.getNome()).append("\n");
        detalhes.append("NOME SOCIAL: ").append(motorista.getNomeSocial()).append("\n");
        detalhes.append("CPF: ").append(motorista.getCpf()).append("\n");
        detalhes.append("CNH: ").append(motorista.getCnh()).append("\n");
        detalhes.append("EMAIL: ").append(motorista.getEmail()).append("\n");
        detalhes.append("CELULAR: ").append(motorista.getCelular()).append("\n");
        detalhes.append("ENDEREÇO: ").append(motorista.getEndereco()).append("\n\n");
        
        detalhes.append("ESTATÍSTICAS:\n");
        detalhes.append("• STATUS: ").append(motorista.isAtivo() ? "✅ ATIVO" : "❌ INATIVO").append("\n");
        detalhes.append("• NOTA MÉDIA: ").append(String.format("%.1f/5.0", motorista.getNotaMedia())).append("\n");
        detalhes.append("• CORRIDAS REALIZADAS: ").append(motorista.getNumeroCorridas()).append("\n");
        
        detalhes.append("• VEÍCULOS ASSOCIADOS: ");
        if (motorista.getVeiculos().isEmpty()) {
            detalhes.append("Nenhum veículo cadastrado\n");
        } else {
            detalhes.append(motorista.getVeiculos().size()).append(" veículo(s)\n");
            for (model.Veiculo v : motorista.getVeiculos()) {
                detalhes.append("  - ").append(v.getModelo())
                       .append(" (").append(v.getPlaca()).append(")\n");
            }
        }
        
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Detalhes do Motorista");
        alert.setHeaderText(motorista.getNome() + " (" + motorista.getNomeSocial() + ")");
        alert.setContentText(detalhes.toString());
        alert.setWidth(400);
        alert.showAndWait();
    }
    
    // ====== MÉTODO PARA MOSTRAR MENSAGEM DE AÇÃO ======
    private void mostrarMensagemAcao(Motorista motorista) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Ação do Motorista");
        alert.setHeaderText("Funcionalidade em desenvolvimento");
        alert.setContentText(
            "Ação: " + (motorista.isAtivo() ? "Desativar" : "Ativar") + " motorista\n" +
            "Motorista: " + motorista.getNome() + "\n" +
            "Status atual: " + (motorista.isAtivo() ? "Ativo" : "Inativo") + "\n\n" +
            "No sistema completo, esta ação:\n" +
            "• " + (motorista.isAtivo() ? "Desativaria" : "Ativaria") + " o motorista\n" +
            "• " + (motorista.isAtivo() ? "Desativaria" : "Ativaria") + " seus veículos\n" +
            "• Atualizaria o status no sistema"
        );
        alert.showAndWait();
    }
}