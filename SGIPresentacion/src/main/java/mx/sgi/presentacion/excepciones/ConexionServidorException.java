package mx.sgi.presentacion.excepciones;

public class ConexionServidorException extends Exception{
    public ConexionServidorException(String mensaje) {
        super(mensaje);
    }

    public ConexionServidorException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
