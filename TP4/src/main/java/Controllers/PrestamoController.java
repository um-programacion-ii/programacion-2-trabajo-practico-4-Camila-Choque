package Controllers;

import Clases.Libro;
import Clases.Prestamo;
import Servicios.LibroService;
import Servicios.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;
    private final LibroService libroService;

    public PrestamoController(PrestamoService prestamoService, LibroService libroService) {
        this.prestamoService = prestamoService;
        this.libroService = libroService;
    }

    @GetMapping
    public List<Prestamo> obtenerTodos() {
        return prestamoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable Long id) {
        Prestamo prestamo = prestamoService.buscarPorId(id);
        if (prestamo != null) {
            return ResponseEntity.ok(prestamo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/libro/{id}")
    public ResponseEntity<Prestamo> obtenerPorLibro(@PathVariable Long id) {
        Libro libro = libroService.buscarPorId(id);
        if (libro != null) {
            Prestamo prestamo = prestamoService.buscarPorLibro(libro);
            return ResponseEntity.ok(prestamo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Prestamo crear(@RequestBody Prestamo prestamo) {
        return prestamoService.guardar(prestamo);
    }

    @PutMapping("/{prestamo}")
    public Prestamo actualizar( @RequestBody Prestamo prestamo) {
        return prestamoService.actualizar(prestamo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        prestamoService.eliminar(id);
    }
}
