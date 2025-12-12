package portal_capacitaciones.portal_capacitaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionRequestDTO {
    private Long usuarioId;
    private Long cursoId;
}
