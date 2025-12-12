package portal_capacitaciones.portal_capacitaciones.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portal_capacitaciones.portal_capacitaciones.dto.InscripcionRequestDTO;
import portal_capacitaciones.portal_capacitaciones.exceptions.DuplicateResourceException;
import portal_capacitaciones.portal_capacitaciones.exceptions.ResourceNotFoundException;
import portal_capacitaciones.portal_capacitaciones.model.*;
import portal_capacitaciones.portal_capacitaciones.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final ProgresoRepository progresoRepository;

    public InscripcionService(
            InscripcionRepository inscripcionRepository,
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository, ProgresoRepository progresoRepository
    ) {
        this.inscripcionRepository = inscripcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.progresoRepository = progresoRepository;
    }

    @Transactional
    public Inscripcion crearInscripcion(InscripcionRequestDTO dto) {
        Long usuarioId = dto.getUsuarioId();
        Long cursoId = dto.getCursoId();

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + cursoId));

        // Verifica duplicado de inscripcion
        if (inscripcionRepository.existsByUsuarioIdAndCursoId(usuarioId, cursoId)) {
            throw new DuplicateResourceException("Usuario ya inscrito en el curso.");
        }

        // Crear la inscripcion
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(LocalDateTime.now());

        Inscripcion insGuardada = inscripcionRepository.save(inscripcion);

        // Crear progreso si no existe (evita duplicados)
        boolean progresoExiste = progresoRepository.existsByUsuarioIdAndCursoId(usuarioId, cursoId);
        if (!progresoExiste) {
            Progreso progreso = new Progreso();
            progreso.setUsuario(usuario);
            progreso.setCurso(curso);
            progreso.setEstado(EstadoCurso.INICIADO);
            progreso.setFechaActualizacion(LocalDateTime.now());
            progresoRepository.save(progreso);
            // No es necesario guardar referencia en Inscripcion; Progreso es independiente
        }

        return insGuardada;

        //return inscripcionRepository.save(inscripcion);
    }

    @Transactional(readOnly = true)
    public List<Inscripcion> obtenerTodas() {
        return inscripcionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Inscripcion> obtenerPorUsuario(Long usuarioId) {
        // valida existencia de usuario (opcional pero recomendable)
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));
        return inscripcionRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<Inscripcion> obtenerPorCurso(Long cursoId) {
        cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + cursoId));
        return inscripcionRepository.findByCursoId(cursoId);
    }

    @Transactional(readOnly = true)
    public Inscripcion obtenerPorId(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con ID: " + id));
    }

    @Transactional
    public void eliminarInscripcion(Long id) {
        Inscripcion inscripcion = obtenerPorId(id);
        inscripcionRepository.delete(inscripcion);
    }
}
