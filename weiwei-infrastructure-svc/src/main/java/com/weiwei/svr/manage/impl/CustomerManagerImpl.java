package com.weiwei.svr.manage.impl;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.ICustomerDAO;
import com.weiwei.svr.dbmodel.TableCustomer;
import com.weiwei.svr.dbmodel.TableCustomers;
import com.weiwei.svr.manage.ICustomerManager;

@Service
public class CustomerManagerImpl implements ICustomerManager{

	@Autowired
	private ICustomerDAO customerDAO; 
	
	public boolean authenticatePassword(String username, String password) {
		// TODO Auto-generated method stub
		return customerDAO.authenticatePassword(username, password);
	}

	public boolean existingCustomer(String username) {
		// TODO Auto-generated method stub
		return customerDAO.existingCustomer(username);
	}
	
	public boolean existingMobilePhone(String phoneNumber){
		return customerDAO.existingMobilePhone(phoneNumber);
	}

	public void registerNewCustomer(String username, String password, String phoneNumber, String companyName) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		customerDAO.registerNewCustomer(username, password, phoneNumber, companyName, date);
	}

	public List<?> findCustomerByUsername(String username) {
		// TODO Auto-generated method stub
		return customerDAO.findCustomerByUsername(username);
	}

	public void updateInfoByUsername(String username, String columnName, String value) {
		// TODO Auto-generated method stub
		TableCustomers customer = new TableCustomers();
		Class<?> tableClass = customer.getClass();
		boolean containField = false;
		try{
			tableClass.getDeclaredField(columnName);
			containField = true;
		}catch(NoSuchFieldException e){
			containField = false;
		}
		if(containField){
			customerDAO.updateInfoByUsername(username, columnName, value);
		}
	}

	public List<?> findProvinceList() {
		// TODO Auto-generated method stub
		return customerDAO.findProvinceList();
	}
	
	public List<?> findCityListByProvinceId(int province_id){
		return customerDAO.findCityListByProvinceId(province_id);
	}

	public void updateInfoAreaByUsername(String username, int province, int city) {
		customerDAO.updateInfoAreaByUsername(username, province, city);
	}

	public void updateInfoImgByUsername(String username, InputStream input, long size) {
		customerDAO.updatePortraitImg(username, input, (int)size);
	}
	
	public byte[] getPortraitImg(String username){
		return customerDAO.getPortraitImg(username);
	}

}
