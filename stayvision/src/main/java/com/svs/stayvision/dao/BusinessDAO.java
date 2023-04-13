package com.svs.stayvision.dao;


import org.apache.ibatis.annotations.Mapper;

import com.svs.stayvision.vo.Business;


@Mapper
public interface BusinessDAO {
	public int insertBusiness(Business business);
}
