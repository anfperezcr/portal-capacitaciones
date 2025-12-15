package portal_capacitaciones.portal_capacitaciones.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portal_capacitaciones.portal_capacitaciones.dto.InsigniaRequestDTO;
import portal_capacitaciones.portal_capacitaciones.exceptions.DuplicateResourceException;
import portal_capacitaciones.portal_capacitaciones.exceptions.ResourceNotFoundException;
import portal_capacitaciones.portal_capacitaciones.model.Curso;
import portal_capacitaciones.portal_capacitaciones.model.Insignia;
import portal_capacitaciones.portal_capacitaciones.model.Usuario;
import portal_capacitaciones.portal_capacitaciones.repository.CursoRepository;
import portal_capacitaciones.portal_capacitaciones.repository.InsigniaRepository;
import portal_capacitaciones.portal_capacitaciones.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InsigniaService {

    private final InsigniaRepository insigniaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public InsigniaService(
            InsigniaRepository insigniaRepository,
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository
    ) {
        this.insigniaRepository = insigniaRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    // Crear insignia
    @Transactional
    public Insignia crearInsignia(InsigniaRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

        // Evitar duplicados por usuario-curso
        if (insigniaRepository.existsByUsuarioIdAndCursoId(dto.getUsuarioId(), dto.getCursoId())) {
            throw new DuplicateResourceException("Este usuario ya tiene una insignia de este curso.");
        }

        // Elegir imagen según el curso
        String imagen;

        switch (curso.getModulo()) {
            case APIS -> imagen = "apis.png";
            case CLOUD -> imagen = "cloud.png";
            case DATA -> imagen = "data.png";
            case FULLSTACK -> imagen = "fullstack.png";
            default -> imagen = "default.png";
        }

        Insignia insignia = new Insignia();
        insignia.setImagen(imagen);
        insignia.setFechaOtorgada(LocalDateTime.now());
        insignia.setUsuario(usuario);
        insignia.setCurso(curso);

        return insigniaRepository.save(insignia);
    }

    // Obtener todas
    @Transactional(readOnly = true)
    public List<Insignia> obtenerTodas() {
        return insigniaRepository.findAll();
    }

    // Obtener por ID
    @Transactional(readOnly = true)
    public Insignia obtenerPorId(Long id) {
        return insigniaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insignia no encontrada con ID: " + id));
    }

    // Eliminar
    @Transactional
    public void eliminarInsignia(Long id) {
        Insignia insignia = obtenerPorId(id);
        insigniaRepository.delete(insignia);
    }
}
