package mx.itson.sgi.data_access.utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {
    private BCryptPasswordEncoder encoder;
    @Autowired
    private DetallesUsuarios details;

    public Security() {
        this.encoder = new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/users/register",
                 "api/users/login","api/students",
                 "api/payment","api/fees","api/fees/actual-cycle","api/fees/debit-details").permitAll()
                .anyRequest().authenticated())
            .userDetailsService(details)
            .httpBasic(withDefaults());
        return http.build();
    }
    
    public String encodePassword(String password)  {
        return encoder.encode(password);
    }

    public boolean authenticate(String password, String passwordEncoded){
        return encoder.matches(password, passwordEncoded);
    }
}
