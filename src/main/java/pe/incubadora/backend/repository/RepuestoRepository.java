package pe.incubadora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pe.incubadora.backend.entity.Repuesto;

public interface RepuestoRepository extends JpaRepository<Repuesto, Long>, JpaSpecificationExecutor<Repuesto> {

    boolean existsByCodigoIgnoreCase(String codigo);
}
