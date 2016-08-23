package com.proper.enterprise.isj.proxy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.proxy.model.Floor;
import com.proper.enterprise.isj.proxy.model.HospitalNavigation;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.JSONUtil;

/**
 * Created by think on 2016/8/16 0016. 取得各院区楼列表
 */
@RestController
@RequestMapping(path = "/hospitalNavigation")
public class HospitalNavigationController extends BaseController {

	@RequestMapping(path = "/builds", method = RequestMethod.GET)
	public ResponseEntity<List<HospitalNavigation>> getBuilds(
			@RequestParam(required = true) String districtId) {
		System.out.println(districtId);
		String builder = "[" + "  {" + "    'id': '1'," + "    'name': '南湖院区'," + "    'builds': ["
				+ "      {" + "        'id': '1'," + "        'name': '1号楼A座',"
				+ "        'function': '门诊楼'" + "      }," + "      {" + "        'id': '2',"
				+ "        'name': '1号楼B座'," + "        'function': '急诊楼'" + "      }," + "      {"
				+ "        'id': '3'," + "        'name': '1号楼'," + "        'function': '住院楼'"
				+ "      }," + "      {" + "        'id': '4'," + "        'name': '4号楼',"
				+ "        'function': ''" + "      }" + "    ]" + "  }," + "  {" + "    'id': '2',"
				+ "    'name': '滑翔院区'," + "    'builds': [" + "      {" + "        'id': '1',"
				+ "        'name': '1号'," + "        'function': ''" + "      }," + "      {"
				+ "        'id': '2'," + "        'name': '2号楼'," + "        'function': ''"
				+ "      }," + "      {" + "        'id': '3'," + "        'name': '3号楼',"
				+ "        'function': ''" + "      }" + "    ]" + "  }," + "  {" + "    'id': '3',"
				+ "    'name': '沈北院区'," + "    'builds': [" + "      {" + "        'id': '1',"
				+ "        'name': '1号'," + "        'function': ''" + "      }," + "      {"
				+ "        'id': '2'," + "        'name': '2号楼'," + "        'function': ''"
				+ "      }" + "    ]" + "  }" + "]";
		List<HospitalNavigation> hopList = new ArrayList<HospitalNavigation>();
		try {
			hopList = JSONUtil.parse(builder, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseOfGet(hopList);
	}

	@RequestMapping(path = "/builds/floors", method = RequestMethod.GET)
	public ResponseEntity<List<Floor>> getFloor(String buildId) {
		String fl = "[" + "  {" + "    'id': '1'," + "    'name': 'B1F',"
				+ "    'depts': ['核磁共振CT室', 'DR室', 'ECT室']" + "  }," + "  {" + "    'id': '2',"
				+ "    'name': '01F'," + "    'depts': ['神经功能科', '门诊收费处', '便民门诊', '消费监控中心']"
				+ "  }," + "  {" + "    'id': '3'," + "    'name': '02F',"
				+ "    'depts': ['儿科门诊', '发育儿科', '儿科药房', '临床营养科']" + "  }," + "  {"
				+ "    'id': '4'," + "    'name': '03F',"
				+ "    'depts': ['科室1', '科室2', '科室3', '科室4', '科室5', '科室6', '科室7']" + "  }" + "]";
		List<Floor> floors = new ArrayList<Floor>();
		try {
			floors = JSONUtil.parse(fl, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseOfGet(floors);
	}

	public static void main(String[] args) throws IOException {
		String builder = "[" + "  {" + "    'id': '1'," + "    'name': '南湖院区'," + "    'builds': ["
				+ "      {" + "        'id': '1'," + "        'name': '1号楼A座',"
				+ "        'function': '门诊楼'" + "      }," + "      {" + "        'id': '2',"
				+ "        'name': '1号楼B座'," + "        'function': '急诊楼'" + "      }," + "      {"
				+ "        'id': '3'," + "        'name': '1号楼'," + "        'function': '住院楼'"
				+ "      }," + "      {" + "        'id': '4'," + "        'name': '4号楼',"
				+ "        'function': ''" + "      }" + "    ]" + "  }," + "  {" + "    'id': '2',"
				+ "    'name': '滑翔院区'," + "    'builds': [" + "      {" + "        'id': '1',"
				+ "        'name': '1号'," + "        'function': ''" + "      }," + "      {"
				+ "        'id': '2'," + "        'name': '2号楼'," + "        'function': ''"
				+ "      }," + "      {" + "        'id': '3'," + "        'name': '3号楼',"
				+ "        'function': ''" + "      }" + "    ]" + "  }," + "  {" + "    'id': '3',"
				+ "    'name': '沈北院区'," + "    'builds': [" + "      {" + "        'id': '1',"
				+ "        'name': '1号'," + "        'function': ''" + "      }," + "      {"
				+ "        'id': '2'," + "        'name': '2号楼'," + "        'function': ''"
				+ "      }" + "    ]" + "  }" + "]";
//		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<HospitalNavigation> hopList = new ArrayList<HospitalNavigation>();
		try {
			hopList = JSONUtil.parse(builder, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(JSONUtil.toJSON(hopList));
	}
}
