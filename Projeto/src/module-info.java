module Projeto {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens main to javafx.graphics;
    opens controller to javafx.fxml;
    opens model to javafx.base;
    
    exports main;
    exports controller;
    exports model;
}