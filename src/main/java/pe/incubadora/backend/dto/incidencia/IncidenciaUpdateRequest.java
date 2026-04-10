package pe.incubadora.backend.dto.incidencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.incubadora.backend.entity.PrioridadIncidencia;

public record IncidenciaUpdateRequest(
        @NotBlank @Size(max = 160) String titulo,
        @NotBlank @Size(max = 1000) String descripcion,
        @NotNull PrioridadIncidencia prioridad
) {
}
