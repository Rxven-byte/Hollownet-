/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Hollownet.demo.Services;

import Hollownet.demo.Entities.Usuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author PC
 */
public interface IUsuarioService {
    
    public List<Usuario> findAll();

    public Optional<Usuario> getById ( long id);

    public Usuario save(Usuario id);

    public Optional<Usuario> delete(long id);
    
    public Usuario findByNombre(String nombre);
    
}
