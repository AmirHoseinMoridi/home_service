package com.example.home_service.base.util;

import com.example.home_service.base.domain.User;
import com.example.home_service.config.CustomUserDetails;
import com.example.home_service.entity.enumaration.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SuppressWarnings("unused")
public class SecurityUtil {

    private SecurityUtil() {
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static User getCurrentUser() {
        return isAuthenticated() ?
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser() : null;
    }

    public static Long getCurrentUserId() {
        return isAuthenticated() ?
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId()
                : null;
    }

    public static String getCurrentUsername() {
        return isAuthenticated() ?
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUsername()
                : null;
    }

    public static Role getCurrentUserType() {
        return isAuthenticated() ?
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getRole()
                : null;
    }

}
