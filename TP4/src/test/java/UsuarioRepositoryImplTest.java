
import Clases.Usuario;
import Repository.UsuarioRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioRepositoryImplTest {
    private UsuarioRepositoryImpl usuarioRepository;
    private Usuario usuario;

    @BeforeEach
    void setUp(){
        usuarioRepository = new UsuarioRepositoryImpl();
        usuario = new Usuario();
        usuario.setNombre("camila");
    }

    @Test
    void testSaveUsuarioConId() {
        Usuario guardado = usuarioRepository.save(usuario);
        assertNotNull(guardado);
        assertEquals(usuario.getId(), guardado.getId());
    }
    @Test
    void testSaveUsuarioConIdNulo() {
        usuario.setId(null);
        Usuario guardado = usuarioRepository.save(usuario);
        assertNotNull(guardado.getId());
    }
    @Test
    void testBuscarIdExistente() {
        usuarioRepository.save(usuario);
        Optional<Usuario> resultado = usuarioRepository.findById(usuario.getId());
        assertTrue(resultado.isPresent());
        assertEquals(usuario.getId(), resultado.get().getId());
    }
    @Test
    void testBuscarIdNoExistente() {
        Optional<Usuario> resultado = usuarioRepository.findById(usuario.getId());
        assertFalse(resultado.isPresent());
    }
    @Test
    void testBuscarIdNulo() {
        Optional<Usuario> resultado = usuarioRepository.findById(null);
        assertFalse(resultado.isPresent());
    }
    @Test
    void testBuscarNombreExistente() {
        usuarioRepository.save(usuario);
        Optional<Usuario> resultado = usuarioRepository.findByNombre(usuario.getNombre());
        assertTrue(resultado.isPresent());
        assertEquals(usuario.getNombre(), resultado.get().getNombre());
    }
    @Test
    void testBuscarNombreNoExistente() {
        Optional<Usuario> resultado = usuarioRepository.findByNombre(usuario.getNombre());
        assertFalse(resultado.isPresent());
    }
    @Test
    void testDelete() {
        usuarioRepository.deleteById(usuario.getId());
        Optional<Usuario> resultado = usuarioRepository.findById(usuario.getId());
        assertFalse(resultado.isPresent());
    }
    @Test
    void testExistsById() {
        Usuario guardar = usuarioRepository.save(usuario);
        assertTrue(usuarioRepository.existsById(guardar.getId()));
        usuarioRepository.deleteById(guardar.getId());
        assertFalse(usuarioRepository.existsById(guardar.getId()));

    }
}
