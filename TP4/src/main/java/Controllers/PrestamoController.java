package Controllers;

import Clases.Libro;
import Clases.Prestamo;
import Servicios.PrestamoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }
    @GetMapping
    public List<Prestamo> obtenerTodos() {
        return prestamoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Prestamo obtenerPorId(@PathVariable Long id) {
        return prestamoService.buscarPorId(id);
    }
    @GetMapping("/{Libro}")
    public Prestamo obtenerPorLibro(@PathVariable Libro libro) {
        return prestamoService.buscarPorLibro(libro);
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
