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
public class JuegoService {
    
    @Autowired
    private JuegoRepository juegoRepository;
    
    
    public List<Juego> findAll(){
        return (List<Juego>) this.juegoRepository.findAll();
    }
    
    public Juego save (Juego juego){
        return juegoRepository.save(juego);
    }
    
    public Optional<Juego> getById(Long id){
        return juegoRepository.findById(id);
    }
    
    @Transactional
    public Optional<Juego> delete(Long Id) {
        Optional<Juego> juego = this.getById(Id);
        if(juego.isPresent()){
            this.juegoRepository.deleteById(Id);
        }
        return juego;
    }
    
}
