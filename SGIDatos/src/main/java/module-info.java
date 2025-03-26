module mx.sgi.datos {
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;

    exports mx.sgi.datos;
    exports mx.sgi.datos.repositorios;
    exports mx.sgi.datos.interfaces;
}