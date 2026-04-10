package pe.incubadora.backend.mapper;

import org.springframework.stereotype.Component;
import pe.incubadora.backend.dto.repuesto.RepuestoRequest;
import pe.incubadora.backend.dto.repuesto.RepuestoResponse;
import pe.incubadora.backend.entity.Repuesto;

@Component
public class RepuestoMapper {

    public void updateEntity(RepuestoRequest request, Repuesto repuesto) {
        repuesto.setCodigo(request.codigo().trim().toUpperCase());
        repuesto.setNombre(request.nombre().trim());
        repuesto.setDescripcion(request.descripcion());
        repuesto.setUnidadMedida(request.unidadMedida().trim());
        repuesto.setStockActual(request.stockActual());
        repuesto.setStockMinimo(request.stockMinimo());
        repuesto.setActivo(request.activo());
    }

    public RepuestoResponse toResponse(Repuesto repuesto) {
        return new RepuestoResponse(
                repuesto.getId(),
                repuesto.getCodigo(),
                repuesto.getNombre(),
                repuesto.getDescripcion(),
                repuesto.getUnidadMedida(),
                repuesto.getStockActual(),
                repuesto.getStockMinimo(),
                repuesto.getStockActual() <= repuesto.getStockMinimo(),
                repuesto.isActivo(),
                repuesto.getVersion(),
                repuesto.getCreatedAt(),
                repuesto.getUpdatedAt()
        );
    }
}
