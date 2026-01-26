package main;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
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
        stage.setTitle("Lista de Motoristas");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        
        Label titulo = new Label("Motoristas Cadastrados:");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        vbox.getChildren().add(titulo);
        
        // Lista os motoristas
        if (sistema.getMotoristas().isEmpty()) {
            vbox.getChildren().add(new Label("Nenhum motorista cadastrado."));
        } else {
            for (Motorista m : sistema.getMotoristas()) {
                Label info = new Label(
                    "🚗 " + m.getNome() + 
                    "\n   CPF: " + m.getCpf() + 
                    "\n   CNH: " + m.getCnh() +
                    "\n   Email: " + m.getEmail() +
                    "\n   Celular: " + m.getCelular()
                );
                info.setStyle("-fx-padding: 5; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");
                vbox.getChildren().add(info);
            }
        }
        
        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> stage.close());
        
        vbox.getChildren().add(btnFechar);
        
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        
        Scene scene = new Scene(scrollPane, 500, 400);
        stage.setScene(scene);
        stage.show();
    }
}