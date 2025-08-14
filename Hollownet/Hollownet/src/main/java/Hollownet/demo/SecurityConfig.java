package Hollownet.demo;

import Hollownet.demo.Services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppAuthenticationSuccessHandler successHandler;
    private final UserService userService; // implements UserDetailsService

    public SecurityConfig(AppAuthenticationSuccessHandler successHandler, UserService userService) {
        this.successHandler = successHandler;
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Qué rutas se permiten sin login
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/Login", "/register", "/Registro", "/Registro.html",
                        "/css/**", "/js/**", "/images/**", "/webjars/**")
                .permitAll()
                .anyRequest().authenticated()
                )
                // Config de formulario de login
                .formLogin(form -> form
                .loginPage("/Login").permitAll() // tu página de login 
                .loginProcessingUrl("/login") // a dónde hace POST el formulario
                .successHandler(successHandler) 
                .failureUrl("/Login?error=true")
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/Login?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
                )
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
