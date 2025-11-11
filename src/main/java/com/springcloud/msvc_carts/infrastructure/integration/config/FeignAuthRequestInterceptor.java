package com.springcloud.msvc_carts.infrastructure.integration.config;

import com.springcloud.msvc_carts.infrastructure.security.AuthUser;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.stream.Collectors;

@Configuration
public class FeignAuthRequestInterceptor implements RequestInterceptor {

    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_ROLES_HEADER = "X-User-Roles";
    private static final String USER_EMAIL_HEADER = "X-User-Email";

    @Override
    public void apply(RequestTemplate template) {

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AuthUser) {

            AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String roles = authUser.getAuthorities().stream()
                    .map(g -> g.getAuthority().replace("ROLE_", ""))
                    .collect(Collectors.joining(","));

            template.header(USER_ID_HEADER, authUser.getId().toString());
            template.header(USER_ROLES_HEADER, roles);
            template.header(USER_EMAIL_HEADER, authUser.getEmail());
        }
    }
}
