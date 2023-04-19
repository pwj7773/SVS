package com.svs.stayvision.service.reply;




import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.stayvision.dao.ReplyDAO;
import com.svs.stayvision.util.PageNavigator;
import com.svs.stayvision.vo.Reply;



@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO rDao;
	@Override
	public int insertReply(Reply r) {
		return rDao.insertReply(r);
	}

	@Override
	public List<Reply> getAllReply(int boardNum,PageNavigator navi) {
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());
		return rDao.findReplyByBoardNum(boardNum,rb);
	}

	@Override
	public Reply getOneReply(int replyNum) {
		return rDao.findReplyByReplyNum(replyNum);
	}

	@Override
	public int updateReply(Reply r) {
		return rDao.updateReply(r);
	}

	@Override
	public int deleteReply(int replyNum) {
		return rDao.deleteReply(replyNum);
	}

	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, int boardNum) {
		int total = rDao.countReply(boardNum);
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total, null, null, null);
		return navi;
	}

}
