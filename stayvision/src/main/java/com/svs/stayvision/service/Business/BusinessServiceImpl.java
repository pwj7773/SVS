package com.svs.stayvision.service.Business;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svs.stayvision.dao.BusinessDAO;
import com.svs.stayvision.vo.Business;

@Service
public class BusinessServiceImpl implements BusinessService {
	
	@Autowired
	private BusinessDAO bDao;

	@Override
	public int insertBusiness(Business business) {
		// TODO Auto-generated method stub
		return bDao.insertBusiness(business);
	}

	@Override
	public Business findOneBusiness(int businessNum) {
		// TODO Auto-generated method stub
		return bDao.findOneBusiness(businessNum);
	}

	@Override
	public List<Business> selectBusiness(String id) {
		// TODO Auto-generated method stub
		return bDao.selectBusiness(id);
	}

	@Override
	public List<Business> selectBusinessAll(String id) {
		// TODO Auto-generated method stub
		return bDao.selectBusinessAll(id);
	}

	@Override
	public int updateBusiness(Business business) {
		// TODO Auto-generated method stub
		return bDao.updateBusiness(business);
	}

}
