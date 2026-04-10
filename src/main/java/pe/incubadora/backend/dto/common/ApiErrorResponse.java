package pe.incubadora.backend.dto.common;

public record ApiErrorResponse(
        String code,
        String message
) {
}
