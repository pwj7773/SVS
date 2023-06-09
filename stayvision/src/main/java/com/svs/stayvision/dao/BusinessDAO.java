package com.svs.stayvision.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.svs.stayvision.vo.Business;


@Mapper
public interface BusinessDAO {
	public int insertBusiness(Business business);

	public Business findOneBusiness(int businessNum);

	public List<Business> selectBusiness(String id);

	public List<Business> selectBusinessAll(Map<String, Object> map);

	public int updateBusiness(Business business);
}
