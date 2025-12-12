package portal_capacitaciones.portal_capacitaciones.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portal_capacitaciones.portal_capacitaciones.dto.CursoRequestDTO;
import portal_capacitaciones.portal_capacitaciones.exceptions.DuplicateResourceException;
import portal_capacitaciones.portal_capacitaciones.exceptions.ResourceNotFoundException;
import portal_capacitaciones.portal_capacitaciones.model.Curso;
import portal_capacitaciones.portal_capacitaciones.repository.CursoRepository;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    // Crear curso
    @Transactional
    public Curso crearCurso(CursoRequestDTO dto) {

        if (cursoRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("Ya existe un curso con este nombre.");
        }

        Curso curso = new Curso();
        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setModulo(dto.getModulo());
        curso.setActivo(dto.isActivo());

        return cursoRepository.save(curso);
    }

    // Obtener todos
    @Transactional(readOnly = true)
    public List<Curso> obtenerTodos() {
        return cursoRepository.findAll();
    }

    // Obtener por ID
    @Transactional(readOnly = true)
    public Curso obtenerPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
    }

    // Actualizar
    @Transactional
    public Curso actualizarCurso(Long id, CursoRequestDTO dto) {

        Curso curso = obtenerPorId(id);

        // Validar si el nombre es cambiado y ya existe
        if (!curso.getNombre().equals(dto.getNombre()) &&
                cursoRepository.existsByNombre(dto.getNombre())) {
            throw new DuplicateResourceException("El nombre del curso ya está en uso.");
        }

        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setModulo(dto.getModulo());
        curso.setActivo(dto.isActivo());

        return cursoRepository.save(curso);
    }

    // Eliminar curso
    @Transactional
    public void eliminarCurso(Long id) {
        Curso curso = obtenerPorId(id);
        cursoRepository.delete(curso);
    }
}
