package pe.incubadora.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ordenes_trabajo")
public class OrdenTrabajo extends BaseEntity {

    @Column(nullable = false, unique = true, length = 40)
    private String codigo;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incidencia_id", nullable = false, unique = true)
    private Incidencia incidencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoOrdenTrabajo estado;

    @Column(nullable = false, length = 1000)
    private String descripcionTrabajo;

    @Column(length = 1000)
    private String diagnosticoInicial;

    @Column(length = 1000)
    private String observacionCierre;

    private LocalDateTime fechaAsignacion;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_asignado_id")
    private UserEntity tecnicoAsignado;
}
