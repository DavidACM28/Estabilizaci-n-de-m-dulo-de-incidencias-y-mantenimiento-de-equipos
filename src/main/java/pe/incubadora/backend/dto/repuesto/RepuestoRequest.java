package pe.incubadora.backend.dto.repuesto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RepuestoRequest(
        @NotBlank @Size(max = 40) String codigo,
        @NotBlank @Size(max = 150) String nombre,
        @Size(max = 500) String descripcion,
        @NotBlank @Size(max = 30) String unidadMedida,
        @NotNull @Min(0) Integer stockActual,
        @NotNull @Min(0) Integer stockMinimo,
        @NotNull Boolean activo
) {
}
