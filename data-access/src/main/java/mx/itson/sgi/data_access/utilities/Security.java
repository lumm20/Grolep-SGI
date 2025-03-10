package mx.itson.sgi.data_access.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Security {
    private BCryptPasswordEncoder encoder;

    public Security() {
        this.encoder = new BCryptPasswordEncoder();
    }

    public String encode(String password)  {
        return encoder.encode(password);
    }

    public boolean authenticate(String password, String passwordEncoded){
        return encoder.matches(password, passwordEncoded);
    }
}
