package com.svs.stayvision.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.stayvision.dao.BoardDAO;
import com.svs.stayvision.vo.Board;

@Service
public class BoardServiceImpl implements BoardService {
@Autowired
private BoardDAO bDao;
	@Override
	public int empBoardWirte(Board board) {
		return bDao.empBoardWirte(board);
	}
	@Override
	public Board boardSelectAll() {
		return bDao.boardSelectAll();
	}

}
