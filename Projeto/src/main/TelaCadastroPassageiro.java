package main;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import controller.SistemaUberLand;

public class TelaCadastroPassageiro {
    
    private final SistemaUberLand sistema;
    private final Stage stage;
    
    public TelaCadastroPassageiro(SistemaUberLand sistema) {
        this.sistema = sistema;
        this.stage = new Stage();
    }
    
    private void mostrarAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    public void mostrar() {
        stage.setTitle("Cadastrar Passageiro");
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        
        Label lblNome = new Label("Nome:");
        TextField txtNome = new TextField();
        
        Label lblCPF = new Label("CPF:");
        TextField txtCPF = new TextField();
        
        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        
        Label lblCelular = new Label("Celular:");
        TextField txtCelular = new TextField();
        
        Label lblPagamento = new Label("Forma de Pagamento:");
        ComboBox<String> comboPagamento = new ComboBox<>();
        comboPagamento.getItems().addAll("DINHEIRO", "CARTAO", "PIX");
        comboPagamento.setValue("CARTAO");
        
        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        
        grid.add(lblNome, 0, 0);
        grid.add(txtNome, 1, 0);
        
        grid.add(lblCPF, 0, 1);
        grid.add(txtCPF, 1, 1);
        
        grid.add(lblEmail, 0, 2);
        grid.add(txtEmail, 1, 2);
        
        grid.add(lblCelular, 0, 3);
        grid.add(txtCelular, 1, 3);
        
        grid.add(lblPagamento, 0, 4);
        grid.add(comboPagamento, 1, 4);
        
        grid.add(btnSalvar, 0, 5);
        grid.add(btnCancelar, 1, 5);
        
        btnSalvar.setOnAction(e -> {
            if (txtNome.getText().trim().isEmpty()) {
                mostrarAlerta("Atenção", "Digite o nome!", AlertType.WARNING);
                txtNome.requestFocus();
                return;
            }
            
            if (txtCPF.getText().trim().isEmpty()) {
                mostrarAlerta("Atenção", "Digite o CPF!", AlertType.WARNING);
                txtCPF.requestFocus();
                return;
            }
            
            boolean sucesso = sistema.cadastrarPassageiroGUI(
                txtNome.getText().trim(),
                txtCPF.getText().trim(),
                txtEmail.getText().trim(),
                txtCelular.getText().trim(),
                comboPagamento.getValue()
            );
            
            if (sucesso) {
                mostrarAlerta("Sucesso", "Passageiro cadastrado!", AlertType.INFORMATION);
                stage.close();
            } else {
                mostrarAlerta("CPF Inválido", "CPF inválido! Digite novamente.", AlertType.ERROR);
                txtCPF.requestFocus();
                txtCPF.selectAll();
            }
        });
        
        btnCancelar.setOnAction(e -> stage.close());
        
        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}