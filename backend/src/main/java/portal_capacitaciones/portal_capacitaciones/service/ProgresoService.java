package portal_capacitaciones.portal_capacitaciones.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portal_capacitaciones.portal_capacitaciones.dto.InsigniaRequestDTO;
import portal_capacitaciones.portal_capacitaciones.dto.ProgresoRequestDTO;
import portal_capacitaciones.portal_capacitaciones.exceptions.DuplicateResourceException;
import portal_capacitaciones.portal_capacitaciones.exceptions.ResourceNotFoundException;
import portal_capacitaciones.portal_capacitaciones.model.*;
import portal_capacitaciones.portal_capacitaciones.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgresoService {

    private final ProgresoRepository progresoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final InsigniaService insigniaService;

    public ProgresoService(
            ProgresoRepository progresoRepository,
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository, InsigniaService insigniaService
    ) {
        this.progresoRepository = progresoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;

        this.insigniaService = insigniaService;
    }

    // Crear progreso
    @Transactional
    public Progreso crearProgreso(ProgresoRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        // Evitar duplicación
        if (progresoRepository.existsByUsuarioIdAndCursoId(dto.getUsuarioId(), dto.getCursoId())) {
            throw new DuplicateResourceException("Este usuario ya tiene un progreso registrado para este curso.");
        }

        Progreso progreso = new Progreso();
        progreso.setEstado(dto.getEstado());
        progreso.setFechaActualizacion(LocalDateTime.now());
        progreso.setUsuario(usuario);
        progreso.setCurso(curso);

        return progresoRepository.save(progreso);
    }

    // Obtener todos
    @Transactional(readOnly = true)
    public List<Progreso> obtenerTodos() {
        return progresoRepository.findAll();
    }

    // Obtener por ID
    @Transactional(readOnly = true)
    public Progreso obtenerPorId(Long id) {
        return progresoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Progreso no encontrado con ID: " + id));
    }

    // Actualizar progreso
    @Transactional
    public Progreso actualizarProgreso(Long id, ProgresoRequestDTO dto) {

        Progreso progreso = obtenerPorId(id);

        // 1️⃣ Actualizar estado
        progreso.setEstado(dto.getEstado());
        progreso.setFechaActualizacion(LocalDateTime.now());

        Progreso progresoActualizado = progresoRepository.save(progreso);

        // 2️⃣ Si el curso fue COMPLETADO → otorgar insignia
        if (dto.getEstado() == EstadoCurso.COMPLETADO) {

            InsigniaRequestDTO insigniaDTO = new InsigniaRequestDTO();
            insigniaDTO.setUsuarioId(progreso.getUsuario().getId());
            insigniaDTO.setCursoId(progreso.getCurso().getId());

            // imagen simple por ahora
            insigniaDTO.setImagen("insignia-curso.png");

            try {
                insigniaService.crearInsignia(insigniaDTO);
            } catch (DuplicateResourceException e) {
                // si ya existe la insignia, no hacemos nada
            }
        }

        return progresoActualizado;
    }


    // Eliminar progreso
    @Transactional
    public void eliminarProgreso(Long id) {
        Progreso progreso = obtenerPorId(id);
        progresoRepository.delete(progreso);
    }
}
