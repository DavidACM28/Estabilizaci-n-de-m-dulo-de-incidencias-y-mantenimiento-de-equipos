package pe.incubadora.backend.dto.orden;

import jakarta.validation.constraints.NotNull;

public record OrdenTrabajoRepuestoRequest(
        @NotNull Long repuestoId,
        @NotNull Integer cantidad
) {
}
