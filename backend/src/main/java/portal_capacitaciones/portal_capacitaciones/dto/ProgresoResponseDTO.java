package portal_capacitaciones.portal_capacitaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgresoResponseDTO {

    private Long id;
    private String estado;
    private String fechaActualizacion;

    private Long usuarioId;
    private String nombreUsuario;

    private Long cursoId;
    private String nombreCurso;
}
