package com.svs.stayvision.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.svs.stayvision.service.Business.BusinessService;
import com.svs.stayvision.vo.Business;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class BusinessController {
	@Autowired
	private BusinessService bService;
	
	// 숙박업소 정보 추가
	@GetMapping("/insertBusiness")
	public String insertBusiness() {
		log.debug("insertBusiness() 실행됨");
		return "insertBusiness";
	}
	
	@PostMapping("/insertBusiness")
	public String insertBusiness(Business business, @AuthenticationPrincipal UserDetails user) {
		log.debug("insertBusiness() 실행됨");
		
		// 시큐리티에서 사용자 id를 가져옴
		business.setUserId(user.getUsername());
		bService.insertBusiness(business);
		
		return "redirect:/loginafter";
	}
}
