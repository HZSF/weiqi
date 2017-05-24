package com.weiwei.svr.dao;

public interface IHealthyDAO {
	public String insertNewHeathCheckBooking(String username) ;
	public void cancelHealthCheckBooking(String username) ;
	public boolean getHealthCheckIsApplied(String username);
}
