package com.svs.stayvision.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.stayvision.dao.BoardDAO;
import com.svs.stayvision.util.PageNavigator;
import com.svs.stayvision.vo.Board;

@Service
public class BoardServiceImpl implements BoardService {
@Autowired
private BoardDAO bDao;
	@Override
	public int boardWirte(Board board) {
		return bDao.boardWirte(board);
	}

	@Override
	public Board boardSelect(int boardNum) {
		return bDao.boardSelect(boardNum);
	}
	@Override
	public int boardDelete(int boardNum) {
		return bDao.boardDelete(boardNum);
	}
	@Override
	public int boardUpdate(Board board) {
		return bDao.boardUpdate(board);
	}
	@Override
	public int addBoardViewCount(int boardNum) {
		return bDao.addBoardViewCount(boardNum);
	}
	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String category, String keyword, String boardType) {
		Map<String,Object> map = new HashMap<>();
		map.put("category", category);
		map.put("keyword", keyword);
		map.put("boardType",boardType);
		int total = bDao.countBoard(map);
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, total, category, keyword, boardType);
		
		return navi;
	}
	@Override
	public List<Board> boardSelectAll(PageNavigator navi) {
		RowBounds rb = new RowBounds(navi.getStartRecord(), navi.getCountPerPage());
		Map<String,Object> map = new HashMap<>();
		map.put("category", navi.getCategory());
		map.put("keyword", navi.getKeyword());
		map.put("boardType", navi.getBoardType());
		
		return bDao.boardSelectAll(rb,map);
	}

}
