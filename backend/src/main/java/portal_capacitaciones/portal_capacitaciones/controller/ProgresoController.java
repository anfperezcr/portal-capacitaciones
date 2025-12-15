package portal_capacitaciones.portal_capacitaciones.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal_capacitaciones.portal_capacitaciones.dto.ProgresoRequestDTO;
import portal_capacitaciones.portal_capacitaciones.dto.ProgresoResponseDTO;
import portal_capacitaciones.portal_capacitaciones.model.Progreso;
import portal_capacitaciones.portal_capacitaciones.service.ProgresoService;

import java.util.List;


@Tag(
        name = "Progresos",
        description = "Seguimiento del progreso de los usuarios en los cursos"
)

@RestController
@RequestMapping("/api/v1/progresos")
public class ProgresoController {

    private final ProgresoService progresoService;

    public ProgresoController(ProgresoService progresoService) {
        this.progresoService = progresoService;
    }

    // Crear
    @PostMapping
    public ResponseEntity<ProgresoResponseDTO> crear(@RequestBody ProgresoRequestDTO dto) {

        Progreso progreso = progresoService.crearProgreso(dto);

        ProgresoResponseDTO response = new ProgresoResponseDTO(
                progreso.getId(),
                progreso.getEstado().toString(),
                progreso.getFechaActualizacion().toString(),
                progreso.getUsuario().getId(),
                progreso.getUsuario().getNombre(),
                progreso.getCurso().getId(),
                progreso.getCurso().getNombre()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<ProgresoResponseDTO>> obtenerTodos() {

        List<ProgresoResponseDTO> lista = progresoService.obtenerTodos().stream()
                .map(p -> new ProgresoResponseDTO(
                        p.getId(),
                        p.getEstado().toString(),
                        p.getFechaActualizacion().toString(),
                        p.getUsuario().getId(),
                        p.getUsuario().getNombre(),
                        p.getCurso().getId(),
                        p.getCurso().getNombre()
                )).toList();

        return ResponseEntity.ok(lista);
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProgresoResponseDTO> obtenerPorId(@PathVariable Long id) {

        Progreso p = progresoService.obtenerPorId(id);

        ProgresoResponseDTO response = new ProgresoResponseDTO(
                p.getId(),
                p.getEstado().toString(),
                p.getFechaActualizacion().toString(),
                p.getUsuario().getId(),
                p.getUsuario().getNombre(),
                p.getCurso().getId(),
                p.getCurso().getNombre()
        );

        return ResponseEntity.ok(response);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<ProgresoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody ProgresoRequestDTO dto) {

        Progreso progreso = progresoService.actualizarProgreso(id, dto);

        ProgresoResponseDTO response = new ProgresoResponseDTO(
                progreso.getId(),
                progreso.getEstado().toString(),
                progreso.getFechaActualizacion().toString(),
                progreso.getUsuario().getId(),
                progreso.getUsuario().getNombre(),
                progreso.getCurso().getId(),
                progreso.getCurso().getNombre()
        );

        return ResponseEntity.ok(response);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        progresoService.eliminarProgreso(id);
        return ResponseEntity.noContent().build();
    }
}
