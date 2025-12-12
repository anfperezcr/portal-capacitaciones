package portal_capacitaciones.portal_capacitaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal_capacitaciones.portal_capacitaciones.model.Progreso;

@Repository
public interface ProgresoRepository extends JpaRepository<Progreso, Long> {

    boolean existsByUsuarioIdAndCursoId(Long usuarioId, Long cursoId);
}
