package com.rjs.code.studio.metamore.security.impl;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserVerificationImpl {

    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response);
}
