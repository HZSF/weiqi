package com.weiwei.centreservice.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.beans.AnnounceCommentBean;
import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.CommentAnnounceRequest;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.CommentAnnounceTableBean;
import com.weiwei.svr.dbmodel.TableCommentAnnounce;
import com.weiwei.svr.manage.ICommentManager;

//removable

public class AnnounceCommentProcessor extends BaseProcessor{
	
	protected ApplicationContext ctx;
	private CommentAnnounceRequest request;
	
	private List<AnnounceCommentBean> result_list;
	private boolean moreComment;
	
	@Override
	protected void preProcess(Map scopes){
		request = (CommentAnnounceRequest)scopes.get(Constants.COMMENT_ANNOUNCE_ADD_REQUEST);
		if(request!= null && request.getStartId() > 0){
			moreComment = true;
		} else {
			moreComment = false;
		}
	}
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Announcement.xml");
		ICommentManager commentManager = (ICommentManager)ctx.getBean("commentManagerImpl");
		ArrayList<CommentAnnounceTableBean> tableBeanList = null;
		result_list = new ArrayList<AnnounceCommentBean>();
		int announceId = 0;
		if(!StringUtility.isEmptyString(request.getAnnounceId())){
			announceId = Integer.valueOf(request.getAnnounceId());
		}
		if(moreComment){
			tableBeanList = (ArrayList<CommentAnnounceTableBean>)commentManager.getCommentLimitedNumbersFromStartIdJoin(announceId, request.getStartId(), request.getNumbers());
		}else{
			tableBeanList = (ArrayList<CommentAnnounceTableBean>)commentManager.getCommentLimitedNumbersJoin(announceId);
		}
		if (tableBeanList != null && tableBeanList.size() > 0){
			for(CommentAnnounceTableBean table : tableBeanList){
				AnnounceCommentBean bean = new AnnounceCommentBean();
				bean.setId(table.getId());
				bean.setAnnounce_id(table.getAnnounce_id());
				bean.setComment(table.getComment());
				bean.setComment_time(table.getComment_time().toString());
				bean.setCustomer_name(table.getUserName());
				bean.setNumberOfLike(table.getCountLike());
				if(!StringUtility.isEmptyString(table.getParent_comment_id()) && !"null".equalsIgnoreCase(table.getParent_comment_id()))
					bean.setParent_comment_id(Integer.valueOf(table.getParent_comment_id()));
				bean.setParent_comment(table.getParentComment());
				bean.setParent_comment_customer(table.getParentCommentUserName());
				
				result_list.add(bean);
			}
		}
		scopes.put(Constants.SERVICE_RESPONSE, result_list);
		
		return Constants.EVENT_SUCCESS;
	}

}
