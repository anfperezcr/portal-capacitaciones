package portal_capacitaciones.portal_capacitaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
//Esta anotacion impone unicidad a la BD
@Table(name = "progresos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "curso_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Progreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCurso estado;  // INICIADO - COMPLETADO

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;

    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

}

