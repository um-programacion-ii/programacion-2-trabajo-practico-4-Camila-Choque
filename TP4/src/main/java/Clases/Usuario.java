package Clases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private EstadoUsuario estado;

    public enum EstadoUsuario {
        ACTIVO,
        NO_ACTIVO
    }
}
