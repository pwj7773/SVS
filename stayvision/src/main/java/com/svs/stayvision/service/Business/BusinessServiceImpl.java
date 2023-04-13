package com.svs.stayvision.service.Business;



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

}
