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
import model.Passageiro;
import model.Motorista;

public class TelaSolicitarCorrida {
    
    private final SistemaUberLand sistema;
    private final Stage stage;
    private ComboBox<Passageiro> comboPassageiros;
    private ComboBox<Motorista> comboMotoristas;
    private TextField txtDistancia;
    private Label lblValor;
    
    public TelaSolicitarCorrida(SistemaUberLand sistema) {
        this.sistema = sistema;
        this.stage = new Stage();
    }
    
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    public void mostrar() {
        stage.setTitle("🚖 Solicitar Corrida - UberLand");
        
        // ====== LAYOUT PRINCIPAL ======
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        
        // ====== CABEÇALHO ======
        VBox cabecalho = criarCabecalho();
        borderPane.setTop(cabecalho);
        
        // ====== FORMULÁRIO ======
        VBox formulario = criarFormulario();
        borderPane.setCenter(formulario);
        
        // ====== RODAPÉ ======
        HBox rodape = criarRodape();
        borderPane.setBottom(rodape);
        
        // ====== CONFIGURAÇÃO DA JANELA ======
        Scene scene = new Scene(borderPane, 550, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    // ====== MÉTODO PARA CRIAR CABEÇALHO ======
    private VBox criarCabecalho() {
        VBox cabecalho = new VBox(15);
        cabecalho.setAlignment(Pos.CENTER);
        cabecalho.setPadding(new Insets(0, 0, 20, 0));
        
        // Ícone e título
        HBox tituloBox = new HBox(10);
        tituloBox.setAlignment(Pos.CENTER);
        
        Label iconLabel = new Label("🚖");
        iconLabel.setStyle("-fx-font-size: 28px;");
        
        Label titulo = new Label("SOLICITAR NOVA CORRIDA");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 22));
        titulo.setTextFill(Color.web("#9C27B0"));
        
        tituloBox.getChildren().addAll(iconLabel, titulo);
        
        // Subtítulo
        Label subtitulo = new Label("Preencha os dados da corrida desejada");
        subtitulo.setFont(Font.font("System", FontWeight.NORMAL, 13));
        subtitulo.setTextFill(Color.web("#7f8c8d"));
        
        // Separador
        Separator separator = new Separator();
        separator.setPrefWidth(450);
        separator.setStyle("-fx-background-color: #ecf0f1;");
        
        cabecalho.getChildren().addAll(tituloBox, subtitulo, separator);
        return cabecalho;
    }
    
    // ====== MÉTODO PARA CRIAR FORMULÁRIO ======
    private VBox criarFormulario() {
        VBox formulario = new VBox(20);
        formulario.setAlignment(Pos.CENTER);
        formulario.setPadding(new Insets(20));
        formulario.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        
        // ====== VERIFICAÇÃO INICIAL ======
        if (sistema.getPassageiros().isEmpty() || sistema.getMotoristas().isEmpty()) {
            return criarMensagemErro();
        }
        
        // ====== CAMPOS DO FORMULÁRIO ======
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));
        
        // Passageiro
        Label lblPassageiro = criarLabel("Passageiro *");
        comboPassageiros = new ComboBox<>();
        for (Passageiro p : sistema.getPassageiros()) {
            comboPassageiros.getItems().add(p);
        }
        comboPassageiros.setPromptText("Selecione um passageiro...");
        comboPassageiros.setCellFactory(lv -> new ListCell<Passageiro>() {
            @Override
            protected void updateItem(Passageiro item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getNome() + " (CPF: " + item.getCpf() + 
                           " | Corridas: " + item.getNumeroCorridas() + ")");
                }
            }
        });
        comboPassageiros.setButtonCell(new ListCell<Passageiro>() {
            @Override
            protected void updateItem(Passageiro item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Selecione um passageiro...");
                } else {
                    setText(item.getNome());
                }
            }
        });
        estilizarComboBox(comboPassageiros);
        
        // Motorista
        Label lblMotorista = criarLabel("Motorista *");
        comboMotoristas = new ComboBox<>();
        for (Motorista m : sistema.getMotoristas()) {
            if (m.isAtivo()) { // Mostrar apenas motoristas ativos
                comboMotoristas.getItems().add(m);
            }
        }
        comboMotoristas.setPromptText("Selecione um motorista...");
        comboMotoristas.setCellFactory(lv -> new ListCell<Motorista>() {
            @Override
            protected void updateItem(Motorista item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    String status = item.isAtivo() ? "✅" : "❌";
                    setText(item.getNome() + " " + status + 
                           " | Nota: " + String.format("%.1f", item.getNotaMedia()) +
                           " | CNH: " + item.getCnh());
                }
            }
        });
        comboMotoristas.setButtonCell(new ListCell<Motorista>() {
            @Override
            protected void updateItem(Motorista item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Selecione um motorista...");
                } else {
                    setText(item.getNome() + " (CNH: " + item.getCnh() + ")");
                }
            }
        });
        estilizarComboBox(comboMotoristas);
        
        // Distância
        Label lblDistancia = criarLabel("Distância (km) *");
        txtDistancia = criarTextField("Ex: 10.5");
        txtDistancia.setPromptText("Digite a distância em quilômetros");
        
        // Valor estimado (calculado automaticamente)
        Label lblValorEstimado = criarLabel("Valor estimado:");
        lblValor = new Label("R$ 0,00");
        lblValor.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblValor.setTextFill(Color.web("#4CAF50"));
        
        // Calcular valor quando a distância mudar
        txtDistancia.textProperty().addListener((obs, oldVal, newVal) -> {
            calcularValorEstimado();
        });
        
        // Layout dos campos
        grid.add(lblPassageiro, 0, 0);
        grid.add(comboPassageiros, 1, 0);
        
        grid.add(lblMotorista, 0, 1);
        grid.add(comboMotoristas, 1, 1);
        
        grid.add(lblDistancia, 0, 2);
        grid.add(txtDistancia, 1, 2);
        
        grid.add(lblValorEstimado, 0, 3);
        grid.add(lblValor, 1, 3);
        
        // ====== BOTÕES ======
        HBox botoesContainer = new HBox(20);
        botoesContainer.setAlignment(Pos.CENTER);
        botoesContainer.setPadding(new Insets(30, 0, 10, 0));
        
        Button btnCancelar = criarBotao("Cancelar", "#F44336");
        Button btnSolicitar = criarBotao("Solicitar Corrida", "#4CAF50");
        
        // Ações dos botões
        btnCancelar.setOnAction(e -> stage.close());
        
        btnSolicitar.setOnAction(e -> {
            solicitarCorrida();
        });
        
        botoesContainer.getChildren().addAll(btnCancelar, btnSolicitar);
        
        // Adicionar tudo ao formulário
        formulario.getChildren().addAll(grid, botoesContainer);
        return formulario;
    }
    
    // ====== MÉTODO PARA SOLICITAR CORRIDA ======
    private void solicitarCorrida() {
        // Validações
        if (comboPassageiros.getValue() == null) {
            mostrarAlerta("Campo obrigatório", "Selecione um passageiro!", Alert.AlertType.WARNING);
            comboPassageiros.requestFocus();
            return;
        }
        
        if (comboMotoristas.getValue() == null) {
            mostrarAlerta("Campo obrigatório", "Selecione um motorista!", Alert.AlertType.WARNING);
            comboMotoristas.requestFocus();
            return;
        }
        
        if (txtDistancia.getText().trim().isEmpty()) {
            mostrarAlerta("Campo obrigatório", "Informe a distância da corrida!", Alert.AlertType.WARNING);
            txtDistancia.requestFocus();
            return;
        }
        
        try {
            double distancia = Double.parseDouble(txtDistancia.getText().trim());
            
            // Validações de distância
            if (distancia <= 0) {
                mostrarAlerta("Valor inválido", "A distância deve ser maior que zero!", Alert.AlertType.WARNING);
                txtDistancia.requestFocus();
                txtDistancia.selectAll();
                return;
            }
            
            if (distancia > 100) {
                mostrarAlerta("Distância muito longa", "A distância máxima é de 100 km!", Alert.AlertType.WARNING);
                txtDistancia.requestFocus();
                txtDistancia.selectAll();
                return;
            }
            
            // Selecionar passageiro e motorista no sistema
            Passageiro passageiro = comboPassageiros.getValue();
            Motorista motorista = comboMotoristas.getValue();
            
            sistema.selecionarPassageiro(passageiro);
            sistema.selecionarMotorista(motorista);
            
            // Solicitar corrida
            boolean sucesso = sistema.solicitarCorridaGUI(distancia);
            
            if (sucesso) {
                // Calcular valor estimado
                calcularValorEstimado();
                double valor = extrairValorDoLabel(lblValor.getText());
                
                // Mostrar mensagem de sucesso
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("✅ CORRIDA SOLICITADA COM SUCESSO!\n\n");
                mensagem.append("📋 Detalhes da corrida:\n");
                mensagem.append("• Passageiro: ").append(passageiro.getNome()).append("\n");
                mensagem.append("• Motorista: ").append(motorista.getNome()).append("\n");
                mensagem.append("• Distância: ").append(String.format("%.1f km", distancia)).append("\n");
                mensagem.append("• Valor estimado: ").append(String.format("R$ %.2f", valor)).append("\n\n");
                mensagem.append("📍 A corrida foi registrada no sistema.");
                
                mostrarAlerta("Corrida solicitada", mensagem.toString(), Alert.AlertType.INFORMATION);
                stage.close();
            } else {
                mostrarAlerta("Erro na solicitação", 
                    "❌ Não foi possível solicitar a corrida.\n\n" +
                    "Possíveis causas:\n" +
                    "• Motorista não está ativo\n" +
                    "• Motorista não possui veículo\n" +
                    "• Erro no sistema", 
                    Alert.AlertType.ERROR);
            }
            
        } catch (NumberFormatException ex) {
            mostrarAlerta("Valor inválido", "Digite um número válido para a distância!\nEx: 10.5", Alert.AlertType.WARNING);
            txtDistancia.requestFocus();
            txtDistancia.selectAll();
        } catch (Exception ex) {
            mostrarAlerta("Erro inesperado", "Ocorreu um erro: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    // ====== MÉTODO PARA CALCULAR VALOR ESTIMADO ======
    private void calcularValorEstimado() {
        try {
            if (!txtDistancia.getText().trim().isEmpty()) {
                double distancia = Double.parseDouble(txtDistancia.getText().trim());
                double valor = distancia * 2.5; // R$ 2,50 por km
                
                // Verificar se motorista selecionado é VIP
                if (comboMotoristas.getValue() != null) {
                    Motorista motorista = comboMotoristas.getValue();
                    // Aqui poderia adicionar lógica para calcular baseado no tipo de veículo
                    if (motorista.getVeiculos() != null && !motorista.getVeiculos().isEmpty()) {
                        // Se o motorista tem veículo, usar cálculo mais preciso
                        // Por enquanto mantemos o valor fixo de R$ 2,50/km
                    }
                }
                
                lblValor.setText(String.format("R$ %.2f", valor));
            } else {
                lblValor.setText("R$ 0,00");
            }
        } catch (NumberFormatException e) {
            lblValor.setText("R$ 0,00");
        }
    }
    
    // ====== MÉTODO PARA EXTRAIR VALOR DO LABEL ======
    private double extrairValorDoLabel(String texto) {
        try {
            // Remove "R$ " e substitui vírgula por ponto
            String valorStr = texto.replace("R$ ", "").replace(",", ".");
            return Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    // ====== MÉTODO PARA CRIAR MENSAGEM DE ERRO ======
    private VBox criarMensagemErro() {
        VBox mensagemBox = new VBox(20);
        mensagemBox.setAlignment(Pos.CENTER);
        mensagemBox.setPadding(new Insets(40, 20, 40, 20));
        mensagemBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        Label icon = new Label("⚠️");
        icon.setStyle("-fx-font-size: 48px;");
        
        Label titulo = new Label("Cadastro Insuficiente");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 18));
        titulo.setTextFill(Color.web("#FF5722"));
        
        VBox detalhes = new VBox(10);
        detalhes.setAlignment(Pos.CENTER_LEFT);
        
        if (sistema.getPassageiros().isEmpty()) {
            Label lbl1 = new Label("• ❌ Nenhum passageiro cadastrado");
            lbl1.setFont(Font.font("System", FontWeight.NORMAL, 14));
            lbl1.setTextFill(Color.web("#F44336"));
            detalhes.getChildren().add(lbl1);
        }
        
        if (sistema.getMotoristas().isEmpty()) {
            Label lbl2 = new Label("• ❌ Nenhum motorista cadastrado");
            lbl2.setFont(Font.font("System", FontWeight.NORMAL, 14));
            lbl2.setTextFill(Color.web("#F44336"));
            detalhes.getChildren().add(lbl2);
        }
        
        Label instrucao = new Label("\nPara solicitar uma corrida, é necessário:\n1. Cadastrar ao menos 1 passageiro\n2. Cadastrar ao menos 1 motorista\n3. Ambos devem estar ativos no sistema");
        instrucao.setFont(Font.font("System", FontWeight.NORMAL, 13));
        instrucao.setTextFill(Color.web("#666666"));
        instrucao.setWrapText(true);
        
        Button btnFechar = criarBotao("Fechar", "#607D8B");
        btnFechar.setOnAction(e -> stage.close());
        
        mensagemBox.getChildren().addAll(icon, titulo, detalhes, instrucao, btnFechar);
        return mensagemBox;
    }
    
    // ====== MÉTODO PARA CRIAR RODAPÉ ======
    private HBox criarRodape() {
        HBox rodape = new HBox();
        rodape.setAlignment(Pos.CENTER);
        rodape.setPadding(new Insets(20, 0, 0, 0));
        
        Label lblInfo = new Label("* Campos obrigatórios | Valor estimado: R$ 2,50 por km");
        lblInfo.setFont(Font.font("System", FontWeight.NORMAL, 11));
        lblInfo.setTextFill(Color.web("#95a5a6"));
        
        rodape.getChildren().add(lblInfo);
        return rodape;
    }
    
    // ====== MÉTODOS AUXILIARES ======
    
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
                    "-fx-border-color: #9C27B0;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-padding: 8 12;" +
                    "-fx-font-size: 13px;" +
                    "-fx-effect: dropshadow(gaussian, rgba(156, 39, 176, 0.3), 5, 0, 0, 1);"
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
    
    private void estilizarComboBox(ComboBox<?> combo) {
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
                    "-fx-border-color: #9C27B0;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-font-size: 13px;" +
                    "-fx-effect: dropshadow(gaussian, rgba(156, 39, 176, 0.3), 5, 0, 0, 1);"
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
    }
    
    private Button criarBotao(String texto, String cor) {
        Button botao = new Button(texto);
        botao.setPrefWidth(180);
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
        
        return botao;
    }
}