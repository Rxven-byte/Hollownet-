package Hollownet.demo.Services;

/**
 *
 * @author Antonio
 */

import Hollownet.demo.Entities.Noticia;
import java.util.List;   
import java.util.Optional; 

public interface INoticiaService {
    
    public List<Noticia> findAll();

  
    public Noticia save(Noticia noticia);

   
    public Optional<Noticia> getById(Long id);


    public Optional<Noticia> delete(Long id);


    public Optional<Noticia> update(Long id, Noticia noticiaActualizada);
}
    

