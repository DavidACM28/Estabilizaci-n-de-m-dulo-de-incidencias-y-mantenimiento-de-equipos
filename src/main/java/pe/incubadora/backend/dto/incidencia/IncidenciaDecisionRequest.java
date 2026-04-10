package pe.incubadora.backend.dto.incidencia;

import jakarta.validation.constraints.Size;

public record IncidenciaDecisionRequest(
        @Size(max = 500) String comentario
) {
}
