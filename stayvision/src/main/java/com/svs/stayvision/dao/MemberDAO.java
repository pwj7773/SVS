package com.svs.stayvision.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.svs.stayvision.vo.Member;


@Mapper
public interface MemberDAO {
	public int insertMember(Member member);

	public List<Member> selectAllMember();

	public List<Member> selectStandbyMember();

	public int adminapproval(Member member);

	public int adminrefuse(Member member);

	public Member findOneMember(String id);

	public int memberupdate(Member member);

	public int memberdisabled(Member member);

}
