package pe.incubadora.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.incubadora.backend.entity.OrdenTrabajoRepuesto;

public interface OrdenTrabajoRepuestoRepository extends JpaRepository<OrdenTrabajoRepuesto, Long> {

    List<OrdenTrabajoRepuesto> findByOrdenTrabajoId(Long ordenTrabajoId);

    void deleteByOrdenTrabajoId(Long ordenTrabajoId);
}
