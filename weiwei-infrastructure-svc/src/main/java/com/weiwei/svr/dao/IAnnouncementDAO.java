package com.weiwei.svr.dao;

import java.util.List;

import com.weiwei.svr.dbmodel.Announce;

public interface IAnnouncementDAO {
	public List<Announce> findBySequenceId(int startId, int endId);
	public List<?> findByUrl(String url);
	public List<Announce> findeByKeywords(String key);
}
