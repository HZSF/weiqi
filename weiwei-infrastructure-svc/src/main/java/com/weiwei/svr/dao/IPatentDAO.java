package com.weiwei.svr.dao;

import java.sql.Timestamp;
import java.util.List;

import com.weiwei.svr.dbmodel.TableAnnualFeeDetail;
import com.weiwei.svr.dbmodel.TableAnnualFeeMonitor;

public interface IPatentDAO {
	public List<?> findAnnualFeeMonitorByCustomerID(int id);
	public List<?> findAnnualFeeMonitor(int id, String patentId);
	public List<?> findAnnualFeeDetailByMonitorID(int id);
	public List<?> findAnnualFeeDetailByMonitorIDArray(Integer[] ids);
	public List<?> findAchievementTransformLimitedNumbers(int n);
	public List<?> findAchievementTransformByStartIdLimitedNumbers(int startId, int n);
	public void addAnnualFeeMonitor(TableAnnualFeeMonitor data);
	public void deleteAnnFeeMonitor(int custId, String patentId);
	public void addFeeDetail(TableAnnualFeeDetail data);
	public void addAchievementTransform(int custId, String patentId, String title, Timestamp applyDate, double price);
}
