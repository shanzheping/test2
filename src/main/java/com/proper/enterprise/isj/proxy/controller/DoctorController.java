package com.proper.enterprise.isj.proxy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.proxy.model.Doctor;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.JSONUtil;
import com.proper.enterprise.platform.core.utils.StringUtil;

/**
 * Created by think on 2016/8/16 0016. 查询全院医生列表
 */
@RestController
@RequestMapping(path = "/doctors")
public class DoctorController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Doctor>> getDoctors(String searchName, String subjectId) {
		String docStr = "[" + "  {" + "    'id': '1'," + "    'name': '姓名1',"
				+ "    'sexCode': '1'," + "    'title': '主任医师'," + "    'hospital': '中国医科大学附属盛京医院',"
				+ "    'deptId': '1'," + "    'dept': '第一内分泌科门诊（南湖）'," + "    'skill': '擅长于风湿科常见疾病'"
				+ "  }," + "  {" + "    'id': '2'," + "    'name': '姓名2'," + "    'sexCode': '2',"
				+ "    'title': '副主任医师'," + "    'hospital': '中国医科大学附属盛京医院'," + "    'deptId': '2',"
				+ "    'dept': '第二内分泌科门诊（滑翔）'," + "    'skill': ''" + "  }," + "  {"
				+ "    'id': '1'," + "    'name': '姓名3'," + "    'sexCode': '2',"
				+ "    'title': '主任医师'," + "    'hospital': '中国医科大学附属盛京医院'," + "    'deptId': '1',"
				+ "    'dept': '第一内分泌科门诊（南湖）'," + "    'skill': ''" + "  }," + "  {"
				+ "    'id': '1'," + "    'name': '姓名4'," + "    'sexCode': '1',"
				+ "    'title': '主任医师'," + "    'hospital': '中国医科大学附属盛京医院'," + "    'deptId': '1',"
				+ "    'dept': '第一内分泌科门诊（南湖）'," + "    'skill': ''" + "  }," + "  {"
				+ "    'id': '1'," + "    'name': '姓名5'," + "    'sexCode': '1',"
				+ "    'title': '主任医师'," + "    'hospital': '中国医科大学附属盛京医院'," + "    'deptId': '2',"
				+ "    'dept': '第二内分泌科门诊（滑翔）'," + "    'skill': ''" + "  }" + "]";

		List<Doctor> docList = new ArrayList<>();
		try {
			docList = JSONUtil.parse(docStr, List.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseOfGet(docList);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Doctor> getDoctorById(@PathVariable String id) {
		String docStr = "";
		if (StringUtil.isNotEmpty(id)) {
			if (id.equals("1")) {
				docStr = "{" + "  'id': '1'," + "  'name': '姓名1'," + "  'sexCode': '1',"
						+ "  'title': '主任医师'," + "  'hospital': '中国医科大学附属盛京医院',"
						+ "  'deptId': '1'," + "  'dept': '第一内分泌科门诊（南湖）',"
						+ "  'skill': '专业特长：骨关节、神经系统及其他专业影像诊断',"
						+ "  'summary': '姓名1教授、博士生导师。从事临床医学影像诊断、教学及科研工作50余年。曾任北美放射学会和国际骨关节学会会员、中华医学会全国放射学会和全国医学影像技术研究会常委、辽宁省医学影像学会会长、《中国临床医学影像杂志》常务副主编、《中华放射学杂志》常务编委及其他数种国内著名医学影像杂志编委。'"
						+ "}";

			} else if (id.equals("2")) {
				docStr = "{" + "  'id': '2'," + "  'name': '姓名2'," + "  'sexCode': '2',"
						+ "  'title': '副主任医师'," + "  'hospital': '中国医科大学附属盛京医院',"
						+ "  'deptId': '2'," + "  'dept': '第二内分泌科门诊（滑翔）',"
						+ "  'skill': '专业特长：骨关节、神经系统及其他专业影像诊断',"
						+ "  'summary': '姓名1教授、博士生导师。从事临床医学影像诊断、教学及科研工作50余年。曾任北美放射学会和国际骨关节学会会员、中华医学会全国放射学会和全国医学影像技术研究会常委、辽宁省医学影像学会会长、《中国临床医学影像杂志》常务副主编、《中华放射学杂志》常务编委及其他数种国内著名医学影像杂志编委。测试测试测试测试测试测试测试测试测试测试测试。'"
						+ "}";
			}
		}
		Doctor doc = new Doctor();
		try {
			doc = JSONUtil.parse(docStr, Doctor.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseOfGet(doc);
	}
}
