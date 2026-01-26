package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.SistemaUberLand;

public class MainApp extends Application {
    
    private SistemaUberLand sistema = new SistemaUberLand();
    private Label lblStatus;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        Label titulo = new Label("UBERLAND - Sistema de Táxi");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        lblStatus = new Label("Passageiros: 0 | Motoristas: 0 | Corridas: 0");
        atualizarStatus();
        
        Button btnCadastrarPassageiro = new Button("Cadastrar Passageiro");
        Button btnCadastrarMotorista = new Button("Cadastrar Motorista");
        Button btnListarPassageiros = new Button("Listar Passageiros");
        Button btnListarMotoristas = new Button("Listar Motoristas");
        Button btnSolicitarCorrida = new Button("Solicitar Corrida");
        Button btnSair = new Button("Sair");
        
        // Ajustar tamanho dos botões
        btnCadastrarPassageiro.setPrefWidth(200);
        btnCadastrarMotorista.setPrefWidth(200);
        btnListarPassageiros.setPrefWidth(200);
        btnListarMotoristas.setPrefWidth(200);
        btnSolicitarCorrida.setPrefWidth(200);
        btnSair.setPrefWidth(200);
        
        // Ações dos botões
        btnCadastrarPassageiro.setOnAction(e -> {
            TelaCadastroPassageiro tela = new TelaCadastroPassageiro(sistema);
            tela.mostrar();
            atualizarStatus();
        });
        
        btnCadastrarMotorista.setOnAction(e -> {
            TelaCadastroMotorista tela = new TelaCadastroMotorista(sistema);
            tela.mostrar();
            atualizarStatus();
        });
        
        btnListarPassageiros.setOnAction(e -> {
            TelaListarPassageiros tela = new TelaListarPassageiros(sistema);
            tela.mostrar();
        });
        
        btnListarMotoristas.setOnAction(e -> {
            TelaListarMotoristas tela = new TelaListarMotoristas(sistema);
            tela.mostrar();
        });
        
        btnSolicitarCorrida.setOnAction(e -> {
            TelaSolicitarCorrida tela = new TelaSolicitarCorrida(sistema);
            tela.mostrar();
            atualizarStatus();
        });
        
        btnSair.setOnAction(e -> stage.close());
        
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
            titulo, lblStatus, 
            btnCadastrarPassageiro, btnCadastrarMotorista,
            btnListarPassageiros, btnListarMotoristas,
            btnSolicitarCorrida, btnSair
        );
        
        Scene scene = new Scene(layout, 450, 400);
        stage.setTitle("UberLand - Sistema de Táxi");
        stage.setScene(scene);
        stage.show();
    }
    
    private void atualizarStatus() {
        lblStatus.setText(
            "📊 Status: " + 
            "Passageiros: " + sistema.getTotalPassageiros() + " | " +
            "Motoristas: " + sistema.getTotalMotoristas() + " | " +
            "Corridas: " + sistema.getTotalCorridas()
        );
    }
}