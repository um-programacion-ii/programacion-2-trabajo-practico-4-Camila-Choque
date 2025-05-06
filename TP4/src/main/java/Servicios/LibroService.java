package Servicios;

import Clases.Libro;
import java.util.List;

public interface LibroService {
        Libro guardar(Libro libro);
        Libro buscarPorIsbn(String isbn);
        Libro buscarPorId(Long id);
        List<Libro> obtenerTodos();
        void eliminar(Long id);
        Libro actualizar(Libro libro);
        boolean existe(Long id);
}
