module mx.sgi.presentacion.main.sgipresentacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.jfoenix;
    requires java.net.http;
    requires com.google.gson;
    requires fontawesomefx;
    requires java.desktop;
    requires mx.itson.sgi.dto;
    requires MaterialFX;
    requires org.kordamp.ikonli.materialdesign;

    opens mx.sgi.presentacion.main to javafx.fxml;
    exports mx.sgi.presentacion.main;
    exports mx.sgi.presentacion.controladores;
    opens mx.sgi.presentacion.controladores to javafx.fxml;
}
