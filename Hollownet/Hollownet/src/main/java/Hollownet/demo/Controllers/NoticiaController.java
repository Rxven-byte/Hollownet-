package Hollownet.demo.Controllers;

import Hollownet.demo.Entities.Noticia;
import Hollownet.demo.Services.NoticiaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    // Muestra la lista de noticias existentes (público)
    @GetMapping("")
    public String mostrarNoticias(Model model) {
        List<Noticia> noticias = noticiaService.findAll();
        model.addAttribute("listaNoticias", noticias);
        return "Noticias";
    }

    // Muestra el formulario para crear o editar noticias y la tabla (solo admin)
    @GetMapping("/gestionar")
    public String mostrarFormulario(Model model, @ModelAttribute("successMessage") String successMessage, @ModelAttribute("noticiaForm") Noticia noticiaForm) {
        // Obtenemos todas las noticias de la base de datos
        List<Noticia> noticias = noticiaService.findAll();
        model.addAttribute("noticias", noticias);
        
        // Si el formulario ya tiene una noticia (para edición), se usa esa.
        // Si no, se crea una nueva.
        if (noticiaForm.getId() == null) {
            model.addAttribute("noticia", new Noticia());
        } else {
            model.addAttribute("noticia", noticiaForm);
        }

        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }
        return "AdminNoticias";
    }
    
    // Método para cargar los datos de una noticia en el formulario para su edición
    @GetMapping("/gestionar/{id}/editar")
    public String editarNoticia(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        noticiaService.getById(id).ifPresent(noticia -> redirectAttributes.addFlashAttribute("noticiaForm", noticia));
        return "redirect:/noticias/gestionar";
    }

    // Maneja la solicitud POST para guardar o actualizar una noticia
    @PostMapping("/guardarNoticia")
    public String guardarNoticia(@ModelAttribute("noticia") Noticia noticia, RedirectAttributes redirectAttributes) {
        noticiaService.save(noticia);
        redirectAttributes.addFlashAttribute("successMessage", "Noticia guardada exitosamente");
        return "redirect:/noticias/gestionar";
    }

    // Método para eliminar una noticia
    @PostMapping("/gestionar/{id}/eliminar")
    public String eliminarNoticia(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        noticiaService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Noticia eliminada exitosamente");
        return "redirect:/noticias/gestionar";
    }
}




/*package Hollownet.demo.Controllers;

import Hollownet.demo.Entities.Noticia;
import Hollownet.demo.Services.NoticiaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    // Muestra la lista de noticias existentes (público)
    @GetMapping("/noticia")
    public String mostrarNoticias(Model model) {
        List<Noticia> noticias = noticiaService.findAll();
        model.addAttribute("listaNoticias", noticias);
        return "Noticias";
    }

    // Muestra el formulario para crear o editar noticias y la tabla (solo admin)
    @GetMapping("")
    public String mostrarFormulario(Model model, @ModelAttribute("successMessage") String successMessage) {
        // 1. Añadimos un objeto Noticia vacío para el formulario de creación
        model.addAttribute("noticia", new Noticia());

        // 2. Obtenemos todas las noticias de la base de datos
        List<Noticia> noticias = noticiaService.findAll();

        // 3. Añadimos la lista de noticias al modelo para que el HTML pueda mostrarla en la tabla
        model.addAttribute("noticias", noticias);

        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }
        return "AdminNoticias";
    }

    // Método para editar una noticia. Carga los datos de la noticia en el formulario.
    @GetMapping("/{id}/editar")
    public String editarNoticia(@PathVariable Long id, Model model) {
        // Buscamos la noticia por su ID y la agregamos al modelo para que el formulario la muestre
        noticiaService.getById(id).ifPresent(noticia -> model.addAttribute("noticia", noticia));
        
        // También necesitamos la lista de noticias para la tabla
        List<Noticia> noticias = noticiaService.findAll();
        model.addAttribute("noticias", noticias);

        return "AdminNoticias";
    }

    // Maneja la solicitud POST para guardar o actualizar una noticia
    @PostMapping("/guardarNoticia")
    public String guardarNoticia(@ModelAttribute Noticia noticia, RedirectAttributes redirectAttributes) {
        noticiaService.save(noticia);
        redirectAttributes.addFlashAttribute("successMessage", "Noticia guardada exitosamente");
        return "redirect:/adminNoticias";
    }

    // Método para eliminar una noticia
    @PostMapping("/{id}/eliminar")
    public String eliminarNoticia(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        noticiaService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Noticia eliminada exitosamente");
        return "redirect:/adminNoticias";
    }
    
    






















}*/
/*package Hollownet.demo.Controllers;

import Hollownet.demo.Entities.Noticia;
import Hollownet.demo.Services.NoticiaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    // Muestra la lista de noticias existentes (público)
    @GetMapping("/noticia")
    public String mostrarNoticias(Model model) {
        List<Noticia> noticias = noticiaService.findAll();
        model.addAttribute("listaNoticias", noticias);
        return "Noticias";
    }

    // Muestra el formulario para crear o editar noticias (solo admin)
    @GetMapping("/gestNoticias")
    public String mostrarFormulario(Model model, @ModelAttribute("successMessage") String successMessage) {
        // 1. Añadimos el objeto Noticia para el formulario
        model.addAttribute("noticia", new Noticia()); 

        // 2. Obtenemos todas las noticias de la base de datos
        List<Noticia> noticias = noticiaService.findAll();

        // 3. Añadimos la lista de noticias al modelo para que el HTML pueda mostrarla
        model.addAttribute("noticias", noticias);

        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }
        return "AdminNoticias";
    }

    // Maneja la solicitud POST para guardar una noticia (solo admin)
    @PostMapping("/admin/guardarNoticia")
    public String guardarNoticia(@ModelAttribute Noticia noticia, RedirectAttributes redirectAttributes) {
        noticiaService.save(noticia);
        redirectAttributes.addFlashAttribute("successMessage", "Noticia guardada exitosamente");
        return "redirect:/noticias/gestNoticias";
    }
}
*/