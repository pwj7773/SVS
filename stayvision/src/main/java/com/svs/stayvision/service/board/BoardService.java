package com.svs.stayvision.service.board;

import java.util.List;

import com.svs.stayvision.vo.Board;

public interface BoardService {
	public int boardWirte(Board board);
	public List<Board> boardSelectAll(String category, String keyword);
	public Board boardSelect(int boardNum);
}
