package com.weiwei.svr.manage;

public interface IHealthyManager {
	public String insertNewHeathCheckBooking(String username) ;
	public void cancelHealthCheckBooking(String username);
	public boolean getHealthCheckIsApplied(String username);
}
