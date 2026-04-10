package pe.incubadora.backend.dto.sede;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SedeRequest(
        @NotBlank @Size(max = 30) String codigo,
        @NotBlank @Size(max = 150) String nombre,
        @NotBlank @Size(max = 80) String ciudad,
        @NotBlank @Size(max = 250) String direccion,
        @NotNull Boolean activa
) {
}
