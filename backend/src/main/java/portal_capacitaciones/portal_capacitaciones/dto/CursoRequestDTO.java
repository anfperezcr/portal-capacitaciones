package portal_capacitaciones.portal_capacitaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import portal_capacitaciones.portal_capacitaciones.model.ModuloCurso;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoRequestDTO {

    private String nombre;
    private String descripcion;
    private ModuloCurso modulo;
    private boolean activo;
}
