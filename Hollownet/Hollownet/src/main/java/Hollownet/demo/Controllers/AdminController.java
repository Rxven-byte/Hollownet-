/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Controllers;

/**
 *
 * @author caleb
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping({"/admin", "/admin/", "/admin/index"})
    public String adminHome() {
        // Renderiza: src/main/resources/templates/admin.html
        return "admin";
    }
}
