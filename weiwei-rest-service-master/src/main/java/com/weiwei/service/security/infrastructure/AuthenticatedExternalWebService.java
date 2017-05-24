package com.weiwei.service.security.infrastructure;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.weiwei.service.controller.impl.AuthenticationWithToken;
import com.weiwei.service.externalwebservice.ExternalWebServiceStub;

public class AuthenticatedExternalWebService extends AuthenticationWithToken {

	private ExternalWebServiceStub externalWebService;

    public AuthenticatedExternalWebService(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
    }

    public void setExternalWebService(ExternalWebServiceStub externalWebService) {
        this.externalWebService = externalWebService;
    }

    public ExternalWebServiceStub getExternalWebService() {
        return externalWebService;
    }
}
