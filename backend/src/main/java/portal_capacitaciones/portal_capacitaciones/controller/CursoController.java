package portal_capacitaciones.portal_capacitaciones.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal_capacitaciones.portal_capacitaciones.dto.CursoRequestDTO;
import portal_capacitaciones.portal_capacitaciones.dto.CursoResponseDTO;
import portal_capacitaciones.portal_capacitaciones.model.Curso;
import portal_capacitaciones.portal_capacitaciones.service.CursoService;

import java.util.List;


@Tag(
        name = "Cursos",
        description = "Administración de cursos de capacitación"
)

@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // Crear curso
    @PostMapping
    public ResponseEntity<CursoResponseDTO> crearCurso(@RequestBody CursoRequestDTO dto) {

        Curso curso = cursoService.crearCurso(dto);

        CursoResponseDTO respuesta = new CursoResponseDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getModulo().toString(),
                curso.isActivo()
        );

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> obtenerTodos() {

        List<CursoResponseDTO> lista = cursoService.obtenerTodos().stream()
                .map(curso -> new CursoResponseDTO(
                        curso.getId(),
                        curso.getNombre(),
                        curso.getDescripcion(),
                        curso.getModulo().toString(),
                        curso.isActivo()
                )).toList();

        return ResponseEntity.ok(lista);
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> obtenerCurso(@PathVariable Long id) {

        Curso curso = cursoService.obtenerPorId(id);

        CursoResponseDTO respuesta = new CursoResponseDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getModulo().toString(),
                curso.isActivo()
        );

        return ResponseEntity.ok(respuesta);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> actualizarCurso(
            @PathVariable Long id,
            @RequestBody CursoRequestDTO dto) {

        Curso curso = cursoService.actualizarCurso(id, dto);

        CursoResponseDTO respuesta = new CursoResponseDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getModulo().toString(),
                curso.isActivo()
        );

        return ResponseEntity.ok(respuesta);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
