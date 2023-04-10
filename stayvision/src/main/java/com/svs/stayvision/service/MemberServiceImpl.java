package com.svs.stayvision.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.svs.stayvision.dao.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberDAO mDao;

}
