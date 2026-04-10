package pe.incubadora.backend.mapper;

import org.springframework.stereotype.Component;
import pe.incubadora.backend.dto.common.SedeSummaryResponse;
import pe.incubadora.backend.dto.sede.SedeRequest;
import pe.incubadora.backend.dto.sede.SedeResponse;
import pe.incubadora.backend.entity.Sede;

@Component
public class SedeMapper {

    public void updateEntity(SedeRequest request, Sede sede) {
        sede.setCodigo(request.codigo().trim().toUpperCase());
        sede.setNombre(request.nombre().trim());
        sede.setCiudad(request.ciudad().trim());
        sede.setDireccion(request.direccion().trim());
        sede.setActiva(request.activa());
    }

    public SedeResponse toResponse(Sede sede) {
        return new SedeResponse(
                sede.getId(),
                sede.getCodigo(),
                sede.getNombre(),
                sede.getCiudad(),
                sede.getDireccion(),
                sede.isActiva(),
                sede.getCreatedAt(),
                sede.getUpdatedAt()
        );
    }

    public SedeSummaryResponse toSummary(Sede sede) {
        return new SedeSummaryResponse(
                sede.getId(),
                sede.getCodigo(),
                sede.getNombre(),
                sede.getCiudad()
        );
    }
}
