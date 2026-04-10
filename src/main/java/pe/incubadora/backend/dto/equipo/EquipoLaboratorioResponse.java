package pe.incubadora.backend.dto.equipo;

import java.time.LocalDateTime;
import pe.incubadora.backend.dto.common.SedeSummaryResponse;
import pe.incubadora.backend.entity.EstadoEquipo;

public record EquipoLaboratorioResponse(
        Long id,
        String codigoPatrimonial,
        String nombre,
        String marca,
        String modelo,
        String numeroSerie,
        String area,
        boolean activo,
        EstadoEquipo estado,
        SedeSummaryResponse sede,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
