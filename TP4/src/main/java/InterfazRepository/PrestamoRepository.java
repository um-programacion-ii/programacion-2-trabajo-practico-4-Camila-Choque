package InterfazRepository;

import Clases.Libro;
import Clases.Prestamo;
import java.util.List;
import java.util.Optional;

public interface PrestamoRepository {
    Prestamo save(Prestamo prestamo);
    Optional<Prestamo> findById(Long id);
    Optional<Prestamo> findByLibro(Libro libro);
    List<Prestamo> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
