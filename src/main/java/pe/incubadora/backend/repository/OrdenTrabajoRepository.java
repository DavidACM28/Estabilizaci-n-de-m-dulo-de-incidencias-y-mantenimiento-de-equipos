package pe.incubadora.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pe.incubadora.backend.entity.OrdenTrabajo;

public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long>, JpaSpecificationExecutor<OrdenTrabajo> {

    boolean existsByIncidenciaId(Long incidenciaId);

    Optional<OrdenTrabajo> findByIncidenciaId(Long incidenciaId);
}
