package com.weiwei.svr.manage;

public interface IInspectionKidsManager {
	public String insertNewKidsInspectionBooking(String username) ;
	public void cancelBookedKidsInspection(String username);
}
