package pe.incubadora.backend.dto.common;

public record SedeSummaryResponse(
        Long id,
        String codigo,
        String nombre,
        String ciudad
) {
}
