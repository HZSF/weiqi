package com.weiwei.svr.infra;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConnectionManager {
	
	public ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:Infra-Module.xml");

	//public ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:Announcement.xml");
	public ApplicationContext getContext(){
		return context;
	}
}
