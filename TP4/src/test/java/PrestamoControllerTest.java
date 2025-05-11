
import Clases.Libro;
import Clases.Prestamo;
import Clases.Usuario;
import Controllers.PrestamoController;
import Servicios.LibroService;
import Servicios.PrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PrestamoControllerTest {
    @Mock
    private PrestamoService prestamoService;
    @InjectMocks
    private PrestamoController  prestamoController;
    @Mock
    private LibroService libroService;
    @Autowired
    private MockMvc mockMvc;
    private Prestamo prestamo;
    private Libro libro;
    private Usuario usuario;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper(); // Segurido por la IA
        objectMapper.registerModule(new JavaTimeModule()); // Segurido por la IA
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();
        libro = new Libro(1L, "111", "Harry Potter", "J.K. Rowling", Libro.EstadoLibro.DISPONIBLE);
        usuario = new Usuario();
        prestamo = new Prestamo(1L,libro,usuario, LocalDate.now(), LocalDate.now().plusDays(14));


    }
    @Test
    void testObtenerPorId() throws Exception {
        when(prestamoService.buscarPorId(1L)).thenReturn(prestamo);
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();
        mockMvc.perform(get("/api/prestamos/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        when(prestamoService.buscarPorId(100L)).thenReturn(null);
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();
        mockMvc.perform(get("/api/prestamos/{id}", 100L))
                .andExpect(status().isNotFound());
    }
    @Test
    void testObtenerPorLibro() throws Exception {
        when(libroService.buscarPorId(1L)).thenReturn(libro);
        when(prestamoService.buscarPorLibro(libro)).thenReturn(prestamo);
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();
        mockMvc.perform(get("/api/prestamos/libro/{libro}", libro.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libro.id").value(libro.getId()));

    }
    @Test
    void testObtenerPorLibroNoExistente() throws Exception {
        when(libroService.buscarPorId(102L)).thenReturn(null);
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();
        mockMvc.perform(get("/api/prestamos/libro/{libro}", 102L))
                .andExpect(status().isNotFound());
    }
    @Test
    void testCrearPrestamo() throws Exception {
        when(prestamoService.guardar(any(Prestamo.class))).thenReturn(prestamo);
        mockMvc.perform(post("/api/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prestamo.getId()));

    }
    @Test
    void testActualizarPrestamo() throws Exception {
        when(prestamoService.actualizar(any(Prestamo.class))).thenReturn(prestamo);
        mockMvc.perform(put("/api/prestamos/{prestamo}", prestamo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prestamo.getId()))
                .andExpect(jsonPath("$.libro.id").value(prestamo.getLibro().getId()))
                .andExpect(jsonPath("$.usuario.id").value(prestamo.getUsuario().getId()));
    }
    @Test
    void testEliminarPrestamo() throws Exception {
        mockMvc.perform(delete("/api/prestamos/{id}", 1L))
                .andExpect(status().isOk());
    }
}
