package com.weiwei.centreservice.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.beans.CommentBean;
import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.CommentFetchRequest;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.CommentTableJoinBean;
import com.weiwei.svr.manage.ICommentManager;

public class CommentFetchProcessor extends BaseProcessor{
	protected ApplicationContext ctx;
	private CommentFetchRequest request;
	
	private List<CommentBean> result_list;
	private boolean moreComment;
	
	@Override
	protected void preProcess(Map scopes){
		request = (CommentFetchRequest)scopes.get(Constants.COMMENT_ANNOUNCE_ADD_REQUEST);
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
		ArrayList<CommentTableJoinBean> tableBeanList = null;
		result_list = new ArrayList<CommentBean>();
		int announceId = 0;
		if(!StringUtility.isEmptyString(request.getAnnounceId())){
			announceId = Integer.valueOf(request.getAnnounceId());
		}
		if(moreComment){
			tableBeanList = (ArrayList<CommentTableJoinBean>)commentManager.getCommentLimitedNumbersFromStartId(announceId, request.getStartId(), request.getNumbers());
		}else{
			tableBeanList = (ArrayList<CommentTableJoinBean>)commentManager.getCommentLimitedNumbers(announceId);
		}
		if (tableBeanList != null && tableBeanList.size() > 0){
			for(CommentTableJoinBean table : tableBeanList){
				CommentBean bean = new CommentBean();
				bean.setSession_id(table.getSession_id());
				bean.setId(table.getId());
				bean.setAnnounce_id(table.getAnnounce_id());
				bean.setComment(table.getComment_content());
				bean.setComment_time(table.getComment_time().toString());
				bean.setCustomer_name(table.getUserName());
				bean.setNumberOfLike(table.getCountLike());
				bean.setNumberOfComment(table.getCountComments());
				
				if(table.getCountComments() > 1){
					String commentConcat = table.getCommentGroup();
					String[] commentArray = commentConcat.split(Constants.SEPARATOR);
					bean.setCommentList(commentArray);
					String usernameConcat = table.getUsernameGroup();
					String[] usernameArray = usernameConcat.split(Constants.SEPARATOR);
					bean.setCustomernameList(usernameArray);
				}
				
				result_list.add(bean);
			}
		}
		scopes.put(Constants.SERVICE_RESPONSE, result_list);
		
		return Constants.EVENT_SUCCESS;
	}
}
