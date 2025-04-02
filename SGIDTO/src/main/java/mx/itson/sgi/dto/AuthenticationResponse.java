package mx.itson.sgi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// AuthenticationResponse.java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String nombreUsuario;
    private String correoUsuario;
    private Long idUsuario;
    private String error;
}