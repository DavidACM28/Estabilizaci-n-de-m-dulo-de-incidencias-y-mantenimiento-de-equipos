package pe.incubadora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pe.incubadora.backend.entity.EstadoIncidencia;
import pe.incubadora.backend.entity.Incidencia;
import pe.incubadora.backend.entity.TipoIncidencia;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long>, JpaSpecificationExecutor<Incidencia> {

    boolean existsByEquipoIdAndTipoAndEstadoIn(Long equipoId, TipoIncidencia tipo, Iterable<EstadoIncidencia> estados);
}
