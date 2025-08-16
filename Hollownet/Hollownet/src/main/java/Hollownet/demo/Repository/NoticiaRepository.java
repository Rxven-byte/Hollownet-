package Hollownet.demo.Repository;

/**
 *
 * @author Antonio
 */


import Hollownet.demo.Entities.Noticia;
import org.springframework.data.repository.CrudRepository; 
/*import org.springframework.stereotype.Repository;



@Repository */
public interface NoticiaRepository extends CrudRepository<Noticia, Long>{
    
}
