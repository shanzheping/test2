package com.proper.enterprise.isj.proxy.service.impl;

import com.proper.enterprise.isj.proxy.model.District;
import com.proper.enterprise.isj.proxy.service.IDistrictsService;
import com.proper.enterprise.isj.webservices.WebServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2016/8/18 0018.
 *
 */
@Service
@Transactional
public class DistrictsService implements IDistrictsService {

	@Autowired
	private WebServicesClient webServicesClient;

	@Override
	public List<District> getDistrict() {
		List<District> districtList = new ArrayList<>();
        District dis = new District();
		districtList.add(dis);
		return districtList;
	}
}
