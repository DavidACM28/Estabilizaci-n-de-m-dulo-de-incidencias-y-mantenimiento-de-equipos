package pe.incubadora.backend.dto.orden;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrdenTrabajoRequest(
        @NotNull Long incidenciaId,
        @NotBlank @Size(max = 1000) String descripcionTrabajo,
        @Size(max = 1000) String diagnosticoInicial
) {
}
