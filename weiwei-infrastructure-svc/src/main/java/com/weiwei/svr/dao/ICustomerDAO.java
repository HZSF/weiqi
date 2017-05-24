package com.weiwei.svr.dao;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

public interface ICustomerDAO {
	public boolean authenticatePassword(String username, String password);
	public boolean existingCustomer(String username);
	public boolean existingMobilePhone(String phoneNumber);
	public void registerNewCustomer(String username, String password, String phoneNumber, String companyName, Timestamp date); 
	public List<?> findCustomerIdByUsername(String username);
	public List<?> findCustomerByUsername(String username);
	public void updateInfoByUsername(String username, String columnName, String value);
	public List<?> findCustomerByUserId(int id);
	public List<?> findProvinceList();
	public List<?> findCityListByProvinceId(int province_id);
	public void updateInfoAreaByUsername(String username, int province_id, int city_id);
	public void updatePortraitImg(String username, InputStream imageIs, int size);
	public byte[] getPortraitImg(String username);
}
