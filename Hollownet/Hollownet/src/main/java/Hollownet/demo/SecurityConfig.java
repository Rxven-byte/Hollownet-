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
                // Rutas públicas
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/", "/index", "/index.html",
                        "/Login", "/Registro", "/Registro.html",
                        "/Tienda", "/tienda", "/tienda.html",
                        "/DetalleTienda", "/DetalleTienda.html",
                        "/noticias", "/noticias.html",
                        "/Contacto", "/Contacto.html",
                        "/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico"
                ).permitAll()
                // Zona admin
                .requestMatchers(
                        "/admin/**",
                        "/adminUsuario", "/adminUsuario/**",
                        "/adminJuegos", "/adminJuegos/**"
                ).hasRole("ADMIN")
                // Carrito (requiere login)
                .requestMatchers("/Carrito", "/carrito", "/carrito/**").authenticated()
                // Lo demás, autenticado
                .anyRequest().authenticated()
                )
                // Login form
                .formLogin(form -> form
                .loginPage("/Login").permitAll() // GET /Login (vista)
                .loginProcessingUrl("/login") // POST /login (procesa creds)
                .successHandler(successHandler) // redirige /admin o /
                .failureUrl("/Login?error=true")
                )
                // Logout
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/Login?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
                )
                // Proveedor de autenticación
                .authenticationProvider(authenticationProvider());

        // CSRF habilitado por defecto (correcto para formularios)
        return http.build();
    }
}
