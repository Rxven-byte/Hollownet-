package Hollownet.demo.Config;

import Hollownet.demo.Security.AppAuthenticationSuccessHandler;
import Hollownet.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService; // implements UserDetailsService

    public SecurityConfig(AppAuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // <-- sin args
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(a -> a
                .requestMatchers(
                    "/", "/index.html", "/Inicio.html", "/Contacto.html", "/Acerca.html",
                    "/Tienda.html", "/noticias.html",
                    "/css/**", "/js/**", "/images/**", "/webjars/**",
                    "/Registro.html", "/Login"
                ).permitAll()
                .requestMatchers(
                    "/DetalleTienda.html", "/carrito.html", "/carritofinal.html",
                    "/libreria.html", "/detalle-noticia.html"
                ).hasAnyRole("USER","ADMIN")
                .requestMatchers("/admin.html", "/admin-**.html").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(f -> f
                .loginPage("/Login")
                .successHandler(successHandler)
                .permitAll()
            )
            .logout(l -> l
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index.html")
                .permitAll()
            );
        return http.build();
    }
}
