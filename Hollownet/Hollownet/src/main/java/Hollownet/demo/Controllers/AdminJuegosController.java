/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Controllers;

/**
 *
 * @author caleb
 */
import Hollownet.demo.Entities.Juego;
import Hollownet.demo.Repository.JuegoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class AdminJuegosController {

    private final JuegoRepository juegoRepository;

    public AdminJuegosController(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    // LISTA
    @GetMapping("/adminJuegos")
    public String listar(Model model) {
        model.addAttribute("juegos", juegoRepository.findAll());
        model.addAttribute("form", new Juego()); // para crear rápido en la misma vista
        return "adminJuegos"; // ver plantilla más abajo
    }

    // CREAR (rápido desde el mismo adminJuegos)
    @PostMapping("/adminJuegos")
    public String crear(@ModelAttribute("form") Juego form, RedirectAttributes ra) {
        try {
            if (form.getCodigo() == null || form.getCodigo().isBlank()) {
                ra.addFlashAttribute("err", "El código es obligatorio.");
                return "redirect:/adminJuegos";
            }
            if (juegoRepository.existsByCodigo(form.getCodigo())) {
                ra.addFlashAttribute("err", "Ese código ya existe.");
                return "redirect:/adminJuegos";
            }
            if (form.getPrecio() == null) form.setPrecio(new BigDecimal("0.00"));
            if (form.getStock()  == null) form.setStock(0);
            if (form.getActivo() == null) form.setActivo(true);
            juegoRepository.save(form);
            ra.addFlashAttribute("msg", "Juego creado: " + form.getTitulo());
        } catch (Exception e) {
            ra.addFlashAttribute("err", "No se pudo crear el juego.");
        }
        return "redirect:/adminJuegos";
    }

    // EDITAR (carga)
    @GetMapping("/adminJuegos/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        var juego = juegoRepository.findById(id).orElse(null);
        if (juego == null) {
            ra.addFlashAttribute("err", "Juego no encontrado.");
            return "redirect:/adminJuegos";
        }
        model.addAttribute("juegos", juegoRepository.findAll());
        model.addAttribute("form", juego);
        return "adminJuegos";
    }

    // GUARDAR EDICIÓN
    @PostMapping("/adminJuegos/{id}")
    public String guardarEdicion(@PathVariable Long id, @ModelAttribute("form") Juego form, RedirectAttributes ra) {
        var db = juegoRepository.findById(id).orElse(null);
        if (db == null) {
            ra.addFlashAttribute("err", "Juego no encontrado.");
            return "redirect:/adminJuegos";
        }
        try {
            // si cambia código, validar que no exista
            if (!db.getCodigo().equals(form.getCodigo()) && juegoRepository.existsByCodigo(form.getCodigo())) {
                ra.addFlashAttribute("err", "Ese código ya existe.");
                return "redirect:/adminJuegos";
            }
            db.setCodigo(form.getCodigo());
            db.setTitulo(form.getTitulo());
            db.setCategoria(form.getCategoria());
            db.setPrecio(form.getPrecio() != null ? form.getPrecio() : db.getPrecio());
            db.setStock(form.getStock() != null ? form.getStock() : db.getStock());
            db.setImagen(form.getImagen());
            db.setInformacion(form.getInformacion());
            db.setVideo(form.getVideo());
            db.setActivo(form.getActivo() != null ? form.getActivo() : db.getActivo());
            juegoRepository.save(db);
            ra.addFlashAttribute("msg", "Juego actualizado: " + db.getTitulo());
        } catch (Exception e) {
            ra.addFlashAttribute("err", "No se pudo actualizar el juego.");
        }
        return "redirect:/adminJuegos";
    }

    // ELIMINAR
    @PostMapping("/adminJuegos/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            var j = juegoRepository.findById(id).orElse(null);
            if (j == null) {
                ra.addFlashAttribute("err", "Juego no encontrado.");
            } else {
                juegoRepository.deleteById(id);
                ra.addFlashAttribute("msg", "Juego eliminado: " + j.getTitulo());
            }
        } catch (Exception e) {
            ra.addFlashAttribute("err", "No se pudo eliminar el juego.");
        }
        return "redirect:/adminJuegos";
    }
}
