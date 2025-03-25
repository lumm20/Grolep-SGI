module mx.itson.sgi.dto {

    requires static lombok;
    requires com.google.gson;
    exports mx.itson.sgi.dto;
    opens mx.itson.sgi.dto to com.google.gson;
    exports mx.itson.sgi.dto.vistas;
}