package com.svs.stayvision.service.reply;

import java.util.List;

import com.svs.stayvision.util.PageNavigator;
import com.svs.stayvision.vo.Reply;


public interface ReplyService {
	public int insertReply(Reply r);
	public List<Reply> getAllReply(int boardNum,PageNavigator navi);
	public Reply getOneReply(int replyNum);
	public int updateReply(Reply r);
	public int deleteReply(int replyNum);
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page);
}
