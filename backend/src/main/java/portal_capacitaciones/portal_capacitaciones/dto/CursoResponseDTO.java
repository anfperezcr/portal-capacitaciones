package portal_capacitaciones.portal_capacitaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String modulo;
    private boolean activo;
}
