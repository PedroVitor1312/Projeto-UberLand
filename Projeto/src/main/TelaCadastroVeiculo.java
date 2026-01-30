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
import model.Motorista;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;

public class TelaCadastroVeiculo {
    
    private final SistemaUberLand sistema;
    private final Stage stage;
    private Motorista motoristaSelecionado;
    private Label lblCategoria;
    private Label lblDescricaoCategoria;
    
    public TelaCadastroVeiculo(SistemaUberLand sistema) {
        this.sistema = sistema;
        this.stage = new Stage();
    }
    
    public TelaCadastroVeiculo(SistemaUberLand sistema, Motorista motorista) {
        this.sistema = sistema;
        this.motoristaSelecionado = motorista;
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
        stage.setTitle("🚗 Cadastrar Veículo - UberLand");
        
        // Layout principal
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setStyle("-fx-background-color: #f5f7fa;");
        
        // Cabeçalho
        VBox cabecalho = criarCabecalho();
        borderPane.setTop(cabecalho);
        
        // Conteúdo principal
        ScrollPane conteudo = criarConteudo();
        borderPane.setCenter(conteudo);
        
        // Rodapé
        HBox rodape = criarRodape();
        borderPane.setBottom(rodape);
        
        Scene scene = new Scene(borderPane, 900, 700);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    private VBox criarCabecalho() {
        VBox cabecalho = new VBox(10);
        cabecalho.setAlignment(Pos.CENTER);
        cabecalho.setPadding(new Insets(0, 0, 20, 0));
        
        Label titulo = new Label("🚗 CADASTRAR NOVO VEÍCULO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.web("#FF5722"));
        
        String subtituloTexto;
        if (motoristaSelecionado != null) {
            subtituloTexto = "Motorista: " + motoristaSelecionado.getNome() + 
                           " | O sistema classificará automaticamente como UberX, Comfort ou Black";
        } else {
            subtituloTexto = "O sistema classificará automaticamente como UberX, Comfort ou Black";
        }
        
        Label subtitulo = new Label(subtituloTexto);
        subtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        subtitulo.setTextFill(Color.web("#666666"));
        
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        
        cabecalho.getChildren().addAll(titulo, subtitulo, separator);
        return cabecalho;
    }
    
    private ScrollPane criarConteudo() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        
        // Grid para o formulário
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10));
        
        int linha = 0;
        
        // Motorista (se não foi pré-selecionado)
        if (motoristaSelecionado == null) {
            Label lblMotorista = criarLabel("Motorista *");
            ComboBox<Motorista> comboMotoristas = new ComboBox<>();
            for (Motorista m : sistema.getMotoristas()) {
                if (m.isAtivo()) {
                    comboMotoristas.getItems().add(m);
                }
            }
            comboMotoristas.setPromptText("Selecione um motorista...");
            comboMotoristas.setCellFactory(lv -> new ListCell<Motorista>() {
                @Override
                protected void updateItem(Motorista item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getNome() + " (CNH: " + item.getCnh() + ")");
                }
            });
            comboMotoristas.setButtonCell(new ListCell<Motorista>() {
                @Override
                protected void updateItem(Motorista item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "Selecione um motorista..." : item.getNome());
                }
            });
            estilizarComboBox(comboMotoristas);
            
            grid.add(lblMotorista, 0, linha);
            grid.add(comboMotoristas, 1, linha, 2, 1);
            linha++;
        } else {
            // Mostrar motorista pré-selecionado
            Label lblMotoristaInfo = new Label("Motorista: " + motoristaSelecionado.getNome() + 
                                              " (CNH: " + motoristaSelecionado.getCnh() + ")");
            lblMotoristaInfo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            lblMotoristaInfo.setTextFill(Color.web("#2196F3"));
            grid.add(lblMotoristaInfo, 0, linha, 3, 1);
            linha++;
        }
        
        // Informações básicas do veículo
        Separator sepBasico = new Separator();
        sepBasico.setPadding(new Insets(10, 0, 10, 0));
        Label lblSecaoBasico = new Label("📋 INFORMAÇÕES BÁSICAS DO VEÍCULO");
        lblSecaoBasico.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblSecaoBasico.setTextFill(Color.web("#2196F3"));
        grid.add(lblSecaoBasico, 0, linha, 3, 1);
        linha++;
        grid.add(sepBasico, 0, linha, 3, 1);
        linha++;
        
        // Placa
        Label lblPlaca = criarLabel("Placa *");
        TextField txtPlaca = criarTextField("AAA-1A23");
        grid.add(lblPlaca, 0, linha);
        grid.add(txtPlaca, 1, linha);
        
        Label lblChassi = criarLabel("Chassi (opcional)");
        TextField txtChassi = criarTextField("9BWZZZ377VT004251");
        grid.add(lblChassi, 2, linha);
        grid.add(txtChassi, 3, linha);
        linha++;
        
        // Marca/Modelo
        Label lblMarca = criarLabel("Marca *");
        TextField txtMarca = criarTextField("Volkswagen");
        grid.add(lblMarca, 0, linha);
        grid.add(txtMarca, 1, linha);
        
        Label lblModelo = criarLabel("Modelo *");
        TextField txtModelo = criarTextField("Gol");
        grid.add(lblModelo, 2, linha);
        grid.add(txtModelo, 3, linha);
        linha++;
        
        // Cor/Ano
        Label lblCor = criarLabel("Cor");
        TextField txtCor = criarTextField("Prata");
        grid.add(lblCor, 0, linha);
        grid.add(txtCor, 1, linha);
        
        Label lblAno = criarLabel("Ano *");
        Spinner<Integer> spinnerAno = new Spinner<>(2000, 2025, 2022);
        spinnerAno.setEditable(true);
        spinnerAno.setPrefWidth(150);
        grid.add(lblAno, 2, linha);
        grid.add(spinnerAno, 3, linha);
        linha++;
        
        // Capacidade
        Label lblCapacidade = criarLabel("Capacidade (pessoas) *");
        Spinner<Integer> spinnerCapacidade = new Spinner<>(1, 10, 4);
        spinnerCapacidade.setEditable(true);
        spinnerCapacidade.setPrefWidth(150);
        grid.add(lblCapacidade, 0, linha);
        grid.add(spinnerCapacidade, 1, linha);
        linha++;
        
        // Características
        Separator sepCaracteristicas = new Separator();
        sepCaracteristicas.setPadding(new Insets(10, 0, 10, 0));
        Label lblSecaoCaracteristicas = new Label("⚙️ CARACTERÍSTICAS DO VEÍCULO");
        lblSecaoCaracteristicas.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblSecaoCaracteristicas.setTextFill(Color.web("#4CAF50"));
        grid.add(lblSecaoCaracteristicas, 0, linha, 3, 1);
        linha++;
        grid.add(sepCaracteristicas, 0, linha, 3, 1);
        linha++;
        
        // Coluna 1: Conforto
        VBox colunaConforto = new VBox(10);
        colunaConforto.setPadding(new Insets(10));
        colunaConforto.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 8;");
        
        Label lblConforto = new Label("🛋️ Conforto");
        lblConforto.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblConforto.setTextFill(Color.web("#795548"));
        
        CheckBox chkArCondicionado = criarCheckbox("Ar Condicionado");
        CheckBox chkDirecaoHidraulica = criarCheckbox("Direção Hidráulica");
        CheckBox chkVidroEletrico = criarCheckbox("Vidro Elétrico");
        CheckBox chkTravasEletricas = criarCheckbox("Travas Elétricas");
        CheckBox chkBancosCouro = criarCheckbox("Bancos de Couro");
        CheckBox chkTetoSolar = criarCheckbox("Teto Solar");
        
        colunaConforto.getChildren().addAll(lblConforto, chkArCondicionado, chkDirecaoHidraulica, 
                                          chkVidroEletrico, chkTravasEletricas, chkBancosCouro, chkTetoSolar);
        
        // Coluna 2: Segurança
        VBox colunaSeguranca = new VBox(10);
        colunaSeguranca.setPadding(new Insets(10));
        colunaSeguranca.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 8;");
        
        Label lblSeguranca = new Label("🛡️ Segurança");
        lblSeguranca.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblSeguranca.setTextFill(Color.web("#F44336"));
        
        CheckBox chkAirbag = criarCheckbox("Airbag");
        CheckBox chkAbs = criarCheckbox("Freios ABS");
        CheckBox chkControleTracao = criarCheckbox("Controle de Tração");
        
        colunaSeguranca.getChildren().addAll(lblSeguranca, chkAirbag, chkAbs, chkControleTracao);
        
        // Coluna 3: Tecnologia
        VBox colunaTecnologia = new VBox(10);
        colunaTecnologia.setPadding(new Insets(10));
        colunaTecnologia.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 8;");
        
        Label lblTecnologia = new Label("📱 Tecnologia");
        lblTecnologia.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblTecnologia.setTextFill(Color.web("#2196F3"));
        
        CheckBox chkCameraRe = criarCheckbox("Câmera de Ré");
        CheckBox chkSensorEstacionamento = criarCheckbox("Sensor de Estacionamento");
        CheckBox chkCambioAutomatico = criarCheckbox("Câmbio Automático");
        CheckBox chkPilotoAutomatico = criarCheckbox("Piloto Automático");
        CheckBox chkWifi = criarCheckbox("Wi-Fi");
        CheckBox chkCarregadorWireless = criarCheckbox("Carregador Wireless");
        CheckBox chkSistemaSomPremium = criarCheckbox("Sistema de Som Premium");
        CheckBox chkRodasLigaLeve = criarCheckbox("Rodas de Liga Leve");
        
        colunaTecnologia.getChildren().addAll(lblTecnologia, chkCameraRe, chkSensorEstacionamento,
                                            chkCambioAutomatico, chkPilotoAutomatico, chkWifi,
                                            chkCarregadorWireless, chkSistemaSomPremium, chkRodasLigaLeve);
        
        // Adicionar colunas ao grid
        HBox hboxCaracteristicas = new HBox(20);
        hboxCaracteristicas.getChildren().addAll(colunaConforto, colunaSeguranca, colunaTecnologia);
        grid.add(hboxCaracteristicas, 0, linha, 4, 1);
        linha++;
        
        // Categoria prevista
        Separator sepCategoria = new Separator();
        sepCategoria.setPadding(new Insets(10, 0, 10, 0));
        Label lblSecaoCategoria = new Label("🏷️ CATEGORIA PREVISTA");
        lblSecaoCategoria.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblSecaoCategoria.setTextFill(Color.web("#9C27B0"));
        grid.add(lblSecaoCategoria, 0, linha, 3, 1);
        linha++;
        grid.add(sepCategoria, 0, linha, 3, 1);
        linha++;
        
        lblCategoria = new Label("🔍 Selecione as características acima");
        lblCategoria.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblCategoria.setTextFill(Color.web("#FF5722"));
        
        lblDescricaoCategoria = new Label();
        lblDescricaoCategoria.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        lblDescricaoCategoria.setTextFill(Color.web("#666666"));
        lblDescricaoCategoria.setWrapText(true);
        lblDescricaoCategoria.setPrefWidth(800);
        
        VBox vboxCategoria = new VBox(5);
        vboxCategoria.setPadding(new Insets(10));
        vboxCategoria.setStyle("-fx-background-color: #f3e5f5; -fx-background-radius: 8; -fx-border-color: #9C27B0; -fx-border-radius: 8;");
        vboxCategoria.getChildren().addAll(lblCategoria, lblDescricaoCategoria);
        grid.add(vboxCategoria, 0, linha, 4, 1);
        linha++;
        
        // Botão para simular categoria
        HBox hboxBotoesSimulacao = new HBox(20);
        hboxBotoesSimulacao.setAlignment(Pos.CENTER);
        hboxBotoesSimulacao.setPadding(new Insets(10, 0, 0, 0));
        
        Button btnSimularCategoria = new Button("🔍 Simular Categoria");
        btnSimularCategoria.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20;");
        btnSimularCategoria.setOnAction(e -> {
            simularCategoria(
                chkArCondicionado, chkDirecaoHidraulica, chkVidroEletrico, chkTravasEletricas,
                chkAirbag, chkAbs, chkControleTracao, chkBancosCouro, chkTetoSolar,
                chkSistemaSomPremium, chkRodasLigaLeve, chkCameraRe, chkSensorEstacionamento,
                chkCambioAutomatico, chkPilotoAutomatico, chkWifi, chkCarregadorWireless,
                spinnerCapacidade.getValue(), spinnerAno.getValue()
            );
        });
        
        Button btnLimpar = new Button("🗑️ Limpar Seleção");
        btnLimpar.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20;");
        btnLimpar.setOnAction(e -> {
            limparCheckboxes(chkArCondicionado, chkDirecaoHidraulica, chkVidroEletrico, chkTravasEletricas,
                           chkAirbag, chkAbs, chkControleTracao, chkBancosCouro, chkTetoSolar,
                           chkSistemaSomPremium, chkRodasLigaLeve, chkCameraRe, chkSensorEstacionamento,
                           chkCambioAutomatico, chkPilotoAutomatico, chkWifi, chkCarregadorWireless);
        });
        
        hboxBotoesSimulacao.getChildren().addAll(btnSimularCategoria, btnLimpar);
        grid.add(hboxBotoesSimulacao, 0, linha, 4, 1);
        linha++;
        
        // Botões principais
        HBox hboxBotoes = new HBox(20);
        hboxBotoes.setAlignment(Pos.CENTER);
        hboxBotoes.setPadding(new Insets(20, 0, 0, 0));
        
        Button btnCancelar = new Button("❌ Cancelar");
        btnCancelar.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 30;");
        btnCancelar.setOnAction(e -> stage.close());
        
        Button btnCadastrar = new Button("✅ Cadastrar Veículo");
        btnCadastrar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 30;");
        btnCadastrar.setOnAction(e -> {
            Motorista motorista = motoristaSelecionado;
            if (motorista == null) {
                // Tentar obter do ComboBox
                ComboBox<Motorista> combo = (ComboBox<Motorista>) grid.getChildren().stream()
                    .filter(node -> node instanceof ComboBox && ((ComboBox<?>) node).getPromptText().contains("motorista"))
                    .findFirst()
                    .orElse(null);
                
                if (combo != null && combo.getValue() != null) {
                    motorista = combo.getValue();
                } else {
                    mostrarAlerta("Erro", "Selecione um motorista!", Alert.AlertType.ERROR);
                    return;
                }
            }
            
            cadastrarVeiculo(
                motorista, txtPlaca.getText(), txtChassi.getText(),
                txtCor.getText(), spinnerAno.getValue(), txtMarca.getText(),
                txtModelo.getText(), spinnerCapacidade.getValue(),
                chkArCondicionado.isSelected(), chkDirecaoHidraulica.isSelected(),
                chkVidroEletrico.isSelected(), chkTravasEletricas.isSelected(),
                chkAirbag.isSelected(), chkAbs.isSelected(), chkControleTracao.isSelected(),
                chkBancosCouro.isSelected(), chkTetoSolar.isSelected(),
                chkSistemaSomPremium.isSelected(), chkRodasLigaLeve.isSelected(),
                chkCameraRe.isSelected(), chkSensorEstacionamento.isSelected(),
                chkCambioAutomatico.isSelected(), chkPilotoAutomatico.isSelected(),
                chkWifi.isSelected(), chkCarregadorWireless.isSelected()
            );
        });
        
        hboxBotoes.getChildren().addAll(btnCancelar, btnCadastrar);
        grid.add(hboxBotoes, 0, linha, 4, 1);
        
        container.getChildren().add(grid);
        
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        return scrollPane;
    }
    
    private void simularCategoria(
            CheckBox chkArCondicionado, CheckBox chkDirecaoHidraulica,
            CheckBox chkVidroEletrico, CheckBox chkTravasEletricas,
            CheckBox chkAirbag, CheckBox chkAbs, CheckBox chkControleTracao,
            CheckBox chkBancosCouro, CheckBox chkTetoSolar,
            CheckBox chkSistemaSomPremium, CheckBox chkRodasLigaLeve,
            CheckBox chkCameraRe, CheckBox chkSensorEstacionamento,
            CheckBox chkCambioAutomatico, CheckBox chkPilotoAutomatico,
            CheckBox chkWifi, CheckBox chkCarregadorWireless,
            int capacidade, int ano) {
        
        // Calcular pontuação simples
        int pontuacao = 0;
        if (chkArCondicionado.isSelected()) pontuacao += 1;
        if (chkDirecaoHidraulica.isSelected()) pontuacao += 1;
        if (chkVidroEletrico.isSelected()) pontuacao += 1;
        if (chkTravasEletricas.isSelected()) pontuacao += 1;
        if (chkAirbag.isSelected()) pontuacao += 2;
        if (chkAbs.isSelected()) pontuacao += 2;
        if (chkControleTracao.isSelected()) pontuacao += 2;
        if (chkBancosCouro.isSelected()) pontuacao += 3;
        if (chkTetoSolar.isSelected()) pontuacao += 3;
        if (chkSistemaSomPremium.isSelected()) pontuacao += 3;
        if (chkCameraRe.isSelected()) pontuacao += 4;
        if (chkSensorEstacionamento.isSelected()) pontuacao += 4;
        if (chkCambioAutomatico.isSelected()) pontuacao += 4;
        if (chkPilotoAutomatico.isSelected()) pontuacao += 4;
        if (chkWifi.isSelected()) pontuacao += 4;
        if (chkCarregadorWireless.isSelected()) pontuacao += 4;
        if (chkRodasLigaLeve.isSelected()) pontuacao += 4;
        
        if (capacidade >= 6) pontuacao += 5;
        else if (capacidade >= 4) pontuacao += 2;
        
        int anoAtual = java.time.Year.now().getValue();
        int idade = anoAtual - ano;
        if (idade <= 2) pontuacao += 10;
        else if (idade <= 5) pontuacao += 5;
        else if (idade <= 10) pontuacao += 2;
        
        // Determinar categoria
        String categoria;
        String emoji;
        String cor;
        String descricao;
        
        if (pontuacao >= 25 && capacidade >= 4 && ano >= 2018) {
            categoria = "UBER_BLACK";
            emoji = "⚫";
            cor = "#000000";
            descricao = "Veículos premium de luxo com motorista profissional. " +
                       "Ideal para ocasiões especiais e clientes exigentes.";
        } else if (pontuacao >= 15) {
            categoria = "UBER_COMFORT";
            emoji = "🔵";
            cor = "#2196F3";
            descricao = "Veículos espaçosos e confortáveis para viagens mais longas " +
                       "ou grupos pequenos. Equilíbrio entre conforto e preço.";
        } else {
            categoria = "UBER_X";
            emoji = "🟢";
            cor = "#4CAF50";
            descricao = "Opção econômica e prática para o dia a dia. " +
                       "Carros compactos, eficientes e com ótimo custo-benefício.";
        }
        
        lblCategoria.setText(emoji + " " + categoria + " (" + 
                           (categoria.equals("UBER_BLACK") ? "Premium" : 
                            categoria.equals("UBER_COMFORT") ? "Conforto" : "Econômico") + ")");
        lblCategoria.setTextFill(Color.web(cor));
        
        // Adicionar detalhes
        lblDescricaoCategoria.setText(descricao + 
            "\n\n📊 Pontuação do veículo: " + pontuacao + " pontos" +
            "\n💰 Tarifa mínima estimada: R$ " + 
            (categoria.equals("UBER_BLACK") ? "12,00" : 
             categoria.equals("UBER_COMFORT") ? "8,00" : "6,00") +
            "\n🚗 Valor por km: R$ " + 
            (categoria.equals("UBER_BLACK") ? "4,00" : 
             categoria.equals("UBER_COMFORT") ? "2,80" : "2,20"));
    }
    
    private void limparCheckboxes(CheckBox... checkboxes) {
        for (CheckBox cb : checkboxes) {
            cb.setSelected(false);
        }
        lblCategoria.setText("🔍 Selecione as características acima");
        lblCategoria.setTextFill(Color.web("#FF5722"));
        lblDescricaoCategoria.setText("");
    }
    
    private void cadastrarVeiculo(
            Motorista motorista, String placa, String chassi, String cor, int ano,
            String marca, String modelo, int capacidade,
            boolean arCondicionado, boolean direcaoHidraulica,
            boolean vidroEletrico, boolean travasEletricas,
            boolean airbag, boolean abs, boolean controleTracao,
            boolean bancosCouro, boolean tetoSolar,
            boolean sistemaSomPremium, boolean rodasLigaLeve,
            boolean cameraRe, boolean sensorEstacionamento,
            boolean cambioAutomatico, boolean pilotoAutomatico,
            boolean wifi, boolean carregadorWireless) {
        
        // Validações
        if (motorista == null) {
            mostrarAlerta("Erro", "Selecione um motorista!", Alert.AlertType.ERROR);
            return;
        }
        
        if (placa == null || placa.trim().isEmpty()) {
            mostrarAlerta("Erro", "Digite a placa do veículo!", Alert.AlertType.ERROR);
            return;
        }
        
        if (marca == null || marca.trim().isEmpty()) {
            mostrarAlerta("Erro", "Digite a marca do veículo!", Alert.AlertType.ERROR);
            return;
        }
        
        if (modelo == null || modelo.trim().isEmpty()) {
            mostrarAlerta("Erro", "Digite o modelo do veículo!", Alert.AlertType.ERROR);
            return;
        }
        
        if (ano < 2000 || ano > 2025) {
            mostrarAlerta("Erro", "Ano do veículo inválido! (2000-2025)", Alert.AlertType.ERROR);
            return;
        }
        
        if (capacidade < 1 || capacidade > 10) {
            mostrarAlerta("Erro", "Capacidade inválida! (1-10 pessoas)", Alert.AlertType.ERROR);
            return;
        }
        
        // Gerar chassi se não informado
        if (chassi == null || chassi.trim().isEmpty()) {
            chassi = "CH" + placa.replace("-", "").toUpperCase() + ano;
        }
        
        // Chamar método do sistema para cadastrar veículo
        boolean sucesso = sistema.cadastrarVeiculoParaMotorista(
            motorista.getCpf(), placa, chassi, cor, ano, marca, modelo, capacidade,
            arCondicionado, direcaoHidraulica, vidroEletrico, travasEletricas,
            airbag, abs, controleTracao, bancosCouro, tetoSolar,
            sistemaSomPremium, rodasLigaLeve, cameraRe, sensorEstacionamento,
            cambioAutomatico, pilotoAutomatico, wifi, carregadorWireless
        );
        
        if (sucesso) {
            mostrarAlerta("Sucesso", 
                "✅ Veículo cadastrado com sucesso!\n\n" +
                "👨‍✈️ Motorista: " + motorista.getNome() + "\n" +
                "🚗 Veículo: " + marca + " " + modelo + "\n" +
                "📍 Placa: " + placa + "\n" +
                "🎨 Cor: " + cor + "\n" +
                "📅 Ano: " + ano + "\n\n" +
                "🏷️ O veículo foi automaticamente classificado pelo sistema.",
                Alert.AlertType.INFORMATION);
            stage.close();
        } else {
            mostrarAlerta("Erro", 
                "❌ Não foi possível cadastrar o veículo.\n" +
                "Possíveis causas:\n" +
                "• Placa já cadastrada no sistema\n" +
                "• Motorista não encontrado\n" +
                "• Dados inválidos",
                Alert.AlertType.ERROR);
        }
    }
    
    private HBox criarRodape() {
        HBox rodape = new HBox();
        rodape.setAlignment(Pos.CENTER);
        rodape.setPadding(new Insets(20, 0, 0, 0));
        
        Label lblInfo = new Label("* Os veículos são classificados automaticamente baseados em suas características");
        lblInfo.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        lblInfo.setTextFill(Color.web("#95a5a6"));
        
        rodape.getChildren().add(lblInfo);
        return rodape;
    }
    
    private Label criarLabel(String texto) {
        Label label = new Label(texto);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        label.setTextFill(Color.web("#333333"));
        label.setPrefWidth(150);
        return label;
    }
    
    private TextField criarTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setPrefWidth(200);
        textField.setPrefHeight(35);
        textField.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #bdc3c7;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 5 10;" +
            "-fx-font-size: 12px;"
        );
        
        // Efeito de foco
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                textField.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #2196F3;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-padding: 5 10;" +
                    "-fx-font-size: 12px;" +
                    "-fx-effect: dropshadow(gaussian, rgba(33, 150, 243, 0.3), 5, 0, 0, 1);"
                );
            } else {
                textField.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #bdc3c7;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-padding: 5 10;" +
                    "-fx-font-size: 12px;"
                );
            }
        });
        
        return textField;
    }
    
    private CheckBox criarCheckbox(String texto) {
        CheckBox checkbox = new CheckBox(texto);
        checkbox.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        checkbox.setTextFill(Color.web("#333333"));
        checkbox.setStyle("-fx-padding: 3 0 3 0;");
        return checkbox;
    }
    
    private void estilizarComboBox(ComboBox<?> combo) {
        combo.setPrefWidth(400);
        combo.setPrefHeight(35);
        combo.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #bdc3c7;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-font-size: 12px;"
        );
    }
}