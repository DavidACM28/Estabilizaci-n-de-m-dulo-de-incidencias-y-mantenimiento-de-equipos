package pe.incubadora.backend.dto.incidencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IncidenciaRejectRequest(
        @NotBlank @Size(max = 500) String comentario
) {
}
