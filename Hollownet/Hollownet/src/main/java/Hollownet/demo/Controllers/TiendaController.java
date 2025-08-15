/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Controllers;

import Hollownet.demo.Repository.JuegoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author caleb
 */


@Controller
public class TiendaController {

    private final JuegoRepository juegoRepository;

    public TiendaController(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    @GetMapping({"/Tienda", "/tienda"})
    public String tienda(Model model) {
        model.addAttribute("juegos", juegoRepository.findByActivoTrueOrderByCreatedAtDesc());
        return "tienda"; // usa templates/tienda.html
    }
}
