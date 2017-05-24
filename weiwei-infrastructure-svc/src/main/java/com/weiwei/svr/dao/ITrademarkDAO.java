package com.weiwei.svr.dao;

import java.sql.Timestamp;
import java.util.List;

public interface ITrademarkDAO {
	public void addTrademarkMonitor(int userId, String regNum, int categoryNum, String name);
	public List<?> findTrademarkMonitor(int userId, String regNum);
	public List<?> findTrademarkMonitorByUserId(int userId);
	public void cancelTrademarkMonitor(int userId, String regNum);
	public void addTrademarkSell(int userId, String regNum, int categoryNum, String name, int priceAsk, Timestamp date);
	public List<?> findTrademarkTrade(int userId, String regNum);
	public List<?> findTrademarkTradeLimitedNumbers(int n);
	public List<?> findTrademarkTradeByStartIdLimitedNumbers(int startId, int n);
}
