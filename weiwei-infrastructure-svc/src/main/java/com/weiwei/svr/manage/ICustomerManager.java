package com.weiwei.svr.manage;

import java.io.InputStream;
import java.util.List;

public interface ICustomerManager {
	public boolean authenticatePassword(String username, String password);
	public boolean existingCustomer(String username);
	public boolean existingMobilePhone(String phoneNumber);
	public void registerNewCustomer(String username, String password, String phoneNumber, String companyName);
	public List<?> findCustomerByUsername(String username);
	public void updateInfoByUsername(String username, String columnName, String value);
	public List<?> findProvinceList();
	public List<?> findCityListByProvinceId(int province_id);
	public void updateInfoAreaByUsername(String username, int province, int city);
	public void updateInfoImgByUsername(String username, InputStream imageIs, long size);
	public byte[] getPortraitImg(String username);
}
