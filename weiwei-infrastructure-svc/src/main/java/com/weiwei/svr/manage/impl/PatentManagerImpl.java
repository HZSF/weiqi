package com.weiwei.svr.manage.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.parent.common.base.Constants;
import com.weiwei.svr.dao.ICustomerDAO;
import com.weiwei.svr.dao.IPatentDAO;
import com.weiwei.svr.dbmodel.TableAnnualFeeDetail;
import com.weiwei.svr.dbmodel.TableAnnualFeeMonitor;
import com.weiwei.svr.dbmodel.TableCustomer;
import com.weiwei.svr.dbmodel.TableCustomers;
import com.weiwei.svr.manage.IPatentManager;

@Service
public class PatentManagerImpl implements IPatentManager{
	
	@Autowired
	private IPatentDAO patentDAO;
	
	@Autowired
	private ICustomerDAO customerDAO;

	public List<?> findAnnualFeeMonitorByUsername(String username) {
		// TODO Auto-generated method stub
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username); 
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			return patentDAO.findAnnualFeeMonitorByCustomerID(customerID);
		}
		return null;
	}

	public List<?> findAchievementTransformLimitedNumbers(int n) {
		return patentDAO.findAchievementTransformLimitedNumbers(n);
	}

	public List<?> findAchievementTransformByStartIdLimitedNumbers(int startId, int n) {
		return patentDAO.findAchievementTransformByStartIdLimitedNumbers(startId, n);
	}

	public boolean containAnnualFeeDetailRecordByMonitorID(int id) {
		List<?> result = patentDAO.findAnnualFeeDetailByMonitorID(id);
		if(result != null && result.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean[] containAnnualFeeDetailRecordByMonitorIDArray(Integer[] ids){
		List<TableAnnualFeeDetail> result = (List<TableAnnualFeeDetail>)patentDAO.findAnnualFeeDetailByMonitorIDArray(ids);
		if(result != null && result.size() > 0){
			ArrayList<Integer> availableIDs = new ArrayList<Integer>();
			for(TableAnnualFeeDetail tableAFD : result){
				availableIDs.add(tableAFD.getAnnual_fee_monitor_id());
			}
			boolean[] contains = new boolean[ids.length];
			for(int i=0; i<ids.length; i++){
				if(availableIDs.contains(ids[i])){
					contains[i] = true;
				}else{
					contains[i] = false;
				}
			}
			return contains;
		}else{
			return null;
		}
	}

	public String addAnnualFeeMonitorByUsername(String username, String patent_id, String title, String apply_date, String applicant) {
		// TODO Auto-generated method stub
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username); 
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			return String.valueOf(customerID);
			/*
			TableAnnualFeeMonitor tableData = new TableAnnualFeeMonitor();
			tableData.setCustomer_id(customerID);
			tableData.setPatent_id(patent_id);
			tableData.setTitle(title);
			Calendar calendar = Calendar.getInstance();
			Timestamp add_date = new Timestamp(calendar.getTimeInMillis());
			tableData.setAdd_date(add_date);
			tableData.setApply_date(Timestamp.valueOf(apply_date+" 00:00:00"));
			tableData.setApplicant(applicant);
			patentDAO.addAnnualFeeMonitor(tableData);
			*/
		}
		return null;
	}

	public void deleteAnnFeeMonitor(String username, String patentId) {
		// TODO Auto-generated method stub
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username); 
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			patentDAO.deleteAnnFeeMonitor(customerID, patentId);
		}
	}

	public void addDelegateMonitor(String username, String patentId) {
		// TODO Auto-generated method stub
		List<TableAnnualFeeMonitor> tableAnnualFeeMonitorList = (List<TableAnnualFeeMonitor>)findAnnualFeeMonitorByUsername(username);
		if(tableAnnualFeeMonitorList != null && tableAnnualFeeMonitorList.size() > 0){
			TableAnnualFeeMonitor afmTable = tableAnnualFeeMonitorList.get(0);
			int monitorId = afmTable.getId();
			TableAnnualFeeDetail tableDetail = new TableAnnualFeeDetail();
			tableDetail.setAnnual_fee_monitor_id(monitorId);
			Calendar calendar = Calendar.getInstance();
			Timestamp apply_date = new Timestamp(calendar.getTimeInMillis());
			tableDetail.setCustomer_apply_date(apply_date);
			tableDetail.setCustomer_apply_year(calendar.get(Calendar.YEAR));
			String yearMonth = String.valueOf(calendar.get(Calendar.YEAR)) + String.valueOf(calendar.get(Calendar.MONTH));
			tableDetail.setCustomer_apply_year_month(Integer.valueOf(yearMonth));
			patentDAO.addFeeDetail(tableDetail);
		}
	}

	public String addAchieveTransForm(String username, String patent_id, String title, String price) {
		// TODO Auto-generated method stub
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username); 
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			List<?> existingResult = patentDAO.findAnnualFeeMonitor(customerID, patent_id);
			if(existingResult != null && existingResult.size() > 0){
				return Constants.EVENT_EXISTED;
			}
			Calendar calendar = Calendar.getInstance();
			Timestamp applyDate = new Timestamp(calendar.getTimeInMillis());
			patentDAO.addAchievementTransform(customerID, patent_id, title, applyDate, Double.valueOf(price));
			return Constants.EVENT_SUCCESS;
		}
		return null;
	}
}
