package com.github.salvatorenovelli.seo.websiteversioning.security;


import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final SecurityContext securityContext;

    public UserService(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public String getCurrentUserName() {
        return securityContext.getAuthentication().getName();
    }
}
