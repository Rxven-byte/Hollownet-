/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Entities;

/**
 *
 * @author caleb
 */
import java.math.BigDecimal;

public class CartItem {
    private Long juegoId;
    private String titulo;
    private String imagen;
    private BigDecimal precio;
    private int cantidad = 1;

    public CartItem() {}

    public CartItem(Long juegoId, String titulo, String imagen, BigDecimal precio) {
        this.juegoId = juegoId;
        this.titulo = titulo;
        this.imagen = imagen;
        this.precio = precio;
    }

    public Long getJuegoId() { return juegoId; }
    public String getTitulo() { return titulo; }
    public String getImagen() { return imagen; }
    public BigDecimal getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = Math.max(1, cantidad); }

    public BigDecimal getSubtotal() {
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }
}
