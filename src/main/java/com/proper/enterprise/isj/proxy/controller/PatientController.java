package com.proper.enterprise.isj.proxy.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.proxy.model.Patient;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.JSONUtil;

/**
 * Created by think on 2016/8/16 0016. 登录患者信息
 */
@RestController
@RequestMapping(path = "/patients")
public class PatientController extends BaseController {
	/**
	 * 取得登录患者信息
	 *
	 * @param memberId
	 * @return
	 */
	@RequestMapping(path = "/patient", method = RequestMethod.GET)
	public ResponseEntity<Patient> getPatient(String memberId) {
		String patStr = "{" + "  'id': '1'," + "  'name': '王二'," + "  'sexCode': '1',"
				+ "  'sex': '男'," + "  'idCard': '20106199010102222'," + "  'phone': '13612345678',"
				+ "  'memberCode': '00'," + "  'member': '我'" + "}";
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = JSONUtil.parse(patStr, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Patient pat = new Patient();
		BeanUtils.copyProperties(map, pat);
		return responseOfGet(pat);
	}
}
