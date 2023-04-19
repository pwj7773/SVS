package com.svs.stayvision.service.Business;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Business> selectBusinessAll(String category,String keyword) {
		Map<String, Object> map = new HashMap<String, Object>();
		//맵에 넣는 key값은 마이바티스가 파라미터로 사요할 이름
		//value는 실제 값
		map.put("category", category);
		map.put("keyword", keyword);
		
		return bDao.selectBusinessAll(map);
	}

	@Override
	public int updateBusiness(Business business) {
		// TODO Auto-generated method stub
		return bDao.updateBusiness(business);
	}

}
