package com.weiwei.intellectual.secure.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.intellectual.beans.PatentAchievementTransformBean;
import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.intellectual.common.request.PatentAchievementTransformRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableAchievementTransform;
import com.weiwei.svr.manage.IPatentManager;

public class SecRetrievePatentAchievementTransformProcessor extends BaseProcessor{
	
	private PatentAchievementTransformRequest request;
	private List<PatentAchievementTransformBean> patentAchTransBeanList;
	protected ApplicationContext ctx;
	private int initPatentNumbers = 20;
	
	@Override
	protected void preProcess(Map scopes){
		request = (PatentAchievementTransformRequest)scopes.get(Constants.PATENT_ARCHIEVE_TRANSFORM_REQUEST);
		patentAchTransBeanList = new ArrayList<PatentAchievementTransformBean>();
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:intellectual/Patent.xml");
		IPatentManager patentManager = (IPatentManager) ctx.getBean("patentManagerImpl");
		List<TableAchievementTransform> tableAchiTransList = null;
		if(request == null || request.getStartFromId() == 0){
			tableAchiTransList = (List<TableAchievementTransform>) patentManager.findAchievementTransformLimitedNumbers(initPatentNumbers);
		}else{
			int numbers = request.getNumbers();
			if(numbers<=0){
				numbers = initPatentNumbers;
			}
			tableAchiTransList = (List<TableAchievementTransform>) patentManager.findAchievementTransformByStartIdLimitedNumbers(request.getStartFromId(),numbers);
		}
		if(tableAchiTransList != null && tableAchiTransList.size() > 0){
			for(TableAchievementTransform tableAchiTrans : tableAchiTransList){
				PatentAchievementTransformBean bean = new PatentAchievementTransformBean();
				bean.setId(tableAchiTrans.getId());
				bean.setPatent_id(tableAchiTrans.getPatent_id());
				bean.setTitle(tableAchiTrans.getTitle());
				bean.setApply_date(tableAchiTrans.getApply_date().toString().substring(0, 10));
				bean.setPrice(tableAchiTrans.getPrice());
				patentAchTransBeanList.add(bean);
			}
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, patentAchTransBeanList);
		return event;
	}
}
