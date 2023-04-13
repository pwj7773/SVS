package com.svs.stayvision.service.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.stayvision.dao.BoardDAO;
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
	public List<Board> boardSelectAll(String category, String keyword) {
		Map<String,Object> map = new HashMap<>();
		map.put("category", category);
		map.put("keyword", keyword);
		
		return bDao.boardSelectAll(map);
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

}
