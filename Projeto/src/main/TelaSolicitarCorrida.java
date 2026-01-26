package main;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import controller.SistemaUberLand;

public class TelaSolicitarCorrida {
    
    private final SistemaUberLand sistema;
    private final Stage stage;
    
    public TelaSolicitarCorrida(SistemaUberLand sistema) {
        this.sistema = sistema;
        this.stage = new Stage();
    }
    
    public void mostrar() {
        stage.setTitle("Solicitar Corrida");
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        
        Label lblInfo = new Label("Solicitar Nova Corrida");
        lblInfo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        grid.add(lblInfo, 0, 0, 2, 1);
        
        // Mostra passageiros disponíveis
        if (sistema.getPassageiros().isEmpty() || sistema.getMotoristas().isEmpty()) {
            Label lblErro = new Label("⚠️ Cadastre ao menos 1 passageiro e 1 motorista!");
            lblErro.setStyle("-fx-text-fill: red;");
            grid.add(lblErro, 0, 1, 2, 1);
            
            Button btnFechar = new Button("Fechar");
            btnFechar.setOnAction(e -> stage.close());
            grid.add(btnFechar, 0, 2);
            
        } else {
            // Mostra primeiro passageiro
            Label lblPassageiro = new Label("Passageiro:");
            Label lblPassageiroNome = new Label(sistema.getPassageiros().get(0).getNome());
            lblPassageiroNome.setStyle("-fx-font-weight: bold;");
            
            // Mostra primeiro motorista
            Label lblMotorista = new Label("Motorista:");
            Label lblMotoristaNome = new Label(sistema.getMotoristas().get(0).getNome());
            lblMotoristaNome.setStyle("-fx-font-weight: bold;");
            
            Label lblDistancia = new Label("Distância (km):");
            TextField txtDistancia = new TextField();
            txtDistancia.setPromptText("Ex: 10.5");
            
            Button btnSolicitar = new Button("Solicitar Corrida");
            Button btnCancelar = new Button("Cancelar");
            
            grid.add(lblPassageiro, 0, 1);
            grid.add(lblPassageiroNome, 1, 1);
            
            grid.add(lblMotorista, 0, 2);
            grid.add(lblMotoristaNome, 1, 2);
            
            grid.add(lblDistancia, 0, 3);
            grid.add(txtDistancia, 1, 3);
            
            grid.add(btnSolicitar, 0, 4);
            grid.add(btnCancelar, 1, 4);
            
            // Ação do botão solicitar
            btnSolicitar.setOnAction(e -> {
                try {
                    double distancia = Double.parseDouble(txtDistancia.getText());
                    if (distancia > 0) {
                        boolean sucesso = sistema.solicitarCorridaGUI(distancia);
                        if (sucesso) {
                            System.out.println("✅ Corrida solicitada com sucesso!");
                            stage.close();
                        } else {
                            System.out.println("❌ Erro ao solicitar corrida!");
                        }
                    } else {
                        System.out.println("❌ Distância deve ser maior que zero!");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("❌ Digite uma distância válida!");
                }
            });
            
            btnCancelar.setOnAction(e -> stage.close());
        }
        
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}