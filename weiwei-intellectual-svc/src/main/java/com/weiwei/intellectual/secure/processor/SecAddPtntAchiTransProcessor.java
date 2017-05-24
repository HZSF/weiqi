package com.weiwei.intellectual.secure.processor;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.intellectual.common.request.PtntAddAchiTransRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IPatentManager;

public class SecAddPtntAchiTransProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	private PtntAddAchiTransRequest request;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (PtntAddAchiTransRequest)scopes.get(Constants.PATENT_ADD_ARCHIEVE_TRANS_REQUEST);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		try{
			ctx = new ClassPathXmlApplicationContext("classpath*:intellectual/Patent.xml");
			IPatentManager patentManager = (IPatentManager) ctx.getBean("patentManagerImpl");
			String title = URLDecoder.decode(URLDecoder.decode(request.getTitle(), "UTF-8"), "UTF-8");
			return patentManager.addAchieveTransForm(username, request.getPatentId(), title, request.getPrice());
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
	}
}
