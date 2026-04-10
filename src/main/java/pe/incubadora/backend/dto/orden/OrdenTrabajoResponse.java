package pe.incubadora.backend.dto.orden;

import java.time.LocalDateTime;
import java.util.List;
import pe.incubadora.backend.dto.common.IncidenciaSummaryResponse;
import pe.incubadora.backend.dto.common.UserSummaryResponse;
import pe.incubadora.backend.entity.EstadoOrdenTrabajo;

public record OrdenTrabajoResponse(
        Long id,
        String codigo,
        EstadoOrdenTrabajo estado,
        String descripcionTrabajo,
        String diagnosticoInicial,
        String observacionCierre,
        LocalDateTime fechaAsignacion,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        IncidenciaSummaryResponse incidencia,
        UserSummaryResponse tecnicoAsignado,
        List<OrdenTrabajoRepuestoResponse> repuestos,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
