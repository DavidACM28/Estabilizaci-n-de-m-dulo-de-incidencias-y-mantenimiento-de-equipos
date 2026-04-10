package pe.incubadora.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.incubadora.backend.dto.common.IncidenciaSummaryResponse;
import pe.incubadora.backend.dto.incidencia.IncidenciaResponse;
import pe.incubadora.backend.dto.incidencia.IncidenciaUpdateRequest;
import pe.incubadora.backend.entity.Incidencia;

@Component
@RequiredArgsConstructor
public class IncidenciaMapper {

    private final SedeMapper sedeMapper;
    private final EquipoLaboratorioMapper equipoLaboratorioMapper;

    public void updateEntity(IncidenciaUpdateRequest request, Incidencia incidencia) {
        incidencia.setTitulo(request.titulo().trim());
        incidencia.setDescripcion(request.descripcion().trim());
        incidencia.setPrioridad(request.prioridad());
    }

    public IncidenciaResponse toResponse(Incidencia incidencia) {
        return new IncidenciaResponse(
                incidencia.getId(),
                incidencia.getCodigo(),
                incidencia.getTitulo(),
                incidencia.getDescripcion(),
                incidencia.getTipo(),
                incidencia.getPrioridad(),
                incidencia.getEstado(),
                incidencia.getFechaReporte(),
                incidencia.getFechaLimiteAtencion(),
                incidencia.getFechaRevision(),
                incidencia.getFechaResolucion(),
                incidencia.getFechaCierre(),
                incidencia.getComentarioRevision(),
                sedeMapper.toSummary(incidencia.getSede()),
                equipoLaboratorioMapper.toSummary(incidencia.getEquipo()),
                incidencia.getCreatedAt(),
                incidencia.getUpdatedAt()
        );
    }

    public IncidenciaSummaryResponse toSummary(Incidencia incidencia) {
        return new IncidenciaSummaryResponse(
                incidencia.getId(),
                incidencia.getCodigo(),
                incidencia.getTitulo(),
                incidencia.getEstado(),
                incidencia.getPrioridad(),
                sedeMapper.toSummary(incidencia.getSede())
        );
    }
}
