/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hollownet.demo.Repository;

/**
 *
 * @author caleb
 */
import Hollownet.demo.Entities.ContactoMensaje;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ContactoRepository extends CrudRepository<ContactoMensaje, Long> {
    List<ContactoMensaje> findAllByOrderByCreatedAtDesc();
}
