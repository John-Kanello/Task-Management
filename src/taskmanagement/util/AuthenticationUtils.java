package taskmanagement.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import taskmanagement.model.Role;

import static taskmanagement.constant.AppConstants.SCOPE_PREFIX;

public class AuthenticationUtils {

    public static boolean hasAdminRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority != null ? authority.replaceAll(SCOPE_PREFIX, "") : "")
                .anyMatch(authority -> authority.equals(Role.ADMIN.name()));
    }
}
