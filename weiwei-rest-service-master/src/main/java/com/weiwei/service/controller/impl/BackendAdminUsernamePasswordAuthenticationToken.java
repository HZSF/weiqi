package com.weiwei.service.controller.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class BackendAdminUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public BackendAdminUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}