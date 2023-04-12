package com.svs.stayvision.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.svs.stayvision.vo.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@GetMapping("empBoardList")
	public String empBoardList(Model model) {
		log.debug("empBoardList() 실행");
		log.debug("Board: {}");
		return "emp/boardList";
	}
	
	@GetMapping("boardWrite")
	public String boardWrite() {
		return "emp/boardWrite";
	}
	
	@PostMapping("boardWrite")
	public String boardWrite(Board board,@AuthenticationPrincipal UserDetails user) {
		
		return "redirect:/empBoardList";
	}
}
