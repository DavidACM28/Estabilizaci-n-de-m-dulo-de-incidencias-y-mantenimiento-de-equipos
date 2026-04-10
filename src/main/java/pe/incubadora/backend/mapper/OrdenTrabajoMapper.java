package pe.incubadora.backend.mapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.incubadora.backend.dto.orden.OrdenTrabajoRepuestoResponse;
import pe.incubadora.backend.dto.orden.OrdenTrabajoResponse;
import pe.incubadora.backend.dto.orden.OrdenTrabajoUpdateRequest;
import pe.incubadora.backend.entity.OrdenTrabajo;
import pe.incubadora.backend.entity.OrdenTrabajoRepuesto;

@Component
@RequiredArgsConstructor
public class OrdenTrabajoMapper {

    private final IncidenciaMapper incidenciaMapper;
    private final UserMapper userMapper;
    private final RepuestoMapper repuestoMapper;

    public void updateEntity(OrdenTrabajoUpdateRequest request, OrdenTrabajo ordenTrabajo) {
        ordenTrabajo.setDescripcionTrabajo(request.descripcionTrabajo().trim());
        ordenTrabajo.setDiagnosticoInicial(request.diagnosticoInicial());
    }

    public OrdenTrabajoResponse toResponse(OrdenTrabajo ordenTrabajo, List<OrdenTrabajoRepuesto> repuestos) {
        return new OrdenTrabajoResponse(
                ordenTrabajo.getId(),
                ordenTrabajo.getCodigo(),
                ordenTrabajo.getEstado(),
                ordenTrabajo.getDescripcionTrabajo(),
                ordenTrabajo.getDiagnosticoInicial(),
                ordenTrabajo.getObservacionCierre(),
                ordenTrabajo.getFechaAsignacion(),
                ordenTrabajo.getFechaInicio(),
                ordenTrabajo.getFechaFin(),
                incidenciaMapper.toSummary(ordenTrabajo.getIncidencia()),
                userMapper.toSummary(ordenTrabajo.getTecnicoAsignado()),
                repuestos.stream().map(this::toRepuestoResponse).toList(),
                ordenTrabajo.getCreatedAt(),
                ordenTrabajo.getUpdatedAt()
        );
    }

    private OrdenTrabajoRepuestoResponse toRepuestoResponse(OrdenTrabajoRepuesto ordenTrabajoRepuesto) {
        return new OrdenTrabajoRepuestoResponse(
                ordenTrabajoRepuesto.getId(),
                ordenTrabajoRepuesto.getCantidad(),
                repuestoMapper.toResponse(ordenTrabajoRepuesto.getRepuesto()),
                ordenTrabajoRepuesto.getCreatedAt(),
                ordenTrabajoRepuesto.getUpdatedAt()
        );
    }
}
