package com.weiwei.svr.manage;

import java.util.List;

public interface ITrademarkManager {
	public String addTrademarkMonitor(String username, String regNum, int categoryNum, String name);
	public List<?> findTrademarkMonitorByUserName(String username);
	public void cancelTrademarkMonitor(String username, String regNum);
	public String addTrademarkTrade(String username, String regNum, int categoryNum, String name, int priceAsk);
	public List<?> findTrademarkTradeLimitedNumbers(int n);
	public List<?> findTrademarkTradeByStartIdLimitedNumbers(int startId, int n);
}
