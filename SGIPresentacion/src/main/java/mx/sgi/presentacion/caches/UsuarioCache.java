package mx.sgi.presentacion.caches;

import mx.itson.sgi.dto.AuthenticationResponse;
import mx.itson.sgi.dto.UsuarioDTO;

public class UsuarioCache {

    private static volatile UsuarioDTO instance;
    private static volatile AuthenticationResponse session;

    private UsuarioCache() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized UsuarioDTO getInstance() {
        if (instance == null) {
            instance = new UsuarioDTO();
        }
        return instance;
    }
    public static synchronized AuthenticationResponse getSession() {
        if (session == null) {
            session = new AuthenticationResponse();
        }
        return session;
    }

    public static void setInstance(UsuarioDTO usuario){
        UsuarioCache.instance = usuario;
    }

    public static void setSession(AuthenticationResponse session){
        UsuarioCache.session = session;
    }

    public static void limpiarInstancia() {
        instance = null;
    }
    public static void clearSession() {
        session = null;
    }
}

