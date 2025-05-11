import Clases.Libro;
import Repository.LibroRepository;
import Repository.LibroRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LibroRepositoryImplTest {
    private LibroRepository libroRepository;
    private Libro libro;
    private Libro libro2;


    @BeforeEach
    void setUp() {
        libroRepository = new LibroRepositoryImpl();
        libro = new Libro();
        libro.setId(libro.getId());
        libro.setIsbn("11111");
        libro.setTitulo(libro.getTitulo());
        libro2 = new Libro(1L,"1234","harry potter","Pepe", Libro.EstadoLibro.DISPONIBLE);
    }

    @Test
    void testSaveLibroConId() {
        Libro guardado = libroRepository.save(libro);
        assertNotNull(guardado);
        assertEquals(libro.getId(), guardado.getId());
        assertEquals(libro.getTitulo(), guardado.getTitulo());
    }
    @Test
    void testSaveLibroConIdNulo() {
        libro.setId(null);
        Libro guardado = libroRepository.save(libro);
        assertNotNull(guardado.getId());
    }

    @Test
    void testBuscarIdExistente() {
        libroRepository.save(libro);
        Optional<Libro> resultado = libroRepository.findById(libro.getId());
        assertTrue(resultado.isPresent());
        assertEquals(libro.getId(), resultado.get().getId());
        assertEquals(libro.getTitulo(), resultado.get().getTitulo());
    }
    @Test
    void testBuscarIdNoExistente() {
        Optional<Libro> resultado = libroRepository.findById(999L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void testBuscarIdNulo() {
        Optional<Libro> resultado = libroRepository.findById(null);
        assertFalse(resultado.isPresent());
    }
    @Test
    void testBuscarIsbnExistente() {
        libroRepository.save(libro);
        Optional<Libro> resultado = libroRepository.findByIsbn(libro.getIsbn());
        assertTrue(resultado.isPresent());
        assertEquals(libro.getIsbn(), resultado.get().getIsbn());
    }
    @Test
    void testBuscarIsbnNoExistente() {
        Optional<Libro> resultado = libroRepository.findByIsbn(libro.getIsbn());
        assertFalse(resultado.isPresent());
    }
    @Test
    void testDelete() {
        libroRepository.deleteById(libro.getId());
        Optional<Libro> resultado = libroRepository.findById(libro.getId());
        assertFalse(resultado.isPresent());
    }
    @Test
    void testExistsById() {
        Libro guardar = libroRepository.save(libro2);
        assertTrue(libroRepository.existsById(guardar.getId()));
        libroRepository.deleteById(guardar.getId());
        assertFalse(libroRepository.existsById(guardar.getId()));

    }

}
