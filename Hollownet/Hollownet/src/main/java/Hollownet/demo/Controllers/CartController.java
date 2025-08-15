package Hollownet.demo.Controllers;

import Hollownet.demo.Entities.CartItem;
import Hollownet.demo.Repository.JuegoRepository;
import Hollownet.demo.Services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    public static final Logger log = LoggerFactory.getLogger(CartController.class);

    // Constantes para redirects (evita literales duplicadas)
    private static final String REDIR_TIENDA = "redirect:/Tienda";
    private static final String REDIR_CARRITO = "redirect:/Carrito";
    private static final String REDIR_CARRITO_OK = "redirect:/Carrito?ok=1";

    private final CartService cartService;
    private final JuegoRepository juegoRepository;

    @Autowired(required = false)
    private JavaMailSender mailSender; // con Mailtrap enviará; si no, simula por log

    public CartController(CartService cartService, JuegoRepository juegoRepository) {
        this.cartService = cartService;
        this.juegoRepository = juegoRepository;
    }

    // Ver carrito
    @GetMapping({"/Carrito", "/carrito"})
    public String verCarrito(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "carrito";
    }

    // Agregar desde tienda (Opción C: un solo return)
    @PostMapping("/carrito/agregar/{id}")
    public String agregar(@PathVariable Long id, RedirectAttributes ra) {
        var juego = juegoRepository.findById(id).orElse(null);

        if (juego == null) {
            ra.addFlashAttribute("err", "Juego no encontrado.");
        } else if (juego.getStock() <= 0 || !Boolean.TRUE.equals(juego.getActivo())) {
            ra.addFlashAttribute("err", "No hay keys de juegos en este momento");
        } else {
            cartService.add(new CartItem(juego.getId(), juego.getTitulo(), juego.getImagen(), juego.getPrecio()));
            ra.addFlashAttribute("msg", "Se agregó \"" + juego.getTitulo() + "\" al carrito.");
        }

        // Siempre vuelve a Tienda (un solo return)
        return REDIR_TIENDA;
    }

    // Eliminar ítem
    @PostMapping("/carrito/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        cartService.remove(id);
        ra.addFlashAttribute("msg", "Artículo eliminado del carrito.");
        return REDIR_CARRITO;
    }

    // Vaciar carrito
    @PostMapping("/carrito/limpiar")
    public String limpiar(RedirectAttributes ra) {
        cartService.clear();
        ra.addFlashAttribute("msg", "Carrito vaciado.");
        return REDIR_CARRITO;
    }

    // Finalizar compra (simulada): revalida stock, descuenta y “envía correo”
    @PostMapping("/carrito/finalizar")
    public String finalizar(Authentication auth,
            @RequestParam(required = false) String nombreTarjeta,
            @RequestParam(required = false) String numeroTarjeta,
            @RequestParam(required = false) String vencimiento,
            @RequestParam(required = false) String cvv,
            RedirectAttributes ra) {

        // destino por defecto: éxito
        String destino = REDIR_CARRITO_OK;

        if (cartService.getItems().isEmpty()) {
            ra.addFlashAttribute("err", "Tu carrito está vacío.");
            destino = REDIR_CARRITO; // error ⇒ /Carrito
        } else {
            // Revalidar stock/activo
            for (var item : cartService.getItems()) {
                var juego = juegoRepository.findById(item.getJuegoId()).orElse(null);
                if (juego == null || !Boolean.TRUE.equals(juego.getActivo()) || juego.getStock() < item.getCantidad()) {
                    ra.addFlashAttribute("err", "Sin stock para: " + (juego != null ? juego.getTitulo() : item.getTitulo()));
                    destino = REDIR_CARRITO;
                    break;
                }
            }

            // Si sigue siendo éxito, descontamos stock y “enviamos” correo
            if (destino.equals(REDIR_CARRITO_OK)) {
                for (var item : cartService.getItems()) {
                    var juego = juegoRepository.findById(item.getJuegoId()).orElseThrow();
                    juego.setStock(juego.getStock() - item.getCantidad());
                    juegoRepository.save(juego);
                }

                String usuario = (auth != null ? auth.getName() : "usuario");
                String listado = cartService.getItems().stream()
                        .map(i -> "• " + i.getTitulo() + " x" + i.getCantidad())
                        .collect(java.util.stream.Collectors.joining("\n"));
                String body = "Hola " + usuario + ",\n\nGracias por tu compra en HollowNet.\n\nJuegos:\n"
                        + listado + "\n\nTotal: " + cartService.getTotal() + "\n\n*Este es un correo de simulación*";

                try {
                    if (mailSender != null) {
                        var msg = new org.springframework.mail.SimpleMailMessage();
                        msg.setTo("test@hollownet.local"); 
                        msg.setSubject("HollowNet - Confirmación de compra (simulación)");
                        msg.setText(body);
                        mailSender.send(msg);
                    } else {
                        org.slf4j.LoggerFactory.getLogger(getClass()).info("[EMAIL SIMULADO]\n{}", body);
                    }
                } catch (org.springframework.mail.MailException ex) {
                    org.slf4j.LoggerFactory.getLogger(getClass()).warn("Fallo al enviar correo simulado: {}", ex.getMessage());
                }

                cartService.clear();
                ra.addFlashAttribute("msg", "¡Compra finalizada! Revisa tu bandeja (Mailtrap).");
            }
        }

        return destino; // ahora NO es invariante
    }
}
