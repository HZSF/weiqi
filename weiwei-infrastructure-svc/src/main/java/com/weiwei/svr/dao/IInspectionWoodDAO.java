package com.weiwei.svr.dao;

public interface IInspectionWoodDAO {
	public String insertNewWoodInspectionBooking(String username) ;
	public void cancelBookedWookInspection(String username);
}
