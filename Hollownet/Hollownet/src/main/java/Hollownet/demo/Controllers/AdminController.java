/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Controllers;

/**
 *
 * @author caleb
 */


import Hollownet.demo.Repository.UsuarioRepository;   // <- tu interfaz real
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    private static final String VIEW_ADMIN = "admin";
    private static final String VIEW_ADMIN_USUARIOS = "adminUsuario";
    private static final String REDIRECT_ADMIN_USUARIOS = "redirect:/adminUsuario";

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Panel principal -> templates/admin.html
    @GetMapping({"/admin", "/admin/", "/admin/index"})
    public String adminHome() {
        return VIEW_ADMIN;
    }

    // Listado -> templates/adminUsuario.html
    @GetMapping("/adminUsuario")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return VIEW_ADMIN_USUARIOS;
    }

    // Eliminar usuario y volver a la lista
    @PostMapping("/adminUsuario/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Long id,
                                  Authentication auth,
                                  RedirectAttributes ra) {
        try {
            var actual = (auth != null ? auth.getName() : null);
            var u = usuarioRepository.findById(id).orElse(null);
            if (u == null) {
                ra.addFlashAttribute("err", "El usuario no existe.");
                return REDIRECT_ADMIN_USUARIOS;
            }
            if (actual != null && actual.equalsIgnoreCase(u.getNombre())) {
                ra.addFlashAttribute("err", "No puedes eliminar tu propio usuario.");
                return REDIRECT_ADMIN_USUARIOS;
            }
            usuarioRepository.deleteById(id);
            ra.addFlashAttribute("msg", "Usuario eliminado: " + u.getNombre());
        } catch (Exception e) {
            ra.addFlashAttribute("err", "No se pudo eliminar el usuario.");
        }
        return REDIRECT_ADMIN_USUARIOS;
    }
    
    
}
