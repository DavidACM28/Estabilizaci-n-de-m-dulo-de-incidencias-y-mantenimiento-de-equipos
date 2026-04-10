package pe.incubadora.backend.dto.common;

import pe.incubadora.backend.entity.Role;

public record UserSummaryResponse(
        Long id,
        String email,
        String nombreCompleto,
        Role role
) {
}
