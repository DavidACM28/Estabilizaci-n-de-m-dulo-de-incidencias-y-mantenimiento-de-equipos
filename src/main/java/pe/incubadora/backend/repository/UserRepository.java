package pe.incubadora.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.incubadora.backend.entity.Role;
import pe.incubadora.backend.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    Optional<UserEntity> findByEmailIgnoreCaseAndActivoTrue(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByRole(Role role);
}
