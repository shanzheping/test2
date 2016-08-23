package com.proper.enterprise.isj.proxy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.proxy.model.Subject;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.JSONUtil;
import com.proper.enterprise.platform.core.utils.StringUtil;

/**
 * Created by think on 2016/8/16 0016. 学科列表
 */
@RestController
@RequestMapping(path = "/subjects")
public class SubjectController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Subject>> getSubject(String districtId, String subjectId) {
		String sub = "";
		if (StringUtil.isNotEmpty(subjectId) && subjectId.equals("1")) {
			sub = "[" + "  {" + "    'id': '11'," + "    'name': '内分泌'," + "    'hasNext': '0'"
					+ "  }," + "  {" + "    'id': '12'," + "    'name': '消化',"
					+ "    'hasNext': '0'" + "  }," + "  {" + "    'id': '13',"
					+ "    'name': '呼吸'," + "    'hasNext': '1'" + "  }," + "  {"
					+ "    'id': '14'," + "    'name': '血液'," + "    'hasNext': '1'" + "  },"
					+ "  {" + "    'id': '15'," + "    'name': '心血管'," + "    'hasNext': '0'"
					+ "  }," + "  {" + "    'id': '16'," + "    'name': '肾脏',"
					+ "    'hasNext': '1'" + "  }," + "  {" + "    'id': '17',"
					+ "    'name': '风湿'," + "    'hasNext': '0'" + "  }" + "]";
		} else {
			sub = "[" + "  {" + "    'id': '1'," + "    'name': '内科'," + "    'hasNext': '1'"
					+ "  }," + "  {" + "    'id': '2'," + "    'name': '外科'," + "    'hasNext': '1'"
					+ "  }," + "  {" + "    'id': '3'," + "    'name': '妇产科',"
					+ "    'hasNext': '1'" + "  }," + "  {" + "    'id': '4'," + "    'name': '儿科',"
					+ "    'hasNext': '1'" + "  }," + "  {" + "    'id': '5'," + "    'name': '眼科',"
					+ "    'hasNext': '0'" + "  }," + "  {" + "    'id': '6',"
					+ "    'name': '耳鼻喉'," + "    'hasNext': '1'" + "  }," + "  {"
					+ "    'id': '7'," + "    'name': '皮肤'," + "    'hasNext': '0'" + "  }," + "  {"
					+ "    'id': '8'," + "    'name': '中医'," + "    'hasNext': '1'" + "  }," + "  {"
					+ "    'id': '9'," + "    'name': '介入'," + "    'hasNext': '1'" + "  }," + "  {"
					+ "    'id': '10'," + "    'name': '传染'," + "    'hasNext': '1'" + "  }" + "]";
		}
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		try {
			mapList = JSONUtil.parse(sub, mapList.getClass());
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Subject> subList = new ArrayList<>();
		Subject tempSub = null;
		for (Map<String, String> map : mapList) {
			tempSub = new Subject();
			BeanUtils.copyProperties(map, tempSub);
			subList.add(tempSub);
		}
		return responseOfGet(subList);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Subject> getSubjectById(@PathVariable String id) {
		Subject sub = new Subject();
		return responseOfGet(sub);
	}
}
