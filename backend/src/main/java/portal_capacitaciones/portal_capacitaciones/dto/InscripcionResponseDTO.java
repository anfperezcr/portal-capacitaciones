package portal_capacitaciones.portal_capacitaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionResponseDTO {
    private Long id;
    private String fechaInscripcion;
    private Long usuarioId;
    private String nombreUsuario;
    private Long cursoId;
    private String nombreCurso;
}
