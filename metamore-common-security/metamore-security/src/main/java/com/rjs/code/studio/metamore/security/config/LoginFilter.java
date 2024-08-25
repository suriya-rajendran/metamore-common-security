package com.rjs.code.studio.metamore.security.config;

import com.rjs.code.studio.metamore.common.security.datamodel.UserDetails;
import com.rjs.code.studio.metamore.security.impl.UserVerificationImpl;
import com.rjs.code.studio.metamore.security.service.CustomTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String xtoken="AUTH-TOKEN";

    private final CustomTokenService tokenService;

    private final String verificationType;

    private final UserDetailsService userDetailsService;

    private final UserVerificationImpl userVerificationImpl;

    public LoginFilter(String url, CustomTokenService tokenService,
                       UserDetailsService userDetailsService, AuthenticationManager authManager, String verificationType,UserVerificationImpl userVerificationImpl) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(url, "POST"));
        setAuthenticationManager(authManager);
        this.verificationType=verificationType;
        this.userVerificationImpl=userVerificationImpl;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
      final UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername("");
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        return getAuthenticationManager().authenticate(authRequest);
        // return userVerificationImpl.attemptAuthentication(request,response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final UserDetails authenticatedUser = (UserDetails) userDetailsService.loadUserByUsername(authResult.getName());
        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

        String token = tokenService.generateToken(authResult.getName());
        response.addHeader(xtoken, "Bearer " + token);

        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        super.successfulAuthentication(request, response, chain, userAuthentication);

    }
}
