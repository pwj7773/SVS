package com.svs.stayvision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.svs.stayvision.service.Member.MemberService;
import com.svs.stayvision.vo.Member;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class MemberController {
	@Autowired
	private MemberService mService;
	
	@GetMapping("login")
	public String login() {
		log.debug("로그인 실행");
		return "login";
	}
	
	// 회원 가입하기(화면 띄우기)
	@GetMapping("/insert")
	public String insert() {
		log.debug("insert() 실행됨");
		return "insert";
	}
	
	// 회원 가입하기(회원 정보 저장 실행)
	@PostMapping("/insert")
	public String insert(Member member) {
		log.debug("insert(member) 실행됨");
		log.debug("member : {}", member);
		mService.insertMember(member);
		
		return "redirect:/";
	}
	
	// 로그인 후 화면 이동
	@GetMapping("/loginafter")
	public String loginafter() {
		return "loginafter";
	}
	
	@GetMapping("/adminapproval")
	public String adminapproval() {
		return "adminapproval";
	}
}
