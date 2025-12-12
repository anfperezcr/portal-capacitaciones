package portal_capacitaciones.portal_capacitaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal_capacitaciones.portal_capacitaciones.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    boolean existsByNombre(String titulo);
}
