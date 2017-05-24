package com.weiwei.svr.manage;

import java.util.List;

public interface AnnounceManager {
	public List<?> findBySequenceId(int startId, int endId);
	public List<?> findByUrl(String url);
	public List<?> findByKeyword(String keyword);
}
