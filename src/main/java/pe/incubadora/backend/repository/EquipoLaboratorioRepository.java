package pe.incubadora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pe.incubadora.backend.entity.EquipoLaboratorio;

public interface EquipoLaboratorioRepository extends JpaRepository<EquipoLaboratorio, Long>, JpaSpecificationExecutor<EquipoLaboratorio> {

    boolean existsByCodigoPatrimonialIgnoreCase(String codigoPatrimonial);
}
