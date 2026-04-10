package pe.incubadora.backend.dto.orden;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record OrdenTrabajoFinalizeRequest(
        @NotBlank @Size(max = 1000) String observacionCierre,
        @Valid List<OrdenTrabajoRepuestoRequest> repuestos
) {
}
