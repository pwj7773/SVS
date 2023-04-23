package com.svs.stayvision.service.Member;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.svs.stayvision.dao.MemberDAO;
import com.svs.stayvision.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberDAO mDao;

	@Override
	public int insertMember(Member member) {
		//비번 암호화
		String encodedPassword = passwordEncoder.encode(member.getPw());
		
		//비번 새로 설정
		member.setPw(encodedPassword);
		
		return mDao.insertMember(member);
	}
	
	@Override
	public List<Member> selectAllMember() {
		
		return mDao.selectAllMember();
	}

	@Override
	public List<Member> selectStandbyMember() {

		return mDao.selectStandbyMember();
	}

	@Override
	public int adminapproval(Member member) {
		// TODO Auto-generated method stub
		return mDao.adminapproval(member);
	}

	@Override
	public int adminrefuse(Member member) {
		// TODO Auto-generated method stub
		return mDao.adminrefuse(member);
	}

	@Override
	public Member findOneMember(String id) {
		
		return mDao.findOneMember(id);
	}

	@Override
	public int memberupdate(Member member) {
		
		//비번 암호화
		String encodedPassword = passwordEncoder.encode(member.getPw());
				
		//비번 새로 설정
		member.setPw(encodedPassword);
		
		return mDao.memberupdate(member);
	}

	@Override
	public int memberdisabled(Member member) {
		
		return mDao.memberdisabled(member);
	}

	@Override
	public int updateMember(Member member) {

		return mDao.updateMember(member);
	}

	@Override
	public int checkMember(String id, String pw) {
		//비번 암호화
		boolean pwMatch = passwordEncoder.matches(pw, mDao.findOneMember(id).getPassword());
		
		if(pwMatch) {
			return 1;
		}else {
			return 0;
		}
	}

	
}
