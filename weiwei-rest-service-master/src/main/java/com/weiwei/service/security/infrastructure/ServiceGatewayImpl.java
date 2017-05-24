package com.weiwei.service.security.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weiwei.service.api.samplestuff.ServiceGateway;
import com.weiwei.service.domain.DomainUser;
import com.weiwei.service.domain.Stuff;

import java.util.List;

@Component
public class ServiceGatewayImpl extends ServiceGatewayBase implements ServiceGateway {

    @Autowired
    public ServiceGatewayImpl(AuthenticatedExternalServiceProvider authenticatedExternalServiceProvider) {
        super(authenticatedExternalServiceProvider);
    }

    @Override
    public List<Stuff> getSomeStuff() {
        String stuffFromExternalWebService = externalService().getSomeStuff();
        // do some processing, create return list
        return null;
    }

    @Override
    public void createStuff(Stuff newStuff, DomainUser domainUser) {
        // do some processing, store domainUser in newStuff, send newStuff over the wire to external web service etc.
    }
}