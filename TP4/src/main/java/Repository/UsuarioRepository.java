package Repository;

import Clases.*;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByNombre(String nombre);
    List<Usuario> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
