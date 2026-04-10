package pe.incubadora.backend.dto.orden;

import java.time.LocalDateTime;
import pe.incubadora.backend.dto.repuesto.RepuestoResponse;

public record OrdenTrabajoRepuestoResponse(
        Long id,
        Integer cantidad,
        RepuestoResponse repuesto,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
