package com.weiwei.service.controller.impl;

public interface ExternalServiceAuthenticator {

	AuthenticationWithToken authenticate(String username, String password);
}
