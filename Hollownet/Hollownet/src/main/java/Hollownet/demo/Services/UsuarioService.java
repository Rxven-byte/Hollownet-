/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Services;

import Hollownet.demo.Entities.Usuario;
import Hollownet.demo.Repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService{
    
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) this.usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getById(long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario id) {
        return usuarioRepository.save(id);
    }

    @Override
    public Optional<Usuario> delete(long id) {
        Optional<Usuario> juego = this.getById(id);
        if(juego.isPresent()){
            this.usuarioRepository.deleteById(id);
        }
        return juego;
    }

    @Override
    public Usuario findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
    
}
