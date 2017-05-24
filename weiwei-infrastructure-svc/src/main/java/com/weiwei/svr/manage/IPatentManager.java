package com.weiwei.svr.manage;

import java.util.List;

public interface IPatentManager {
	public List<?> findAnnualFeeMonitorByUsername(String username);
	public boolean containAnnualFeeDetailRecordByMonitorID(int id);
	public boolean[] containAnnualFeeDetailRecordByMonitorIDArray(Integer[] ids);
	public List<?> findAchievementTransformLimitedNumbers(int n);
	public List<?> findAchievementTransformByStartIdLimitedNumbers(int startId, int n);
	public String addAnnualFeeMonitorByUsername(String username, String patent_id, String title, String apply_date, String applicant);
	public void deleteAnnFeeMonitor(String username, String patentId);
	public void addDelegateMonitor(String username, String patentId);
	public String addAchieveTransForm(String username, String patent_id, String title, String price);
}
