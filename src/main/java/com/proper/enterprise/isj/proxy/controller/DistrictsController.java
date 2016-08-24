package com.proper.enterprise.isj.proxy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.proxy.model.District;
import com.proper.enterprise.isj.proxy.service.IDistrictsService;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.JSONUtil;

/**
 * Created by think on 2016/8/16 0016. 院区列表
 */
@RestController
@RequestMapping(path = "/districts")
public class DistrictsController extends BaseController {

	@Autowired
	private IDistrictsService districtsService;

	/**
	 *
	 * @return
	 *
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<District>> getDistricts() {

		String fl = "[" + "  {" + "    'id': '1'," + "    'name': '南湖院区'" + "      }," + "  {"
				+ "    'id': '2'," + "    'name': '滑翔院区'" + "  }," + "  {" + "    'id': '3',"
				+ "    'name': '沈北院区'" + "  }" + "]";
		List<District> disList = new ArrayList<District>();
		try {
			disList = JSONUtil.parse(fl, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseOfGet(disList);
	}

}
