/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Services;

/**
 *
 * @author caleb
 */
import Hollownet.demo.Entities.CartItem;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    public void add(CartItem item) {
        CartItem existing = items.get(item.getJuegoId());
        if (existing == null) {
            items.put(item.getJuegoId(), item);
        } else {
            existing.setCantidad(existing.getCantidad() + 1);
        }
    }

    public void remove(Long juegoId) {
        items.remove(juegoId);
    }

    public void clear() {
        items.clear();
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public int getCount() {
        return items.values().stream().mapToInt(CartItem::getCantidad).sum();
    }

    public BigDecimal getTotal() {
        return items.values().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean contains(Long juegoId) {
        return items.containsKey(juegoId);
    }
}
