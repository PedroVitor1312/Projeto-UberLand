module Projeto {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.fxml;          // Corrigido
    opens controller to javafx.fxml;    // Certo
    // opens model to javafx.base;      // Remover, geralmente não é necessário abrir model

    exports main;
    exports controller;
    exports model;                      // Mantém apenas se você precisa realmente exportar
}
