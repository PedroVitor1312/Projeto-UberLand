package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import controller.SistemaUberLand;

public class TelaCadastroPassageiro {
    
    private final SistemaUberLand sistema;
    private final Stage stage;
    
    public TelaCadastroPassageiro(SistemaUberLand sistema) {
        this.sistema = sistema;
        this.stage = new Stage();
        configurarStage();
    }
    
    private void configurarStage() {
        stage.setTitle("👤 Cadastrar Passageiro - UberLand");
        stage.setResizable(false);
    }
    
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        
        // Estilizar alertas
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1;");
        
        alert.showAndWait();
    }
    
    public void mostrar() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f7fa;");
        
        // Container principal
        VBox containerPrincipal = new VBox(20);
        containerPrincipal.setAlignment(Pos.TOP_CENTER);
        containerPrincipal.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 4);");
        containerPrincipal.setMaxWidth(550);
        containerPrincipal.setPadding(new Insets(30));
        
        // ====== CABEÇALHO ======
        VBox cabecalho = criarCabecalho();
        
        // ====== FORMULÁRIO ======
        GridPane formulario = criarFormulario();
        
        // ====== RODAPÉ ======
        HBox rodape = criarRodape();
        
        containerPrincipal.getChildren().addAll(cabecalho, formulario, rodape);
        root.setCenter(containerPrincipal);
        
        Scene scene = new Scene(root, 600, 550);
        stage.setScene(scene);
        stage.show();
    }
    
    private VBox criarCabecalho() {
        VBox cabecalho = new VBox(15);
        cabecalho.setAlignment(Pos.CENTER);
        cabecalho.setPadding(new Insets(0, 0, 20, 0));
        
        // Ícone e título
        HBox tituloBox = new HBox(10);
        tituloBox.setAlignment(Pos.CENTER);
        
        Label iconLabel = new Label("👤");
        iconLabel.setStyle("-fx-font-size: 28px;");
        
        Label titulo = new Label("CADASTRAR PASSAGEIRO");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 22));
        titulo.setTextFill(Color.web("#2196F3"));
        
        tituloBox.getChildren().addAll(iconLabel, titulo);
        
        // Subtítulo
        Label subtitulo = new Label("Adicione um novo passageiro ao sistema");
        subtitulo.setFont(Font.font("System", FontWeight.NORMAL, 13));
        subtitulo.setTextFill(Color.web("#7f8c8d"));
        
        // Separador
        Separator separator = new Separator();
        separator.setPrefWidth(450);
        separator.setStyle("-fx-background-color: #ecf0f1;");
        
        cabecalho.getChildren().addAll(tituloBox, subtitulo, separator);
        return cabecalho;
    }
    
    private GridPane criarFormulario() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        
        // ====== CAMPOS DO FORMULÁRIO ======
        
        // Nome completo
        Label lblNome = criarLabel("Nome completo *");
        TextField txtNome = criarTextField("Ex: Maria Silva Santos");
        grid.add(lblNome, 0, 0);
        grid.add(txtNome, 1, 0);
        
        // CPF
        Label lblCPF = criarLabel("CPF *");
        TextField txtCPF = criarTextField("Ex: 123.456.789-00");
        grid.add(lblCPF, 0, 1);
        grid.add(txtCPF, 1, 1);
        
        // Email
        Label lblEmail = criarLabel("Email");
        TextField txtEmail = criarTextField("Ex: maria@email.com");
        grid.add(lblEmail, 0, 2);
        grid.add(txtEmail, 1, 2);
        
        // Celular
        Label lblCelular = criarLabel("Celular");
        TextField txtCelular = criarTextField("Ex: (11) 98765-4321");
        grid.add(lblCelular, 0, 3);
        grid.add(txtCelular, 1, 3);
        
        // Endereço
        Label lblEndereco = criarLabel("Endereço");
        TextField txtEndereco = criarTextField("Rua, número, bairro, cidade");
        grid.add(lblEndereco, 0, 4);
        grid.add(txtEndereco, 1, 4);
        
        // Forma de Pagamento
        Label lblPagamento = criarLabel("Forma de Pagamento *");
        ComboBox<String> comboPagamento = criarComboBox();
        comboPagamento.getItems().addAll("DINHEIRO", "CARTAO", "PIX");
        comboPagamento.setValue("CARTAO");
        grid.add(lblPagamento, 0, 5);
        grid.add(comboPagamento, 1, 5);
        
        // ====== BOTÕES ======
        HBox botoesContainer = new HBox(20);
        botoesContainer.setAlignment(Pos.CENTER);
        botoesContainer.setPadding(new Insets(30, 0, 10, 0));
        
        Button btnCancelar = criarBotao("Cancelar", "#F44336");
        Button btnSalvar = criarBotao("Cadastrar Passageiro", "#4CAF50");
        
        // Ações dos botões
        btnCancelar.setOnAction(e -> stage.close());
        
        btnSalvar.setOnAction(e -> processarCadastro(
            txtNome, txtCPF, txtEmail, txtCelular, txtEndereco, comboPagamento
        ));
        
        botoesContainer.getChildren().addAll(btnCancelar, btnSalvar);
        grid.add(botoesContainer, 0, 6, 2, 1);
        
        return grid;
    }
    
    private void processarCadastro(
        TextField txtNome, TextField txtCPF, TextField txtEmail, 
        TextField txtCelular, TextField txtEndereco, 
        ComboBox<String> comboPagamento
    ) {
        // Validações
        if (txtNome.getText().trim().isEmpty()) {
            mostrarAlerta("Campo obrigatório", "O nome completo é obrigatório!", Alert.AlertType.WARNING);
            txtNome.requestFocus();
            return;
        }
        
        if (txtCPF.getText().trim().isEmpty()) {
            mostrarAlerta("Campo obrigatório", "O CPF é obrigatório!", Alert.AlertType.WARNING);
            txtCPF.requestFocus();
            return;
        }
        
        if (comboPagamento.getValue() == null) {
            mostrarAlerta("Campo obrigatório", "Selecione uma forma de pagamento!", Alert.AlertType.WARNING);
            return;
        }
        
        // Preparar dados
        String nome = txtNome.getText().trim();
        String cpf = txtCPF.getText().trim();
        String email = txtEmail.getText().trim().isEmpty() ? "nao_informado@uberland.com" : txtEmail.getText().trim();
        String celular = txtCelular.getText().trim().isEmpty() ? "(00) 00000-0000" : txtCelular.getText().trim();
        String endereco = txtEndereco.getText().trim().isEmpty() ? "Endereço não informado" : txtEndereco.getText().trim();
        String pagamento = comboPagamento.getValue();
        
        // Chamar sistema para cadastrar
        boolean sucesso = sistema.cadastrarPassageiroGUI(nome, cpf, email, celular, pagamento);
        
        if (sucesso) {
            // Mensagem de sucesso
            StringBuilder mensagem = new StringBuilder();
            mensagem.append("✅ Passageiro cadastrado com sucesso!\n\n");
            mensagem.append("Nome: ").append(nome).append("\n");
            mensagem.append("CPF: ").append(cpf).append("\n");
            mensagem.append("Email: ").append(email).append("\n");
            mensagem.append("Forma de Pagamento: ").append(pagamento);
            
            mostrarAlerta("Cadastro realizado", mensagem.toString(), Alert.AlertType.INFORMATION);
            stage.close();
        } else {
            mostrarAlerta("Erro no cadastro", 
                "❌ Não foi possível cadastrar o passageiro.\n\n" +
                "Possíveis causas:\n" +
                "• CPF já cadastrado\n" +
                "• Formato de CPF inválido\n" +
                "• Dados inconsistentes", 
                Alert.AlertType.ERROR);
        }
    }
    
    private HBox criarRodape() {
        HBox rodape = new HBox();
        rodape.setAlignment(Pos.CENTER);
        rodape.setPadding(new Insets(20, 0, 0, 0));
        
        Label lblInfo = new Label("* Campos obrigatórios");
        lblInfo.setFont(Font.font("System", FontWeight.NORMAL, 11));
        lblInfo.setTextFill(Color.web("#95a5a6"));
        
        rodape.getChildren().add(lblInfo);
        return rodape;
    }
    
    // ====== MÉTODOS AUXILIARES PARA CRIAÇÃO DE COMPONENTES ======
    
    private Label criarLabel(String texto) {
        Label label = new Label(texto);
        label.setFont(Font.font("System", FontWeight.SEMI_BOLD, 13));
        label.setTextFill(Color.web("#34495e"));
        label.setPrefWidth(150);
        return label;
    }
    
    private TextField criarTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setPrefWidth(250);
        textField.setPrefHeight(35);
        textField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #bdc3c7;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 8 12;" +
            "-fx-font-size: 13px;"
        );
        
        // Efeito de foco
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                textField.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #2196F3;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-padding: 8 12;" +
                    "-fx-font-size: 13px;" +
                    "-fx-effect: dropshadow(gaussian, rgba(33, 150, 243, 0.3), 5, 0, 0, 1);"
                );
            } else {
                textField.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #bdc3c7;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-padding: 8 12;" +
                    "-fx-font-size: 13px;"
                );
            }
        });
        
        return textField;
    }
    
    private ComboBox<String> criarComboBox() {
        ComboBox<String> combo = new ComboBox<>();
        combo.setPrefWidth(250);
        combo.setPrefHeight(35);
        combo.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #bdc3c7;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-font-size: 13px;"
        );
        
        // Efeito de foco
        combo.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                combo.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #2196F3;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-font-size: 13px;" +
                    "-fx-effect: dropshadow(gaussian, rgba(33, 150, 243, 0.3), 5, 0, 0, 1);"
                );
            } else {
                combo.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #bdc3c7;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-font-size: 13px;"
                );
            }
        });
        
        return combo;
    }
    
    private Button criarBotao(String texto, String cor) {
        Button botao = new Button(texto);
        botao.setPrefWidth(200);
        botao.setPrefHeight(40);
        botao.setFont(Font.font("System", FontWeight.BOLD, 14));
        botao.setTextFill(Color.WHITE);
        
        // Estilo base
        String estiloBase = 
            "-fx-background-color: " + cor + ";" +
            "-fx-background-radius: 8;" +
            "-fx-border-radius: 8;" +
            "-fx-cursor: hand;";
        
        botao.setStyle(estiloBase);
        
        // Efeitos hover
        botao.setOnMouseEntered(e -> {
            botao.setStyle(estiloBase + 
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);" +
                "-fx-scale-x: 1.02;" +
                "-fx-scale-y: 1.02;");
        });
        
        botao.setOnMouseExited(e -> {
            botao.setStyle(estiloBase);
            botao.setScaleX(1.0);
            botao.setScaleY(1.0);
        });
        
        // Efeito de clique
        botao.setOnMousePressed(e -> {
            botao.setStyle(estiloBase + "-fx-scale-x: 0.98; -fx-scale-y: 0.98;");
        });
        
        botao.setOnMouseReleased(e -> {
            botao.setStyle(estiloBase + "-fx-scale-x: 1.02; -fx-scale-y: 1.02;");
        });
        
        return botao;
    }
}