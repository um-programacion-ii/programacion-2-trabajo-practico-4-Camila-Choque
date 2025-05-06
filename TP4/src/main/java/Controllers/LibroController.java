package Controllers;

import Clases.Libro;
import Servicios.LibroService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Libro obtenerPorId(@PathVariable Long id) {
        return libroService.buscarPorId(id);
    }
    @GetMapping("/{isbn}")
    public Libro obtenerPorIsbn(@PathVariable String isbn) {
        return libroService.buscarPorIsbn(isbn);
    }

    @PostMapping
    public Libro crear(@RequestBody Libro libro) {
        return libroService.guardar(libro);
    }

    @PutMapping("/{id}")
    public Libro actualizar( @RequestBody Libro libro) {
        return libroService.actualizar(libro);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
    }
}
