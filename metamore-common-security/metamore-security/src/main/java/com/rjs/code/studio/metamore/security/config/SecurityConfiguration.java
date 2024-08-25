package com.rjs.code.studio.metamore.security.config;

import com.rjs.code.studio.metamore.security.impl.UserVerificationImpl;
import com.rjs.code.studio.metamore.security.service.CustomTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomTokenService tokenService;

    @Autowired
    UserDetailsService userService;

    @Autowired
    UserVerificationImpl userVerificationImpl;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers().permitAll().anyRequest().authenticated()
                .and().addFilterBefore(new LoginFilter("/metamore/login", tokenService, userService,
                        authenticationManager(),"USERNAME",userVerificationImpl),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomTokenAuthenticationFilter(tokenService, userService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
