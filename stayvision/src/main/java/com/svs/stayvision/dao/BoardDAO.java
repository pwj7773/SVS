package com.svs.stayvision.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.svs.stayvision.vo.Board;
@Mapper
public interface BoardDAO {

	public int boardWirte(Board board);

	public List<Board> boardSelectAll(RowBounds rb, Map<String, Object> map);

	public Board boardSelect(int boardNum);

	public int boardDelete(int boardNum);

	public int boardUpdate(Board board);

	public int addBoardViewCount(int boardNum);

	public int countBoard(Map<String, Object> map);

	public List<Board> boardPreview(RowBounds rb, String boardType);

}
