package com.sercurity.authority.config;

import com.sercurity.authority.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.stream.Collectors;

@Configuration
public class JwtCustomizerConfig {
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {

        return context -> {
            System.out.println("Principal class: " + context.getPrincipal().getPrincipal().getClass().getName());
            if (context.getPrincipal() != null && context.getPrincipal().getPrincipal() instanceof UserDetails user) {
                var authorities = user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                context.getClaims().claim("authorities", authorities);
                context.getClaims().claim("username", user.getUsername());
            }
        };
    }
}
