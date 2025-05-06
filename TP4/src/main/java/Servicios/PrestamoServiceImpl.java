package Servicios;

import Clases.Libro;
import Clases.Prestamo;
import Repository.PrestamoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {
    private PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public Prestamo guardar(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo buscarPorLibro(Libro libro) {
        return prestamoRepository.findByLibro(libro)
                .orElse(null);
    }

    @Override
    public Prestamo buscarPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElse(null);
    }
    @Override
    public List<Prestamo> obtenerTodos() {
        return prestamoRepository.findAll();
    }


    @Override
    public void eliminar(Long id) {
        prestamoRepository.deleteById(id);
    }

    @Override
    public Prestamo actualizar( Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public boolean existe(Long id) {
        return prestamoRepository.existsById(id);
    }
}
