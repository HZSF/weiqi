package com.weiwei.svr.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IAnnouncementDAO;
import com.weiwei.svr.manage.AnnounceManager;

@Service
public class AnnounceManagerImpl implements AnnounceManager {

	@Autowired
	private IAnnouncementDAO annDao;
	
	public List<?> findBySequenceId(int startId, int endId) {
		// TODO Auto-generated method stub
		return annDao.findBySequenceId(startId, endId);
	}

	public List<?> findByUrl(String url) {
		// TODO Auto-generated method stub
		return annDao.findByUrl(url);
	}

	public List<?> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return annDao.findeByKeywords(keyword);
	}

}
