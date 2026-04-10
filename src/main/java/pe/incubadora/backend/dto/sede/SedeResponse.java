package pe.incubadora.backend.dto.sede;

import java.time.LocalDateTime;

public record SedeResponse(
        Long id,
        String codigo,
        String nombre,
        String ciudad,
        String direccion,
        boolean activa,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
