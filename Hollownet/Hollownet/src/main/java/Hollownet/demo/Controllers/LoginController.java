package Hollownet.demo.Controllers;

import Hollownet.demo.Entities.Usuario;
import Hollownet.demo.Repository.UsuarioRepository;
import static org.hibernate.query.sqm.tree.SqmNode.log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // -------- LOGIN (GET) --------
    @GetMapping("/Login")
    public String login(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/"; // ya logueado → home
        }
        return "Login";
    }

    // -------- REGISTRO (GET) --------
    @GetMapping("/Registro")
    public String registro(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/";
        }
        return "Registro";
    }

    // -------- REGISTRO (POST) --------
    @PostMapping("/Registro")
    public String registrar(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword
    ) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/Registro?error=pass";
        }

        try {
            Usuario u = new Usuario();
            u.setNombre(nombre.trim());
            u.setEmail(email.trim());
            u.setPassword(passwordEncoder.encode(password));
            u.setEnabled(true);
            u.setRoles("USER");  

            usuarioRepository.save(u);
        } catch (DataIntegrityViolationException e) {
            // nombre/email únicos ya registrados
            return "redirect:/Registro?error=exists";
       } catch (Exception e) {
            log.error("[Registro] Falló la creación de usuario", e);
            return "redirect:/Registro?error=gen";
        }

        return "redirect:/Login?registered=true";
    }
}
