module mx.itson.sgi.reportes {
    requires static lombok;
    requires mx.itson.sgi.dto;
    requires net.sf.jasperreports.core;
    requires spring.core;

    exports mx.itson.sgi.reportes;
    exports mx.itson.sgi.reportes.generador;
}
