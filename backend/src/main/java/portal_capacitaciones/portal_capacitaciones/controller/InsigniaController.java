package portal_capacitaciones.portal_capacitaciones.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal_capacitaciones.portal_capacitaciones.dto.InsigniaRequestDTO;
import portal_capacitaciones.portal_capacitaciones.dto.InsigniaResponseDTO;
import portal_capacitaciones.portal_capacitaciones.model.Insignia;
import portal_capacitaciones.portal_capacitaciones.service.InsigniaService;

import java.util.List;


@Tag(
        name = "Insignias",
        description = "Gestión de insignias otorgadas a los usuarios por completar cursos"
)

@RestController
@RequestMapping("/api/v1/insignias")
public class InsigniaController {

    private final InsigniaService insigniaService;

    public InsigniaController(InsigniaService insigniaService) {
        this.insigniaService = insigniaService;
    }

    // Crear insignia
    @PostMapping
    public ResponseEntity<InsigniaResponseDTO> crearInsignia(@RequestBody InsigniaRequestDTO dto) {

        Insignia insignia = insigniaService.crearInsignia(dto);

        InsigniaResponseDTO response = new InsigniaResponseDTO(
                insignia.getId(),
                insignia.getImagen(),
                insignia.getFechaOtorgada().toString(),
                insignia.getUsuario().getId(),
                insignia.getUsuario().getNombre(),
                insignia.getCurso().getId(),
                insignia.getCurso().getNombre()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<InsigniaResponseDTO>> obtenerTodas() {

        List<InsigniaResponseDTO> lista = insigniaService.obtenerTodas().stream()
                .map(i -> new InsigniaResponseDTO(
                        i.getId(),
                        i.getImagen(),
                        i.getFechaOtorgada().toString(),
                        i.getUsuario().getId(),
                        i.getUsuario().getNombre(),
                        i.getCurso().getId(),
                        i.getCurso().getNombre()
                )).toList();

        return ResponseEntity.ok(lista);
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<InsigniaResponseDTO> obtenerPorId(@PathVariable Long id) {

        Insignia i = insigniaService.obtenerPorId(id);

        InsigniaResponseDTO response = new InsigniaResponseDTO(
                i.getId(),
                i.getImagen(),
                i.getFechaOtorgada().toString(),
                i.getUsuario().getId(),
                i.getUsuario().getNombre(),
                i.getCurso().getId(),
                i.getCurso().getNombre()
        );

        return ResponseEntity.ok(response);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        insigniaService.eliminarInsignia(id);
        return ResponseEntity.noContent().build();
    }
}
