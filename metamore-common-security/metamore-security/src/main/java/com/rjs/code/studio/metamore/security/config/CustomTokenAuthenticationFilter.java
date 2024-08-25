package com.rjs.code.studio.metamore.security.config;

import com.rjs.code.studio.metamore.security.service.CustomTokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomTokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final CustomTokenService tokenService;
    private final UserDetailsService userService;

    public CustomTokenAuthenticationFilter(CustomTokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userService = userDetailsService;
    }

}
