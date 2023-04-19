package com.svs.stayvision.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.svs.stayvision.vo.Reply;


@Mapper
public interface ReplyDAO {

	public int insertReply(Reply r);
	public Reply findReplyByReplyNum(int replyNum);
	public int updateReply(Reply r);
	public int deleteReply(int replyNum);
	public int countReply();
	public List<Reply> findReplyByBoardNum(int boardNum, RowBounds rb);
	
}
