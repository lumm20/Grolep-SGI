package mx.itson.sgi.data_access.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Security {
    private BCryptPasswordEncoder encoder;

    public Security() {
        this.encoder = new BCryptPasswordEncoder(12);
    }

    public String encodePassword(String password)  {
        return encoder.encode(password);
    }

    public boolean authenticate(String password, String passwordEncoded){
        return encoder.matches(password, passwordEncoded);
    }
}
