package com.weiwei.intellectual.secure.processor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.intellectual.beans.PatentAnnualFeeMonitorBean;
import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableAnnualFeeMonitor;
import com.weiwei.svr.manage.IPatentManager;

public class SecRetrievePatentAnnualFeeMonitorProcessor extends BaseProcessor{

	private List<PatentAnnualFeeMonitorBean> patentMonitorBeanList;
	private String username;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		patentMonitorBeanList = new ArrayList<PatentAnnualFeeMonitorBean>();
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:intellectual/Patent.xml");
		IPatentManager patentManager = (IPatentManager) ctx.getBean("patentManagerImpl");
		List<TableAnnualFeeMonitor> tableAnnualFeeMonitorList = (List<TableAnnualFeeMonitor>)patentManager.findAnnualFeeMonitorByUsername(username);
		if(tableAnnualFeeMonitorList != null && tableAnnualFeeMonitorList.size() > 0){
			int length = tableAnnualFeeMonitorList.size();
			Integer[] annualFeeMonitorIDArray = new Integer[length];
			int counter = 0;
			for(TableAnnualFeeMonitor tableMonitor : tableAnnualFeeMonitorList){
				PatentAnnualFeeMonitorBean bean = new PatentAnnualFeeMonitorBean();
				bean.setPatent_id(tableMonitor.getPatent_id());
				bean.setTitle(tableMonitor.getTitle());
				bean.setApplicant(tableMonitor.getApplicant());
				Timestamp applyDate = tableMonitor.getApply_date();
				bean.setApply_date(applyDate.toString().substring(0, 10));
				Calendar calApplyDate = Calendar.getInstance();
				calApplyDate.setTime(applyDate);
				Calendar calExpiredDate = Calendar.getInstance();
				calExpiredDate.set(Calendar.getInstance().get(Calendar.YEAR), calApplyDate.get(Calendar.MONTH), calApplyDate.get(Calendar.DATE));
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				bean.setExpire_date(format1.format(calExpiredDate.getTime()));
				Calendar calendar = Calendar.getInstance();
				if(calExpiredDate.compareTo(calendar) > 0){
					calendar.add(Calendar.DATE, 14);
					if(calExpiredDate.compareTo(calendar) > 0){
						calendar.add(Calendar.DATE, -14);
						calendar.add(Calendar.MONTH, 2);
						if(calExpiredDate.compareTo(calendar) > 0){
							bean.setExpire_status("3");//expired 2 months later
						}else{
							bean.setExpire_status("2");//expired 2 weeks - 2 months
						}
					}else{
						bean.setExpire_status("1");//expired within 2 weeks
					}
				}else{
					bean.setExpire_status("0");//expiry passed
				}
				bean.setAnnual_fee(tableMonitor.getFee2());
				annualFeeMonitorIDArray[counter++] = tableMonitor.getId();
				patentMonitorBeanList.add(bean);
			}
			boolean[] contains = patentManager.containAnnualFeeDetailRecordByMonitorIDArray(annualFeeMonitorIDArray);
			if(contains != null && contains.length == length){
				for(int i=0; i<length; i++){
					if(contains[i]){
						patentMonitorBeanList.get(i).setPayment_status("1");
					}else{
						patentMonitorBeanList.get(i).setPayment_status("0");
					}
				}
			}else{
				for(int i=0; i<length; i++){
					patentMonitorBeanList.get(i).setPayment_status("0");
				}
			}
			
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, patentMonitorBeanList);
		return event;
	}

}
