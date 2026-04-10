package pe.incubadora.backend.mapper;

import org.springframework.stereotype.Component;
import pe.incubadora.backend.dto.auth.AuthenticatedUserResponse;
import pe.incubadora.backend.dto.common.UserSummaryResponse;
import pe.incubadora.backend.entity.UserEntity;

@Component
public class UserMapper {

    public UserSummaryResponse toSummary(UserEntity user) {
        if (user == null) {
            return null;
        }
        return new UserSummaryResponse(
                user.getId(),
                user.getEmail(),
                user.getNombreCompleto(),
                user.getRole()
        );
    }

    public AuthenticatedUserResponse toAuthenticatedResponse(UserEntity user) {
        return new AuthenticatedUserResponse(
                user.getId(),
                user.getEmail(),
                user.getNombreCompleto(),
                user.getRole(),
                user.getSede() != null ? user.getSede().getId() : null,
                user.getSede() != null ? user.getSede().getNombre() : null
        );
    }
}
