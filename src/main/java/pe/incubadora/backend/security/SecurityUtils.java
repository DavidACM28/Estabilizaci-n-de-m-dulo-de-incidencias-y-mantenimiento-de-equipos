package pe.incubadora.backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pe.incubadora.backend.entity.Role;
import pe.incubadora.backend.exception.ForbiddenException;

@Component
public class SecurityUtils {

    public UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new ForbiddenException("FORBIDDEN", "No se pudo obtener el usuario autenticado");
        }
        return principal;
    }

    public boolean hasAnyRole(Role... roles) {
        UserPrincipal principal = getCurrentUser();
        for (Role role : roles) {
            if (principal.getRole() == role) {
                return true;
            }
        }
        return false;
    }
}
