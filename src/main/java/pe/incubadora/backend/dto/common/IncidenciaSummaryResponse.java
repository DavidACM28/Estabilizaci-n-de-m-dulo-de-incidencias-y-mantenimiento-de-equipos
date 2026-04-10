package pe.incubadora.backend.dto.common;

import pe.incubadora.backend.entity.EstadoIncidencia;
import pe.incubadora.backend.entity.PrioridadIncidencia;

public record IncidenciaSummaryResponse(
        Long id,
        String codigo,
        String titulo,
        EstadoIncidencia estado,
        PrioridadIncidencia prioridad,
        SedeSummaryResponse sede
) {
}
