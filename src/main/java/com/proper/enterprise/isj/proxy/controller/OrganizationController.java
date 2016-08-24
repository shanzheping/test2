package com.proper.enterprise.isj.proxy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.proxy.model.District;
import com.proper.enterprise.isj.proxy.model.Organization;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.JSONUtil;
import com.proper.enterprise.platform.core.utils.StringUtil;

/**
 * Created by think on 2016/8/16 0016. 指定科室详细
 */
@RestController
@RequestMapping(path = "/organization")
public class OrganizationController extends BaseController {

	@RequestMapping(path = "/depts/{id}", method = RequestMethod.GET)
	public ResponseEntity<Organization> getOrganization(@PathVariable String id) {
		Organization org = new Organization();
		if (StringUtil.isNotEmpty(id)) {
			String orgStr = "";
			if (id.equals("1")) {
				orgStr = "{" + "  'id': '1'," + "  'name': '第一内分泌科门诊（南湖）'" + "}";
			} else if (id.equals("2")) {
				orgStr = "{" + "  'id': '2'," + "  'name': '第二内分泌科门诊（滑翔）'" + "}";
			}
			try {
				org = JSONUtil.parse(orgStr, Organization.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseOfGet(org);
	}

	@RequestMapping(path = "/districts", method = RequestMethod.GET)
	public ResponseEntity<List<District>> getDistricts() {
		String disStr = "[" + "  {" + "    'id': '1'," + "    'name': '南湖院区'" + "  }," + "  {"
				+ "    'id': '2'," + "    'name': '滑翔院区'" + "  }," + "  {" + "    'id': '3',"
				+ "    'name': '沈北院区'" + "  }" + "]";
		List<District> disList = new ArrayList<>();
		try {
			disList = JSONUtil.parse(disStr, List.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseOfGet(disList);
	}

}
