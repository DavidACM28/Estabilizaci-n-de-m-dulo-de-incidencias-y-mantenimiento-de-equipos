package pe.incubadora.backend.dto.repuesto;

import java.time.LocalDateTime;

public record RepuestoResponse(
        Long id,
        String codigo,
        String nombre,
        String descripcion,
        String unidadMedida,
        Integer stockActual,
        Integer stockMinimo,
        boolean stockBajo,
        boolean activo,
        Long version,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
