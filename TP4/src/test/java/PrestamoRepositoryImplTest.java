import Clases.Libro;
import Clases.Prestamo;
import Repository.PrestamoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PrestamoRepositoryImplTest {
    private PrestamoRepositoryImpl prestamoRepository;
    private Prestamo prestamo;
    private Libro libro;


    @BeforeEach
    void setUp(){
        prestamoRepository = new PrestamoRepositoryImpl();
        libro = new Libro();
        libro.setId(1L);
        prestamo = new Prestamo();
        prestamo.setLibro(libro);
    }

    @Test
    void testSavePrestamoConId() {
        Prestamo guardado = prestamoRepository.save(prestamo);
        assertNotNull(guardado);
        assertEquals(prestamo.getId(), guardado.getId());
    }
    @Test
    void testSavePrestamoConIdNulo() {
        prestamo.setId(null);
        Prestamo guardado = prestamoRepository.save(prestamo);
        assertNotNull(guardado.getId());
    }
    @Test
    void testBuscarIdExistente() {
        prestamoRepository.save(prestamo);
        Optional<Prestamo> resultado = prestamoRepository.findById(prestamo.getId());
        assertTrue(resultado.isPresent());
        assertEquals(prestamo.getId(), resultado.get().getId());
    }
    @Test
    void testBuscarIdNoExistente() {
        Optional<Prestamo> resultado = prestamoRepository.findById(prestamo.getId());
        assertFalse(resultado.isPresent());
    }
    @Test
    void testBuscarIdNulo() {
        Optional<Prestamo> resultado = prestamoRepository.findById(null);
        assertFalse(resultado.isPresent());
    }
    @Test
    void testBuscarLibroExistente() {
        prestamoRepository.save(prestamo);
        Optional<Prestamo> resultado = prestamoRepository.findByLibro(libro);
        assertTrue(resultado.isPresent());
        assertEquals(libro, resultado.get().getLibro());
    }
    @Test
    void testBuscarLibroNoExistente() {
        Optional<Prestamo> resultado = prestamoRepository.findByLibro(libro);
        assertFalse(resultado.isPresent());
    }
    @Test
    void testDelete() {
        prestamoRepository.deleteById(prestamo.getId());
        Optional<Prestamo> resultado = prestamoRepository.findById(prestamo.getId());
        assertFalse(resultado.isPresent());
    }
    @Test
    void testExistsById() {
        Prestamo guardar = prestamoRepository.save(prestamo);
        assertTrue(prestamoRepository.existsById(guardar.getId()));
        prestamoRepository.deleteById(guardar.getId());
        assertFalse(prestamoRepository.existsById(guardar.getId()));

    }

}
