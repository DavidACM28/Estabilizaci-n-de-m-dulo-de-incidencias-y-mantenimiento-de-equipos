package pe.incubadora.backend.dto.auth;

import pe.incubadora.backend.entity.Role;

public record AuthenticatedUserResponse(
        Long id,
        String email,
        String nombreCompleto,
        Role role,
        Long sedeId,
        String sedeNombre
) {
}
