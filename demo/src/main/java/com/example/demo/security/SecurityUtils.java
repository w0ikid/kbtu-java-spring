package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Long currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new IllegalStateException("Unauthenticated");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof Long id)
            return id;
        if (principal instanceof String s)
            return Long.parseLong(s);
        
        throw new IllegalStateException("Unsupported principal type: " + principal.getClass());
    }
}
