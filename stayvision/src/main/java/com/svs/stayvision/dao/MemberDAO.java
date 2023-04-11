package com.svs.stayvision.dao;


import org.apache.ibatis.annotations.Mapper;

import com.svs.stayvision.vo.Member;


@Mapper
public interface MemberDAO {
	public int insertMember(Member member);
}
