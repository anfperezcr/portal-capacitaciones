package portal_capacitaciones.portal_capacitaciones.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal_capacitaciones.portal_capacitaciones.dto.*;
import portal_capacitaciones.portal_capacitaciones.model.Inscripcion;
import portal_capacitaciones.portal_capacitaciones.service.InscripcionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    // Crear inscripción
    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> crear(@RequestBody InscripcionRequestDTO dto) {
        Inscripcion ins = inscripcionService.crearInscripcion(dto);
        InscripcionResponseDTO res = mapToDto(ins);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // Obtener todas
    @GetMapping
    public ResponseEntity<List<InscripcionResponseDTO>> obtenerTodas() {
        List<InscripcionResponseDTO> lista = inscripcionService.obtenerTodas()
                .stream().map(this::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // Obtener por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InscripcionResponseDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<InscripcionResponseDTO> lista = inscripcionService.obtenerPorUsuario(usuarioId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // Obtener por curso
    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<InscripcionResponseDTO>> obtenerPorCurso(@PathVariable Long cursoId) {
        List<InscripcionResponseDTO> lista = inscripcionService.obtenerPorCurso(cursoId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponseDTO> obtenerPorId(@PathVariable Long id) {
        Inscripcion ins = inscripcionService.obtenerPorId(id);
        return ResponseEntity.ok(mapToDto(ins));
    }

    // Eliminar (desinscribir)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inscripcionService.eliminarInscripcion(id);
        return ResponseEntity.noContent().build();
    }

    // --- Mapper privado ---
    private InscripcionResponseDTO mapToDto(Inscripcion ins) {
        return new InscripcionResponseDTO(
                ins.getId(),
                ins.getFechaInscripcion().toString(),
                ins.getUsuario().getId(),
                ins.getUsuario().getNombre(),
                ins.getCurso().getId(),
                ins.getCurso().getNombre()
        );
    }
}
