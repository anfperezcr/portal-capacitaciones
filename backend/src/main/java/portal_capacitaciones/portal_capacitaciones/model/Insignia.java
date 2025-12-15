package portal_capacitaciones.portal_capacitaciones.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "insignias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Insignia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagen;  // ruta o nombre del archivo en static/

    private LocalDateTime fechaOtorgada;

    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    @JsonIgnore
    private Curso curso;
}
