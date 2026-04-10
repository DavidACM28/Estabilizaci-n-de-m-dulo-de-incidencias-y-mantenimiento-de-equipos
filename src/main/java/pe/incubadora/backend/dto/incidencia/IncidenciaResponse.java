package pe.incubadora.backend.dto.incidencia;

import java.time.LocalDateTime;
import pe.incubadora.backend.dto.common.EquipoSummaryResponse;
import pe.incubadora.backend.dto.common.SedeSummaryResponse;
import pe.incubadora.backend.entity.EstadoIncidencia;
import pe.incubadora.backend.entity.PrioridadIncidencia;
import pe.incubadora.backend.entity.TipoIncidencia;

public record IncidenciaResponse(
        Long id,
        String codigo,
        String titulo,
        String descripcion,
        TipoIncidencia tipo,
        PrioridadIncidencia prioridad,
        EstadoIncidencia estado,
        LocalDateTime fechaReporte,
        LocalDateTime fechaLimiteAtencion,
        LocalDateTime fechaRevision,
        LocalDateTime fechaResolucion,
        LocalDateTime fechaCierre,
        String comentarioRevision,
        SedeSummaryResponse sede,
        EquipoSummaryResponse equipo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
