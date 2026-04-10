package pe.incubadora.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.incubadora.backend.dto.common.EquipoSummaryResponse;
import pe.incubadora.backend.dto.equipo.EquipoLaboratorioRequest;
import pe.incubadora.backend.dto.equipo.EquipoLaboratorioResponse;
import pe.incubadora.backend.entity.EquipoLaboratorio;

@Component
@RequiredArgsConstructor
public class EquipoLaboratorioMapper {

    private final SedeMapper sedeMapper;

    public void updateEntity(EquipoLaboratorioRequest request, EquipoLaboratorio equipo) {
        equipo.setCodigoPatrimonial(request.codigoPatrimonial().trim().toUpperCase());
        equipo.setNombre(request.nombre().trim());
        equipo.setMarca(request.marca());
        equipo.setModelo(request.modelo());
        equipo.setNumeroSerie(request.numeroSerie());
        equipo.setArea(request.area());
        equipo.setActivo(request.activo());
        equipo.setEstado(request.estado());
    }

    public EquipoLaboratorioResponse toResponse(EquipoLaboratorio equipo) {
        return new EquipoLaboratorioResponse(
                equipo.getId(),
                equipo.getCodigoPatrimonial(),
                equipo.getNombre(),
                equipo.getMarca(),
                equipo.getModelo(),
                equipo.getNumeroSerie(),
                equipo.getArea(),
                equipo.isActivo(),
                equipo.getEstado(),
                sedeMapper.toSummary(equipo.getSede()),
                equipo.getCreatedAt(),
                equipo.getUpdatedAt()
        );
    }

    public EquipoSummaryResponse toSummary(EquipoLaboratorio equipo) {
        return new EquipoSummaryResponse(
                equipo.getId(),
                equipo.getCodigoPatrimonial(),
                equipo.getNombre(),
                equipo.getEstado(),
                equipo.isActivo(),
                sedeMapper.toSummary(equipo.getSede())
        );
    }
}
