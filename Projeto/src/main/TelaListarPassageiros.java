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

import java.util.List;
import java.util.stream.Collectors;

public class TelaListarPassageiros {

    private final SistemaUberLand sistema;
    private final Stage stage;
    private VBox listaContainer;
    private TextField campoBusca;

    public TelaListarPassageiros(SistemaUberLand sistema) {
        this.sistema = sistema;
        this.stage = new Stage();
    }

    public void mostrar() {
        stage.setTitle("👥 Passageiros - UberLand");

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        borderPane.setTop(criarCabecalho());
        borderPane.setCenter(criarConteudo());
        borderPane.setBottom(criarRodape());

        Scene scene = new Scene(borderPane, 750, 550);
        stage.setScene(scene);
        stage.show();
    }

    private VBox criarCabecalho() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);

        Label titulo = new Label("👥 PASSAGEIROS");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.web("#2196F3"));

        campoBusca = new TextField();
        campoBusca.setPromptText("🔍 Procurar passageiro por nome ou CPF...");
        campoBusca.setMaxWidth(400);

        campoBusca.textProperty().addListener((obs, oldVal, newVal) -> atualizarLista());

        box.getChildren().addAll(titulo, campoBusca, new Separator());
        return box;
    }

    private ScrollPane criarConteudo() {
        listaContainer = new VBox(10);
        atualizarLista();

        ScrollPane scroll = new ScrollPane(listaContainer);
        scroll.setFitToWidth(true);
        return scroll;
    }

    private void atualizarLista() {
        listaContainer.getChildren().clear();

        List<Passageiro> filtrados = sistema.getPassageiros().stream()
                .filter(p -> p.getNome().toLowerCase().contains(campoBusca.getText().toLowerCase())
                        || p.getCpf().contains(campoBusca.getText()))
                .collect(Collectors.toList());

        if (filtrados.isEmpty()) {
            Label vazio = new Label("Nenhum passageiro encontrado.");
            vazio.setPadding(new Insets(20));
            listaContainer.getChildren().add(vazio);
            return;
        }

        int i = 1;
        for (Passageiro p : filtrados) {
            listaContainer.getChildren().add(criarLinhaPassageiro(p, i++));
        }
    }

    private GridPane criarLinhaPassageiro(Passageiro p, int numero) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setPadding(new Insets(10));
        grid.setStyle("-fx-background-color:#F5F5F5; -fx-background-radius:5;");

        Label lblNum = new Label(String.valueOf(numero));
        Label lblNome = new Label(p.getNome());
        Label lblCpf = new Label(p.getCpf());

        Button btnEditar = new Button("✏");
        btnEditar.setOnAction(e -> editarPassageiro(p));

        Button btnExcluir = new Button("🗑");
        btnExcluir.setOnAction(e -> excluirPassageiro(p));

        grid.add(lblNum, 0, 0);
        grid.add(lblNome, 1, 0);
        grid.add(lblCpf, 2, 0);
        grid.add(new HBox(5, btnEditar, btnExcluir), 3, 0);

        return grid;
    }

    private void editarPassageiro(Passageiro p) {
        TextInputDialog dialog = new TextInputDialog(p.getNome());
        dialog.setTitle("Editar Passageiro");
        dialog.setHeaderText("Editar nome do passageiro");
        dialog.setContentText("Novo nome:");

        dialog.showAndWait().ifPresent(novoNome -> {
            p.setNome(novoNome);
            atualizarLista();
        });
    }

    private void excluirPassageiro(Passageiro p) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Excluir");
        confirm.setHeaderText("Deseja remover este passageiro?");
        confirm.setContentText(p.getNome());

        confirm.showAndWait().ifPresent(resp -> {
            if (resp == ButtonType.OK) {
                sistema.getPassageiros().remove(p);
                atualizarLista();
            }
        });
    }

    private HBox criarRodape() {
        HBox rodape = new HBox(10);
        rodape.setAlignment(Pos.CENTER_RIGHT);

        Button fechar = new Button("Fechar");
        fechar.setOnAction(e -> stage.close());

        rodape.getChildren().add(fechar);
        return rodape;
    }
}
