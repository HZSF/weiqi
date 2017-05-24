package com.weiwei.svr.manage.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.ICustomerDAO;
import com.weiwei.svr.dao.ITrademarkDAO;
import com.weiwei.svr.dbmodel.TableCustomer;
import com.weiwei.svr.dbmodel.TableCustomers;
import com.weiwei.svr.dbmodel.TableTrademarkMonitor;
import com.weiwei.svr.manage.ITrademarkManager;

@Service
public class TrademarkManagerImpl implements ITrademarkManager {

	@Autowired
	private ITrademarkDAO trademarkDAO;
	@Autowired
	private ICustomerDAO customerDAO;
	
	public String addTrademarkMonitor(String username, String regNum, int categoryNum, String name) {
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username); 
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			List<?> tableList = trademarkDAO.findTrademarkMonitor(customerID, regNum);
			if(tableList != null && tableList.size() > 0){
				return "existed";
			}else{
				trademarkDAO.addTrademarkMonitor(customerID, regNum, categoryNum, name);
				return "success";
			}
		}
		return null;
	}

	public List<?> findTrademarkMonitorByUserName(String username) {
		// TODO Auto-generated method stub
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username); 
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			return trademarkDAO.findTrademarkMonitorByUserId(customerID);
		}
		return null;
	}

	public void cancelTrademarkMonitor(String username, String regNum) {
		// TODO Auto-generated method stub
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username); 
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			trademarkDAO.cancelTrademarkMonitor(customerID, regNum);
		}
	}

	public String addTrademarkTrade(String username, String regNum, int categoryNum, String name, int priceAsk) {
		// TODO Auto-generated method stub
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username); 
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			List<?> tableList = trademarkDAO.findTrademarkTrade(customerID, regNum);
			if(tableList != null && tableList.size() > 0){
				return "existed";
			}else{
				Calendar calendar = Calendar.getInstance();
				Timestamp date = new Timestamp(calendar.getTimeInMillis());
				trademarkDAO.addTrademarkSell(customerID, regNum, categoryNum, name, priceAsk, date);
				return "success";
			}
		}
		return null;
	}

	public List<?> findTrademarkTradeLimitedNumbers(int n) {
		// TODO Auto-generated method stub
		return trademarkDAO.findTrademarkTradeLimitedNumbers(n);
	}

	public List<?> findTrademarkTradeByStartIdLimitedNumbers(int startId, int n) {
		// TODO Auto-generated method stub
		return trademarkDAO.findTrademarkTradeByStartIdLimitedNumbers(startId, n);
	}

}
