package com.svs.stayvision.service.Member;


import java.util.List;

import com.svs.stayvision.vo.Member;

public interface MemberService {
	public int insertMember(Member member);

	public List<Member> selectAllMember();
	
	public List<Member> selectStandbyMember();

	public int adminapproval(Member member);

	public int adminrefuse(Member member);

	public Member findOneMember(String id);

	public int memberupdate(Member member);

	public int memberdisabled(Member member);

	public int updateMember(Member member);

	public int checkMember(String id, String pw);
}
