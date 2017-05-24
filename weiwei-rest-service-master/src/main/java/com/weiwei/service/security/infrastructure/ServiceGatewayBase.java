package com.weiwei.service.security.infrastructure;

import com.weiwei.service.externalwebservice.ExternalWebServiceStub;

public abstract class ServiceGatewayBase {

	private AuthenticatedExternalServiceProvider authenticatedExternalServiceProvider;
	public ServiceGatewayBase(AuthenticatedExternalServiceProvider authenticatedExternalServiceProvider) {
		this.authenticatedExternalServiceProvider = authenticatedExternalServiceProvider;
	}
	protected ExternalWebServiceStub externalService() {
		return authenticatedExternalServiceProvider.provide().getExternalWebService();
	}
}
