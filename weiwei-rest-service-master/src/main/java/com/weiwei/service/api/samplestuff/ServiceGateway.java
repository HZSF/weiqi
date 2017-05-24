package com.weiwei.service.api.samplestuff;

import java.util.List;

import com.weiwei.service.domain.DomainUser;
import com.weiwei.service.domain.Stuff;

public interface ServiceGateway {
    List<Stuff> getSomeStuff();

    void createStuff(Stuff newStuff, DomainUser domainUser);
}
