package com.svs.stayvision.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.svs.stayvision.service.Business.BusinessService;
import com.svs.stayvision.service.Member.MemberService;
import com.svs.stayvision.service.board.BoardService;
import com.svs.stayvision.vo.Board;
import com.svs.stayvision.vo.Business;
import com.svs.stayvision.vo.Member;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class MemberController {
	@Autowired
	private MemberService mService;
	@Autowired
	private BoardService bService;
	@Autowired
	private BusinessService buService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
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
	public String loginafter(Model model) {
		List<Board> notice = bService.boardPreview("notice");
		List<Board> complain = bService.boardPreview("complain");
		List<Board> emp = bService.boardPreview("emp");
		log.debug("notice{}",notice);
		log.debug("complain{}",complain);
		log.debug("emp{}",emp);
		model.addAttribute("notice",notice);
		model.addAttribute("complain",complain);
		model.addAttribute("emp",emp);
		return "loginafter";
	}
	
	@GetMapping("/adminapproval")
	public String adminapproval(Model model) {
		// 전체 목록
		List<Member> mList = mService.selectAllMember();
		model.addAttribute("mlist",mList);
				
		// 가입 대기 목록
		List<Member> sList = mService.selectStandbyMember();
		model.addAttribute("slist", sList);
				
		return "adminapproval";
	}
	
	@PostMapping(value= "/memberdelete", params = "delete")
	public String memberdelete(@RequestParam String[] checkedValue1) {
		for (String id : checkedValue1) {
			Member member = new Member();
			member.setId(id);
			// System.out.println(id);
			mService.adminrefuse(member);
		}
		
		return "redirect:/adminapproval";
	}
	
	@PostMapping(value= "/memberdelete", params = "disabled")
	public String memberdisabled(@RequestParam String[] checkedValue1) {
		for (String id : checkedValue1) {
			Member member = new Member();
			member.setId(id);
			// System.out.println(id);
			mService.memberdisabled(member);
		}
		
		return "redirect:/adminapproval";
	}
	
	@PostMapping(value = "/adminapproval", params = "approval")
	public String adminapproval(@RequestParam String[] checkedValue2) {
		for (String id : checkedValue2) {
			Member member = new Member();
			member.setId(id);
			// System.out.println(id);
			mService.adminapproval(member);
		}
		
		return "redirect:/adminapproval";	
	}
	
	@PostMapping(value = "/adminapproval", params = "refuse")
	public String adminrefuse(@RequestParam String[] checkedValue2) {
		for (String id : checkedValue2) {
			Member member = new Member();
			member.setId(id);
			// System.out.println(id);
			mService.adminrefuse(member);
		}
		
		return "redirect:/adminapproval";
	}
	
	//회원가입 유효성 검사
	@PostMapping("/checkId")
	@ResponseBody
	public String checkId(String id) {
		log.debug("checkId()");
		log.debug("id : {}",id);
		Member member = mService.findOneMember(id);
		log.debug("member : {}",member);
		if(member == null) {
			return "OK";
		}else {
			return "NG";
		}
	}
	
	@GetMapping("/memberupdate")
	public String memberupdate(@AuthenticationPrincipal UserDetails user,Model model) {
		
		String id = user.getUsername();
		log.debug("id : {}",id);
		Member member = mService.findOneMember(id);
		log.debug("Member : {}",member);
		model.addAttribute("member",member);
		List<Business> bList = buService.selectBusiness(id);
		log.debug("bList Size : {}", bList.size());
		model.addAttribute("blist",bList);
		
		return "memberupdate";
	}
	
	
	@PostMapping("/memberupdate")
	public String memberupdate(Member member) {
		log.debug("Member : {}",member);
		mService.memberupdate(member);
		return "redirect:/loginafter";
	}
	
	@GetMapping("/reauthenticate")
    public String reauthenticate() {
        return "reauthenticate"; // 새로운 인증 정보를 입력받을 폼을 보여줌
    }

	@PostMapping("/reauthenticate")
	public String reauthenticate(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("password") String password, RedirectAttributes attributes) {
		
		log.debug(password);
	    // 입력한 비밀번호가 일치하는지 확인
		log.debug("userDetails.getPassword() : {}", userDetails.getPassword());
		
		// String encodedPassword = passwordEncoder.encode(password);
		
	    boolean isPasswordCorrect = passwordEncoder.matches(password, userDetails.getPassword());
	    
	    if (!isPasswordCorrect) {
	        attributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
	        return "redirect:/reauthenticate";
	    }
	    
	    return "redirect:/memberupdate";
	}


}
