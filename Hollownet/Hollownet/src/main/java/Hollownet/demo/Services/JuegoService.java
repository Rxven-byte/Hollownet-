/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Services;

import Hollownet.demo.Entities.Juego;
import Hollownet.demo.Repository.JuegoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JuegoService implements IJuegoService{
    
    @Autowired
    private JuegoRepository juegoRepository;
    
    
    @Override
    public List<Juego> findAll(){
        return (List<Juego>) this.juegoRepository.findAll();
    }
    
    @Override
    public Juego save (Juego juego){
        return juegoRepository.save(juego);
    }
    
    @Override
    public Optional<Juego> getById(long id) {
        return juegoRepository.findById(id);
    }
    
    @Transactional
    @Override
    public Optional<Juego> delete(long id) {
        Optional<Juego> juego = this.getById(id);
        if(juego.isPresent()){
            this.juegoRepository.deleteById(id);
        }
        return juego;
    }

    @Override
    public Juego findByNombre(String nombre) {
        return juegoRepository.findByNombre(nombre);
    }
    
}
