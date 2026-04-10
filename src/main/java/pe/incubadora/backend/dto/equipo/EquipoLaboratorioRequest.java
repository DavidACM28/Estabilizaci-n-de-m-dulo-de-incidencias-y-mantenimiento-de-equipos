package pe.incubadora.backend.dto.equipo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.incubadora.backend.entity.EstadoEquipo;

public record EquipoLaboratorioRequest(
        @NotBlank @Size(max = 40) String codigoPatrimonial,
        @NotBlank @Size(max = 150) String nombre,
        @Size(max = 80) String marca,
        @Size(max = 80) String modelo,
        @Size(max = 80) String numeroSerie,
        @Size(max = 120) String area,
        @NotNull Boolean activo,
        @NotNull EstadoEquipo estado,
        @NotNull Long sedeId
) {
}
