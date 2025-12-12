package portal_capacitaciones.portal_capacitaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal_capacitaciones.portal_capacitaciones.model.Inscripcion;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    boolean existsByUsuarioIdAndCursoId(Long usuarioId, Long cursoId);

    List<Inscripcion> findByUsuarioId(Long usuarioId);

    List<Inscripcion> findByCursoId(Long cursoId);
}
