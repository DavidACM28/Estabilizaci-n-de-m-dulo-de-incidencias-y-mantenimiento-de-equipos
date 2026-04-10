package pe.incubadora.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pe.incubadora.backend.entity.Sede;

public interface SedeRepository extends JpaRepository<Sede, Long>, JpaSpecificationExecutor<Sede> {

    boolean existsByCodigoIgnoreCase(String codigo);

    Optional<Sede> findByCodigoIgnoreCase(String codigo);
}
