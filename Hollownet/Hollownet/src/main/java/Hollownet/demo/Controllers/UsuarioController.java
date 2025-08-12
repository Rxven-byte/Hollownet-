/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Controllers;

import Hollownet.demo.Entities.Usuario;
import Hollownet.demo.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personas")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;

    @GetMapping("/lista")
    public String listarPersonas(Model model) {
        model.addAttribute("personas", userService.findAll());
        return "persona-list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaPersona(Model model) {
        model.addAttribute("persona", new Usuario());
        return "persona-form";
    }

    @PostMapping
    public String guardarPersona(Usuario persona) {
        userService.save(persona);
        return "redirect:/personas/lista";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarPersona(@PathVariable Long id, Model model) {
        model.addAttribute("persona", userService.getById(id));
        return "persona-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPersona(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/personas/lista";
    }
}
