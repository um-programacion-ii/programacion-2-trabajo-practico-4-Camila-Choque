package Servicios;

import Clases.*;
import Repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {
    private final LibroRepository libroRepository;


    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public Libro buscarPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn)
                .orElse(null);
    }

    @Override
    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }


    @Override
    public void eliminar(Long id) {
        libroRepository.deleteById(id);
    }

    @Override
    public Libro actualizar( Libro libro) {
        return libroRepository.save(libro);
    }
    @Override
    public boolean existe(Long id) {
        return libroRepository.existsById(id);
    }
}
