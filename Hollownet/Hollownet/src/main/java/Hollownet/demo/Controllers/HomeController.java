/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Controllers;

/**
 *
 * @author caleb
 */
import Hollownet.demo.Repository.JuegoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    private final JuegoRepository juegoRepository;

    public HomeController(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Códigos destacados para el slider (1, 2 y 3)
        List<String> codigos = Arrays.asList("1", "2", "3");

        // Requiere el método en el repositorio:
        // List<Juego> findByCodigoIn(Collection<String> codigos);
        var destacados = juegoRepository.findByCodigoIn(codigos);

        model.addAttribute("destacados", destacados);
        return "index";
    }
}
