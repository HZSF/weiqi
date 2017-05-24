package com.weiwei.intellectual.secure.processor;

import java.net.URLDecoder;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.intellectual.common.request.PtntAddAnnFeeMoRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IPatentManager;

public class SecAddPtntAnnFeeMonitorProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	private PtntAddAnnFeeMoRequest request;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (PtntAddAnnFeeMoRequest)scopes.get(Constants.PATENT_ADD_ANN_FEE_MONITOR_REQUEST);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		try{
			String patentTitle = URLDecoder.decode(URLDecoder.decode(request.getPatentTitle(), "UTF-8"), "UTF-8");
			String applicant = URLDecoder.decode(URLDecoder.decode(request.getApplicant(), "UTF-8"), "UTF-8");
			ctx = new ClassPathXmlApplicationContext("classpath*:intellectual/Patent.xml");
			IPatentManager patentManager = (IPatentManager) ctx.getBean("patentManagerImpl");
			String customerID = patentManager.addAnnualFeeMonitorByUsername(username, request.getPatentId(), patentTitle, request.getApplyDate(), applicant);
			
			
			String link = "http://120.55.96.224/ajaxCall/annualFeeMonitor.ashx?";
			link += "id=";
			link += request.getPatentId();
			link += "&applyDate=";
			link += request.getApplyDate();
			link += "&userId=";
			link += customerID;
			link += "&applicant=";
			link += URLDecoder.decode(request.getApplicant(), "UTF-8");
			link += "&title=";
			link += URLDecoder.decode(request.getPatentTitle(), "UTF-8");
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(link);
			HttpResponse response = client.execute(request);
			String result = EntityUtils.toString(response.getEntity());
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
	}
}
