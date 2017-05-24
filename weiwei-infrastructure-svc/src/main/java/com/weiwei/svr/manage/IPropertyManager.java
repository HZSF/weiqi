package com.weiwei.svr.manage;

import java.util.List;

public interface IPropertyManager {
	public void insertSellProperty(String username, String region, String category, int area, int levels, int ask_price, String description);
	public List<?> getSellingPropertyListLimitedNumber(int n);
	public List<?> getSellingPropertyListLimitedNumberStartFromId(int startId, int n);
	public void insertLendProperty(String username, String region, String category, int area, int levels, int ask_price, String description);
	public List<?> getLendingPropertyListLimitedNumber(int n);
	public List<?> getLendingPropertyListLimitedNumberStartFromId(int startId, int n);
	public int addToFavoritesSell(String username, int property_sell_id);
	public int cancelFavoritesSell(String username, int property_sell_id);
	public List<?> getFavoriteSellListLimitedNumber(String username, int n);
	public List<?> getFavoriteSellListLimitedNumberStartFromId(String username, int startId, int n);
	public int addToFavoritesLend(String username, int property_lend_id);
	public int cancelFavoritesLend(String username, int property_lend_id);
	public List<?> getFavoriteLendListLimitedNumber(String username, int n);
	public List<?> getFavoriteLendListLimitedNumberStartFromId(String username, int startId, int n);
}
