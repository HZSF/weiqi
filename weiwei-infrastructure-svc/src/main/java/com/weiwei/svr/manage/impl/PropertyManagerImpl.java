package com.weiwei.svr.manage.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IPropertyDAO;
import com.weiwei.svr.manage.IPropertyManager;

@Service
public class PropertyManagerImpl implements IPropertyManager{
	@Autowired
	private IPropertyDAO propertyDao;

	public void insertSellProperty(String username, String region, String category, int area,
			int levels, int ask_price, String description) {
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		propertyDao.insertSellProperty(username, region, category, area, levels, ask_price, description, date);
	}

	public List<?> getSellingPropertyListLimitedNumber(int n) {
		return propertyDao.getSellingPropertyListLimitedNumber(n);
	}

	public List<?> getSellingPropertyListLimitedNumberStartFromId(int startId, int n) {
		return propertyDao.getSellingPropertyListLimitedNumberStartFromId(startId, n);
	}
	
	public void insertLendProperty(String username, String region, String category, int area,
			int levels, int ask_price, String description) {
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		propertyDao.insertLendProperty(username, region, category, area, levels, ask_price, description, date);
	}
	
	public List<?> getLendingPropertyListLimitedNumber(int n) {
		return propertyDao.getLendingPropertyListLimitedNumber(n);
	}

	public List<?> getLendingPropertyListLimitedNumberStartFromId(int startId, int n) {
		return propertyDao.getLendingPropertyListLimitedNumberStartFromId(startId, n);
	}
	
	public int addToFavoritesSell(String username, int property_sell_id) {
		return propertyDao.addToFavoritesSell(username, property_sell_id);
	}
	
	public int cancelFavoritesSell(String username, int property_sell_id) {
		return propertyDao.cancelFavoritesSell(username, property_sell_id);
	}
	
	public List<?> getFavoriteSellListLimitedNumber(String username, int n) {
		return propertyDao.getFavoriteSellListLimitedNumber(username, n);
	}

	public List<?> getFavoriteSellListLimitedNumberStartFromId(String username, int startId, int n) {
		return propertyDao.getFavoriteSellListLimitedNumberStartFromId(username, startId, n);
	}
	
	public int addToFavoritesLend(String username, int property_lend_id) {
		return propertyDao.addToFavoritesLend(username, property_lend_id);
	}
	
	public int cancelFavoritesLend(String username, int property_lend_id) {
		return propertyDao.cancelFavoritesLend(username, property_lend_id);
	}
	
	public List<?> getFavoriteLendListLimitedNumber(String username, int n) {
		return propertyDao.getFavoriteLendListLimitedNumber(username, n);
	}

	public List<?> getFavoriteLendListLimitedNumberStartFromId(String username, int startId, int n) {
		return propertyDao.getFavoriteLendListLimitedNumberStartFromId(username, startId, n);
	}

}
