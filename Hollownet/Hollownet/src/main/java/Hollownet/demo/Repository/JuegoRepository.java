/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Hollownet.demo.Repository;

import Hollownet.demo.Entities.Juego;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author PC
 */
public interface JuegoRepository extends CrudRepository<Juego, Long>{
    Juego findByNombre(String nombre);
}