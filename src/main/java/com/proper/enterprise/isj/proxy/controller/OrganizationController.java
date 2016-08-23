package com.proper.enterprise.isj.proxy.controller;

import com.proper.enterprise.isj.proxy.model.Organization;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.JSONUtil;
import com.proper.enterprise.platform.core.utils.StringUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

	public static void main(String[] args) throws IOException {
		Organization org = new Organization();
		String orgStr = "{" + "  'id': '1'," + "  'name': '第一内分泌科门诊（南湖）'" + "}";
		try {
			org = JSONUtil.parse(orgStr, Organization.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(JSONUtil.toJSON(org));
	}
}
