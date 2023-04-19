package com.svs.stayvision.service.board;

import java.util.List;

import com.svs.stayvision.util.PageNavigator;
import com.svs.stayvision.vo.Board;

public interface BoardService {
	public int boardWirte(Board board);
	public Board boardSelect(int boardNum);
	public int boardDelete(int boardNum);
	public int boardUpdate(Board board);
	public int addBoardViewCount(int boardNum);
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String category, String keyword, String boardType);
	public List<Board> boardSelectAll(PageNavigator navi);
}
