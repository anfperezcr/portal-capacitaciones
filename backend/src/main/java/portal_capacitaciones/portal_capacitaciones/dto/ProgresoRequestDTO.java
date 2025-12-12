package portal_capacitaciones.portal_capacitaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import portal_capacitaciones.portal_capacitaciones.model.EstadoCurso;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgresoRequestDTO {

    private EstadoCurso estado;   // INICIADO / COMPLETADO
    private Long usuarioId;
    private Long cursoId;
}
