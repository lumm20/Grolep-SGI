module mx.itson.sgi.reportes {
    requires static lombok;
    requires mx.itson.sgi.dto;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires net.sf.jasperreports.pdf;
    requires spring.core;
    requires net.sf.jasperreports.jdt;

    exports mx.itson.sgi.reportes;
    exports mx.itson.sgi.reportes.generador;
}
