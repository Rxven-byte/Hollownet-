/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Hollownet.demo.Repository;

import Hollownet.demo.Entities.Juego;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author PC
 */




public interface JuegoRepository extends JpaRepository<Juego, Long> {
    List<Juego> findByCodigoIn(Collection<String> codigos);
    List<Juego> findByActivoTrueOrderByCreatedAtDesc();
    boolean existsByCodigo(String codigo);
}
