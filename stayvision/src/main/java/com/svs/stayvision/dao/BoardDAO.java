package com.svs.stayvision.dao;

import org.apache.ibatis.annotations.Mapper;

import com.svs.stayvision.vo.Board;
@Mapper
public interface BoardDAO {

	public int empBoardWirte(Board board);

	public Board boardSelectAll();

}
