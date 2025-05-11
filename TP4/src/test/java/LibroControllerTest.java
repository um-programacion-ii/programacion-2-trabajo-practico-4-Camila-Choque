import Clases.Libro;
import Controllers.LibroController;
import Servicios.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LibroControllerTest {

    @Mock
    private LibroService libroService;
    @InjectMocks
    private LibroController libroController;
    @Autowired
    private MockMvc mockMvc;
    private  Libro libro;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {

        libroService = mock(LibroService.class);
        libroController = new LibroController(libroService);
        objectMapper = new ObjectMapper();

        // Esta configuracion se realizo con la ayuda de la IA para solucionar problemas
        mockMvc = MockMvcBuilders
                .standaloneSetup(libroController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
        libro = new Libro(1L,"111","Harry Potter","pepe", Libro.EstadoLibro.DISPONIBLE);

    }

    @Test
    void testObtenerPorId() throws Exception {
        when(libroService.buscarPorId(1L)).thenReturn(libro);
        mockMvc = MockMvcBuilders.standaloneSetup(libroController).build();
        mockMvc.perform(get("/api/libros/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Harry Potter"));
    }
    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        when(libroService.buscarPorId(999L)).thenReturn(null);
        mockMvc = MockMvcBuilders.standaloneSetup(libroController).build();
        mockMvc.perform(get("/api/libros/{id}", 999L))
                .andExpect(status().isNotFound());
    }
    @Test
    void testObtenerPorIsbn() throws Exception {
        when(libroService.buscarPorIsbn("111")).thenReturn(libro);
        mockMvc = MockMvcBuilders.standaloneSetup(libroController).build();
        mockMvc.perform(get("/api/libros/isbn/{isbn}","111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("111"));

    }
    @Test
    void testObtenerPorIsbnNoExistente() throws Exception {
        when(libroService.buscarPorIsbn("999")).thenReturn(null);
        mockMvc = MockMvcBuilders.standaloneSetup(libroController).build();
        mockMvc.perform(get("/api/libros/isbn/{isbn}", "999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearLibro() throws Exception {
        when(libroService.guardar(any(Libro.class))).thenReturn(libro);
        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(libro.getId()))
                .andExpect(jsonPath("$.isbn").value(libro.getIsbn()))
                .andExpect(jsonPath("$.titulo").value(libro.getTitulo()))
                .andExpect(jsonPath("$.autor").value(libro.getAutor()));
    }
    @Test
    void testEliminarLibro() throws Exception {
        mockMvc.perform(delete("/api/libros/{id}", 1L))
                .andExpect(status().isOk());
    }
    @Test
    void testActualizarLibro() throws Exception {
        when(libroService.actualizar(any(Libro.class))).thenReturn(libro);
        mockMvc.perform(put("/api/libros/{id}", libro.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(libro.getId()));

    }
}
