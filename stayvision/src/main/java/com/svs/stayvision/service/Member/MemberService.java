package com.svs.stayvision.service.Member;


import java.util.List;

import com.svs.stayvision.vo.Member;

public interface MemberService {
	public int insertMember(Member member);

	public List<Member> selectAllMember();
	
	public List<Member> selectStandbyMember();

	public int adminapproval(Member member);
}
