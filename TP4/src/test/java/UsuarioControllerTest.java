import Clases.Usuario;
import Controllers.UsuarioController;
import Servicios.UsuarioService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
    @Mock
    private UsuarioService usuarioService;
    @InjectMocks
    private UsuarioController usuarioController;
    private Usuario usuario;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        usuarioService = mock(UsuarioService.class);
        usuarioController = new UsuarioController(usuarioService);
        objectMapper = new ObjectMapper();
        // Esta configuracion se realizo con la ayuda de la IA para solucionar problemas
        mockMvc = MockMvcBuilders
                .standaloneSetup(usuarioController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
        usuario = new Usuario(1L,"camila","camila@", Usuario.EstadoUsuario.ACTIVO);

    }
    @Test
    void testObtenerPorId() throws Exception {
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        mockMvc.perform(get("/api/usuarios/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("camila"));
    }
    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        when(usuarioService.buscarPorId(10L)).thenReturn(null);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        mockMvc.perform(get("/api/usuarios/{id}", 10L))
                .andExpect(status().isNotFound());
    }
    @Test
    void testObtenerPorNombre() throws Exception {
        when(usuarioService.buscarPorNombre("camila")).thenReturn(usuario);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        mockMvc.perform(get("/api/usuarios/nombre/{nombre}","camila"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("camila"));

    }
    @Test
    void testObtenerPorNombreNoExistente() throws Exception {
        when(usuarioService.buscarPorNombre("emma")).thenReturn(null);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        mockMvc.perform(get("/api/usuarios/nombre/{nombre}", "emma"))
                .andExpect(status().isNotFound());
    }
    @Test
    void testCrearUsuario() throws Exception {
        when(usuarioService.guardar(any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuario.getId()))
                .andExpect(jsonPath("$.nombre").value(usuario.getNombre()))
                .andExpect(jsonPath("$.email").value(usuario.getEmail()))
                .andExpect(jsonPath("$.estado").value(usuario.getEstado().toString()));
    }
    @Test
    void testEliminarUsuario() throws Exception {
        mockMvc.perform(delete("/api/usuarios/{id}", 1L))
                .andExpect(status().isOk());
    }
    @Test
    void testActualizarUsuario() throws Exception {
        when(usuarioService.actualizar(any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(put("/api/usuarios/{id}", usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuario.getId()));

    }
}
