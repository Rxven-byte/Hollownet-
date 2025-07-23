/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Controllers;

import Hollownet.demo.Entities.Juego;
import Hollownet.demo.Services.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/juegos")
public class JuegoController {
    @Autowired
    private JuegoService juegoService;
    
    //metodo para mostrar una vista con un listado de los juegos disponibles
    @GetMapping("/lista")
    public String listarJuegos(Model model){
        model.addAttribute("juegos", juegoService.findAll());
        return "ejemplo"; //Aqui va el archivo html con la lista de juegos disponibles
    }
    //metodo para devolver una vista con un tipo de formulario para administradores que permita agregar un nuevo juego
    @GetMapping("/nuevo")
    public String agregarNuevoJuego(Model model){
        model.addAttribute("juego", new Juego());
        return "ejemplo";
    }
    //metodo para usar el crear y guardar un nuevo juego
    @PostMapping
    public String guardarJuego(Juego juego){
        juegoService.save(juego);
        return "ejemplo";
    }
    //metodo para llamar modificar un juego ya añadido
    @GetMapping("/editar/{id}")
    public String editarJuego(@PathVariable Long id, Model model){
        model.addAttribute("juego", juegoService.getById(id));
        return "ejemplo";
    }
    //metodo para eliminar un juego de la base de datos por id
    @GetMapping("/eliminar/{id}")
    public String eliminarJuego(@PathVariable Long id){
        juegoService.delete(id);
        return "ejemplo";
    }
}
