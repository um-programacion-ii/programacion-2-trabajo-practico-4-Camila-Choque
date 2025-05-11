import Clases.Usuario;
import Repository.UsuarioRepository;
import Servicios.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    private Usuario usuario;

    @BeforeEach
    void setUp (){
        usuario = new Usuario();

    }
    @Test
    void testGuardarUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario resultado = usuarioService.guardar(usuario);
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getNombre(), resultado.getNombre());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        assertEquals(usuario.getEstado(), resultado.getEstado());
    }
    @Test
    void testBuscarPorNombreExistente() {
        String nombreExistente = "camila";
        when(usuarioRepository.findByNombre(nombreExistente)).thenReturn(Optional.of(usuario));
        Usuario resultado = usuarioService.buscarPorNombre(nombreExistente);
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getNombre(), resultado.getNombre());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        assertEquals(usuario.getEstado(), resultado.getEstado());
    }
    @Test
    void testBuscarPorNombreNoExistente() {
        when(usuarioRepository.findByNombre(usuario.getNombre())).thenReturn(Optional.empty());
        Usuario resultado = usuarioService.buscarPorNombre(usuario.getNombre());
        assertNull(resultado);

    }
    @Test
    void testBuscarPorIdExistente() {
        Long IdExistente = 1L;
        when(usuarioRepository.findById(IdExistente)).thenReturn(Optional.of(usuario));
        Usuario resultado = usuarioService.buscarPorId(IdExistente);
        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getNombre(), resultado.getNombre());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        assertEquals(usuario.getEstado(), resultado.getEstado());
    }
    @Test
    void testBuscarPorIdNoExistente() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.empty());
        Usuario resultado = usuarioService.buscarPorId(usuario.getId());
        assertNull(resultado);

    }
    @Test
    void testEliminar() {
        Long id = 1L;
        usuarioService.eliminar(id);
        verify(usuarioRepository).deleteById(id);
    }
    @Test
    void testExisteUsuarioExistente() {
        Long idExistente = 1L;
        when(usuarioRepository.existsById(idExistente)).thenReturn(true);
        boolean resultado = usuarioService.existe(idExistente);
        assertTrue(resultado);
    }
    @Test
    void testExisteUsuarioNoExistente() {
        Long idNoExistente = 2L;
        when(usuarioRepository.existsById(idNoExistente)).thenReturn(false);
        boolean resultado = usuarioService.existe(idNoExistente);
        assertFalse(resultado);
    }
}
