package pe.incubadora.backend.dto.orden;

import jakarta.validation.constraints.NotNull;

public record OrdenTrabajoAssignRequest(
        @NotNull Long tecnicoId
) {
}
