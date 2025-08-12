/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Hollownet.demo.Services;

import Hollownet.demo.Entities.Juego;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author PC
 */
public interface IJuegoService {

    public List<Juego> findAll();

    public Optional<Juego> getById ( long id);

    public Juego save(Juego id);

    public Optional<Juego> delete(long id);
    
    public Juego findByNombre(String nombre);
}
    

