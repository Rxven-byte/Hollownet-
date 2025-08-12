/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Entities;

/**
 *
 * @author caleb
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false, length = 50)
    private String nombre; // username

    @NotBlank
    @Column(unique = true, nullable = false, length = 120)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String password; // BCrypt

    @Column(nullable = false)
    private boolean enabled = true;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String roles;

    public Usuario() {}

    public Usuario(String nombre, String email, String password, String roles, boolean enabled) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }

    public String[] getRoleList() {
        return roles == null ? new String[0] : roles.split(",");
    }
}
 