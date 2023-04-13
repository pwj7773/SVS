package com.svs.stayvision.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.svs.stayvision.vo.Board;
@Mapper
public interface BoardDAO {

	public int boardWirte(Board board);

	public List<Board> boardSelectAll(Map<String, Object> map);

	public Board boardSelect(int boardNum);

}
