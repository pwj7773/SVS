package com.svs.stayvision.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.svs.stayvision.service.Business.BusinessService;
import com.svs.stayvision.service.Member.MemberService;
import com.svs.stayvision.vo.Business;
import com.svs.stayvision.vo.Member;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class BusinessController {
	@Autowired
	private BusinessService bService;
	@Autowired
	private MemberService mService;
	
	// 숙박업소 정보 추가 페이지 이동
	@GetMapping("/insertBusiness")
	public String insertBusiness() {
		log.debug("insertBusiness() 실행됨");
		return "business/insertBusiness";
	}
	
	// 숙박업소 정보 추가
	@PostMapping("/insertBusiness")
	public String insertBusiness(Business business, @AuthenticationPrincipal UserDetails user) {
		log.debug("insertBusiness() 실행됨");
		
		// 시큐리티에서 사용자 id를 가져옴
		business.setUserId(user.getUsername());
		bService.insertBusiness(business);
		
		return "redirect:/loginafter";
	}
	
	// ROLE_USER의 내 숙박업소 정보 조회
	@GetMapping("/infoBusiness")
	public String infoBusiness(@AuthenticationPrincipal UserDetails user,Model model) {
		
		String id = user.getUsername();
		log.debug("id : {}",id);
		List<Business> bList = bService.selectBusiness(id);
		log.debug("bList Size : {}", bList.size());
		model.addAttribute("blist",bList);
		log.debug("blist : {}",bList);
		
		return "business/infoBusiness";
	}
	
	// ROLE_ADMIN, ROLE_EMP의 전체 숙박업소 정보 조회
	@GetMapping("/infoBusiness2")
	public String infoBusiness2(@AuthenticationPrincipal UserDetails user,Model model) {
		
		String id = user.getUsername();
		log.debug("id : {}",id);
		List<Business> bList = bService.selectBusinessAll(id);
		log.debug("bList Size : {}", bList.size());
		model.addAttribute("blist",bList);
		//log.debug("blist : {}",bList);
		
		return "business/infoBusiness";
	}
	
	// 숙박업소 정보 수정 페이지 이동
	@GetMapping("/updateBusiness/{businessNum}")
	public String updateBusiness(@PathVariable Integer businessNum, Model model) {
		log.debug("businessNum : {}",businessNum);
		Business business = bService.findOneBusiness(businessNum);
	    log.debug("business : {}",business);
	    model.addAttribute("business", business);
		
		return "business/updateBusiness";
	}
	
	// 숙박업소 정보 수정
	@PostMapping("/updateBusiness")
	public String updateBusiness(Business business) {
		
		String id = business.getUserId();
		log.debug("id2 : {}",id);
		Member member = mService.findOneMember(id);
		log.debug("Member : {}",member);
		member.setName(business.getName());
		// member.setPhone(business.getPhone());
		log.debug("Member2 : {}",member);
		
		log.debug("businessUPDATE : {}",business);
		
		bService.updateBusiness(business);
		mService.updateMember(member);
		
		return "redirect:/loginafter";
	}
	
}
