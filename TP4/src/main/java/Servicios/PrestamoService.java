package Servicios;

import Clases.*;
import java.util.List;

public interface PrestamoService {
    Prestamo guardar(Prestamo prestamo);
    Prestamo buscarPorLibro(Libro libro);
    Prestamo buscarPorId(Long id);
    List<Prestamo> obtenerTodos();
    void eliminar(Long id);
    Prestamo actualizar(Prestamo prestamo);
    boolean existe(Long id);
}
