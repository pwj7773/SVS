package com.svs.stayvision.service.Business;


import java.util.List;

import com.svs.stayvision.vo.Business;

public interface BusinessService {
	public int insertBusiness(Business business);

	public Business findOneBusiness(int businessNum);

	public List<Business> selectBusiness(String id);

	public List<Business> selectBusinessAll(String id);

	public int updateBusiness(Business business);
}
