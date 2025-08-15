/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Controllers;

/**
 *
 * @author caleb
 */
import Hollownet.demo.Entities.ContactoMensaje;
import Hollownet.demo.Repository.ContactoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    // Mostrar formulario
    @GetMapping("/Contacto")
    public String contacto(Model model) {
        return "Contacto"; // templates/Contacto.html
    }

    // Guardar mensaje
    @PostMapping("/Contacto")
    public String enviar(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String mensaje,
                         RedirectAttributes ra) {

        if (isBlank(username) || isBlank(email) || isBlank(mensaje)) {
            ra.addFlashAttribute("error", "Debes llenar todos los campos.");
            return "redirect:/Contacto";
        }

        ContactoMensaje cm = new ContactoMensaje();
        cm.setNombre(username.trim());
        cm.setEmail(email.trim());
        cm.setMensaje(mensaje.trim());
        contactoRepository.save(cm);

        ra.addFlashAttribute("ok", "¡Mensaje enviado con éxito!");
        return "redirect:/Contacto";
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // ===== ADMIN =====

    // Listar mensajes
    @GetMapping("/adminContacto")
    public String adminContacto(Model model) {
        model.addAttribute("mensajes", contactoRepository.findAllByOrderByCreatedAtDesc());
        return "adminContacto"; // templates/adminContacto.html
    }

    // Eliminar mensaje
    @PostMapping("/adminContacto/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        var found = contactoRepository.findById(id).orElse(null);
        if (found == null) {
            ra.addFlashAttribute("err", "El mensaje no existe.");
        } else {
            contactoRepository.deleteById(id);
            ra.addFlashAttribute("msg", "Mensaje eliminado.");
        }
        return "redirect:/adminContacto";
    }
}
