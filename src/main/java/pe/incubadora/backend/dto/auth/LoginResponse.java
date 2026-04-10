package pe.incubadora.backend.dto.auth;

public record LoginResponse(
        String tokenType,
        String accessToken,
        Long expiresInMinutes,
        AuthenticatedUserResponse user
) {
}
