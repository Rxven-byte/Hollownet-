// Archivo: src/main/java/Hollownet/demo/Services/NoticiaService.java

package Hollownet.demo.Services;

import Hollownet.demo.Entities.Noticia;
import Hollownet.demo.Repository.NoticiaRepository;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticiaService implements INoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Override
    @Transactional
    public Noticia save(Noticia noticia) {
        // Se ejecuta solo si la noticia es nueva
        if (noticia.getId() == null) {
            LocalDate fechaActual = LocalDate.now();
            String dia = String.valueOf(fechaActual.getDayOfMonth());
            String mes = fechaActual.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-ES"));
            String year = String.valueOf(fechaActual.getYear());

            String fechaFormateada = dia + " de " + mes + " de " + year;
            // La fecha se establece aquí, de forma automática
            noticia.setFecha(fechaFormateada);
        }
        return this.noticiaRepository.save(noticia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Noticia> findAll() {
        return (List<Noticia>) this.noticiaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Noticia> getById(Long id) {
        return this.noticiaRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Noticia> delete(Long id) {
        Optional<Noticia> noticia = this.getById(id);
        if (noticia.isPresent()) {
            this.noticiaRepository.deleteById(id);
        }
        return noticia;
    }

    @Override
    @Transactional
    public Optional<Noticia> update(Long id, Noticia noticiaActualizada) {
        Optional<Noticia> noticiaExistente = this.getById(id);

        if (noticiaExistente.isPresent()) {
            Noticia noticia = noticiaExistente.get();
            noticia.setTitulo(noticiaActualizada.getTitulo());
            noticia.setContenido(noticiaActualizada.getContenido());
            noticia.setUrlImagen(noticiaActualizada.getUrlImagen());
            noticia.setUrlVideoYoutube(noticiaActualizada.getUrlVideoYoutube());
            // La fecha no se actualiza para mantener la fecha original de publicación
            
            return Optional.of(this.noticiaRepository.save(noticia));
        } else {
            return Optional.empty();
        }
    }
}
