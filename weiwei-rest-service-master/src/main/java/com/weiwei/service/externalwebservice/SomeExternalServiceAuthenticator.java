package com.weiwei.service.externalwebservice;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;

import com.weiwei.service.controller.impl.ExternalServiceAuthenticator;
import com.weiwei.service.domain.DomainUser;
import com.weiwei.service.security.infrastructure.AuthenticatedExternalWebService;
import com.weiwei.svr.manage.ICustomerManager;

public class SomeExternalServiceAuthenticator implements ExternalServiceAuthenticator {
	
	private ICustomerManager customerManager;
	
	public ICustomerManager getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(ICustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	@Override
    public AuthenticatedExternalWebService authenticate(String username, String password) {
        ExternalWebServiceStub externalWebService = new ExternalWebServiceStub();

        // Do all authentication mechanisms required by external web service protocol and validated response.
        // Throw descendant of Spring AuthenticationException in case of unsucessful authentication. For example BadCredentialsException

        authenticateFromDB(username, password);

        // If authentication to external service succeeded then create authenticated wrapper with proper Principal and GrantedAuthorities.
        // GrantedAuthorities may come from external service authentication or be hardcoded at our layer as they are here with ROLE_DOMAIN_USER
        AuthenticatedExternalWebService authenticatedExternalWebService = new AuthenticatedExternalWebService(new DomainUser(username), null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DOMAIN_USER"));
        authenticatedExternalWebService.setExternalWebService(externalWebService);

        return authenticatedExternalWebService;
    }
	
	private void authenticateFromDB(String username, String password){
		if(!customerManager.authenticatePassword(username, password)){
			throw new BadCredentialsException("Bad credentials");
		}
	}
}
