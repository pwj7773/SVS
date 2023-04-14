package com.svs.stayvision.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.svs.stayvision.PageNavigator;
import com.svs.stayvision.service.board.BoardService;
import com.svs.stayvision.vo.Board;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
public class BoardController {
	//게시판 목록의 페이지당 글 수
	//annotation을 가져오는 곳이 lombok이 아닌것에 주! 의!
	@Value("${user.board.page}")
	int countPerPage;

	//게시판 목록의 페이지 이동 링크 수
	@Value("${user.board.group}")
	int pagePerGroup;
	
	@Autowired
	private BoardService bService;
	
	//글 목록보기
	@GetMapping("boardList")
	public String boardList(String category, String keyword, Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		log.debug("BoardList() 실행");
		log.debug("page : {}", page);
		PageNavigator navi = bService.getPageNavigator(pagePerGroup, countPerPage, page, category, keyword);
		log.debug(navi.toString());
		List<Board> board = bService.boardSelectAll(navi);
		log.debug("Board: {}",board);
		model.addAttribute("board",board);
		model.addAttribute("navi", navi);
		model.addAttribute("keyword",keyword);
		model.addAttribute("category",category);
		return "board/boardList";
	}
	
	//글쓰기화면 보여주기
	@GetMapping("boardWrite")
	public String boardWrite() {
		return "board/boardWrite";
	}
	
	//글쓰기
	@PostMapping("boardWrite")
	public String boardWrite(Board board,@AuthenticationPrincipal UserDetails user) {
		log.debug("boardWrite() 실행");
		board.setUserId(user.getUsername());
		log.debug("Board: {}",board);
		bService.boardWirte(board);
		return "redirect:/boardList";
	}
	
	//글보기
	@GetMapping("boardRead")
	public String boardRead(int boardNum, Model model) {
		log.debug("boardRead() 실행");
		bService.addBoardViewCount(boardNum);
		Board board = bService.boardSelect(boardNum);
		log.debug("Board: {}",board);
		model.addAttribute(board);
		return "board/boardRead";
	}
	//글 삭제
	@GetMapping("boardDelete")
	public String boardDelete(int boardNum) {
		log.debug("boardDelete() 실행");
		bService.boardDelete(boardNum);
		return "redirect:/boardList";
	}
	// 수정화면 보여주기
	@GetMapping("boardUpdate")
	public String boardUpdate(int boardNum, Model model) {
		log.debug("boardUpdate() 실행");
		Board board = bService.boardSelect(boardNum);
		log.debug("Board: {}",board);
		model.addAttribute(board);
		return "board/boardUpdate";
	}
	
	@PostMapping("boardUpdate")
	public String boardUpdate(Board board) {
		log.debug("boardUpdate() 실행");
		log.debug("Board : {}", board);
		bService.boardUpdate(board);
		return "redirect:/boardList";
		
	}
}
