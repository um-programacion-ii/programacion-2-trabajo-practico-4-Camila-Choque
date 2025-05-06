package Servicios;

import Clases.*;
import java.util.List;

public interface UsuarioService {
    Usuario guardar(Usuario usuario);
    Usuario buscarPorId(Long id);
    Usuario buscarPorNombre(String nombre);
    List<Usuario> obtenerTodos();
    void eliminar(Long id);
    Usuario actualizar(Usuario Usuario);
    boolean existe(Long id);
}
