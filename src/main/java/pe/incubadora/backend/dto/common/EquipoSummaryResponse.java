package pe.incubadora.backend.dto.common;

import pe.incubadora.backend.entity.EstadoEquipo;

public record EquipoSummaryResponse(
        Long id,
        String codigoPatrimonial,
        String nombre,
        EstadoEquipo estado,
        boolean activo,
        SedeSummaryResponse sede
) {
}
