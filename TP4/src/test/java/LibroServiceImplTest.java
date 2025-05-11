import Clases.Libro;
import Repository.LibroRepository;
import Servicios.LibroServiceImpl;
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
public class LibroServiceImplTest {
    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    @Test
    void buscarPorIsbnExiste() {
        String isbn = "123-456-789";
        Libro libroEsperado = new Libro(1L, isbn, "Test Book", "Test Author", Libro.EstadoLibro.DISPONIBLE);
        when(libroRepository.findByIsbn(isbn)).thenReturn(Optional.of(libroEsperado));
        Libro resultado = libroService.buscarPorIsbn(isbn);
        assertNotNull(resultado);
        assertEquals(isbn, resultado.getIsbn());
        verify(libroRepository).findByIsbn(isbn);
    }

    @Test
    void buscarPorIsbnNoExistente() {
        String isbn = "67890";
        when(libroRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        Libro resultado = libroService.buscarPorIsbn(isbn);
        assertNull(resultado);
    }

    @Test
    void guardarLibro() {
        Libro libro = new Libro();
        libro.setTitulo("El Principito");
        when(libroRepository.save(libro)).thenReturn(libro);
        Libro resultado = libroService.guardar(libro);
        assertNotNull(resultado);
        assertEquals("El Principito", resultado.getTitulo());
    }
    @Test
    void buscarPorIdExiste() {
        Long id = 1L;
        Libro libroEsperado = new Libro(id, "123-456-789", "Test Book", "Test Author", Libro.EstadoLibro.DISPONIBLE);
        when(libroRepository.findById(id)).thenReturn(Optional.of(libroEsperado));
        Libro resultado = libroService.buscarPorId(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(libroRepository).findById(id);
    }
    @Test
    void buscarPorIdNoExistente() {
        Long id = 2L;
        when(libroRepository.findById(id)).thenReturn(Optional.empty());
        Libro resultado = libroService.buscarPorId(id);
        assertNull(resultado);
    }
    @Test
    void testEliminar() {
        Long id = 1L;
        libroService.eliminar(id);
        verify(libroRepository).deleteById(id);
    }
    @Test
    void testExisteLibroExistente() {
        Long idExistente = 1L;
        when(libroRepository.existsById(idExistente)).thenReturn(true);
        boolean resultado = libroService.existe(idExistente);
        assertTrue(resultado);
    }

    @Test
    void testExisteLibroNoExistente() {
        Long idNoExistente = 2L;
        when(libroRepository.existsById(idNoExistente)).thenReturn(false);
        boolean resultado = libroService.existe(idNoExistente);
        assertFalse(resultado);
    }
}

