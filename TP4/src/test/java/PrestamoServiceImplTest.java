import Clases.Libro;
import Clases.Prestamo;
import Clases.Usuario;
import Repository.PrestamoRepository;
import Servicios.PrestamoServiceImpl;
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
public class PrestamoServiceImplTest {
    @Mock
    private PrestamoRepository prestamoRepository;
    @InjectMocks
    private PrestamoServiceImpl prestamoService;
    private Prestamo prestamo;
    private Libro libro;


    @BeforeEach
    void setUp() {
        libro = new Libro(1L, "123-456-789", "Test Book", "Test Author", Libro.EstadoLibro.DISPONIBLE);
        prestamo = new Prestamo();
        prestamo.setUsuario(new Usuario(1L, "TUusario1","usuario@gmail", Usuario.EstadoUsuario.ACTIVO));
    }

    @Test
    void testGuardarPrestamo() {
        when(prestamoRepository.save(prestamo)).thenReturn(prestamo);
        Prestamo resultado = prestamoService.guardar(prestamo);
        assertNotNull(resultado);
        assertEquals(prestamo.getId(), resultado.getId());
        assertEquals(prestamo.getLibro(), resultado.getLibro());
        assertEquals(prestamo.getUsuario(), resultado.getUsuario());
    }
    @Test
    void testBuscarPorLibroExistente() {
        when(prestamoRepository.findByLibro(libro)).thenReturn(Optional.of(prestamo));
        Prestamo resultado = prestamoService.buscarPorLibro(libro);
        assertNotNull(resultado);
        assertEquals(prestamo.getId(), resultado.getId());
        assertEquals(prestamo.getLibro(), resultado.getLibro());
        assertEquals(prestamo.getUsuario(), resultado.getUsuario());
    }

    @Test
    void testBuscarPorLibroNoExistente() {
        when(prestamoRepository.findByLibro(libro)).thenReturn(Optional.empty());
        Prestamo resultado = prestamoService.buscarPorLibro(libro);
        assertNull(resultado);
    }

    @Test
    void testBuscarPorIdExistente() {
        when(prestamoRepository.findById(prestamo.getId())).thenReturn(Optional.of(prestamo));
        Prestamo resultado = prestamoService.buscarPorId(prestamo.getId());
        assertNotNull(resultado);
        assertEquals(prestamo.getId(), resultado.getId());
        assertEquals(prestamo.getLibro(), resultado.getLibro());
        assertEquals(prestamo.getUsuario(), resultado.getUsuario());
    }
    @Test
    void testBuscarPorIdNoExistente() {
        when(prestamoRepository.findById(prestamo.getId())).thenReturn(Optional.empty());
        Prestamo resultado = prestamoService.buscarPorId(prestamo.getId());
        assertNull(resultado);
    }

    @Test
    void testEliminar() {
        Long id = 1L;
        prestamoService.eliminar(id);
        verify(prestamoRepository).deleteById(id);
    }
    @Test
    void testExistePrestamoExistente() {
        Long idExistente = 1L;
        when(prestamoRepository.existsById(idExistente)).thenReturn(true);
        boolean resultado = prestamoService.existe(idExistente);
        assertTrue(resultado);
    }
    @Test
    void testExistePrestamoNoExistente() {
        Long idNoExistente = 2L;
        when(prestamoRepository.existsById(idNoExistente)).thenReturn(false);
        boolean resultado = prestamoService.existe(idNoExistente);
        assertFalse(resultado);
    }

}
