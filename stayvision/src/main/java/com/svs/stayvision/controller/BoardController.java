package com.svs.stayvision.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.svs.stayvision.service.board.BoardService;
import com.svs.stayvision.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired
	private BoardService bService;
	
	//임직원 목록보기
	@GetMapping("boardList")
	public String boardList(String category, String keyword, Model model) {
		log.debug("BoardList() 실행");
		List<Board> board = bService.boardSelectAll(category, keyword);
		log.debug("Board: {}",board);
		model.addAttribute("board",board);
		model.addAttribute("keyword",keyword);
		model.addAttribute("category",category);
		return "board/boardList";
	}
	
	//임직원게시판 글쓰기화면 보여주기
	@GetMapping("boardWrite")
	public String boardWrite() {
		return "board/boardWrite";
	}
	
	//임직원게시판 글쓰기
	@PostMapping("boardWrite")
	public String boardWrite(Board board,@AuthenticationPrincipal UserDetails user) {
		log.debug("boardWrite() 실행");
		board.setUserId(user.getUsername());
		log.debug("Board: {}",board);
		bService.boardWirte(board);
		return "redirect:/boardList";
	}
	
	//임직원게시판 글보기
	@GetMapping("boardRead")
	public String boardRead(int boardNum, Model model) {
		log.debug("boardRead() 실행");
		Board board = bService.boardSelect(boardNum);
		log.debug("Board: {}",board);
		model.addAttribute(board);
		return "board/boardRead";
	}
}
