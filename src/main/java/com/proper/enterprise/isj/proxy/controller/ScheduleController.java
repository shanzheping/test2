package com.proper.enterprise.isj.proxy.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.proxy.model.Doctor;
import com.proper.enterprise.isj.proxy.model.Schedule;
import com.proper.enterprise.isj.proxy.service.IScheduleService;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.DateUtil;

/**
 * Created by think on 2016/8/16 0016. 指定医生排班时间列表（只返回有号日期）
 */
@RestController
@RequestMapping(path = "/schedule")
public class ScheduleController extends BaseController {

	@Autowired
	private IScheduleService scheduleService;

	/**
	 * 指定医生排班时间列表（只返回有号日期）
	 *
	 * @param doctorId
	 *            医生ID
	 * @param date
	 *            日期
	 * @return 指定医生排班时间列表（只返回有号日期）
	 */
	@RequestMapping(path = "/dates", method = RequestMethod.GET)
	public ResponseEntity<List<Schedule>> getDoctorDate(
			@RequestParam(required = true) String doctorId, String date) {
		List<Schedule> scheList = new ArrayList<>();
		return responseOfGet(scheList);
	}

	/**
	 * 取得排班医生列表（只返回有号医生）
	 *
	 * @param districtId
	 *            院区Id
	 * @param subjectId
	 *            学科Id
	 * @param isChild
	 *            是否儿科医生
	 * @param description
	 *            专业/专长
	 * @param startDate
	 *            排班开始时间
	 * @param endDate
	 *            排班结束时间
	 * @return 排班医生列表（只返回有号医生）
	 */
	@RequestMapping(path = "/doctors", method = RequestMethod.GET)
	public ResponseEntity<List<Doctor>> getScheduleDoctors(String districtId, String subjectId,
			String isChild, String description, @RequestParam(required = true) String startDate,
			String endDate) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.toDate(startDate));
		Date sDate = cal.getTime();
		Date eDate = null;
		if (endDate != null) {
			cal.setTime(DateUtil.toDate(endDate));
			eDate = cal.getTime();
		} else {
			eDate = sDate;
		}
		List<Doctor> docList = new ArrayList<>();
		try {
			docList = scheduleService.getScheduleDoctors(districtId, subjectId, null, sDate, eDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// String docStr = "[" + " {" + " 'id': '1'," + " 'name': '姓名1',"
		// + " 'sexCode': '1'," + " 'title': '主任医师'," + " 'skill':
		// '擅长于风湿科常见疾病',"
		// + " 'deptId': '1'," + " 'dept': '第一内分泌科门诊（南湖）'" + " }," + " {"
		// + " 'id': '2'," + " 'name': '姓名2'," + " 'sexCode': '2',"
		// + " 'title': '副主任医师'," + " 'skill': ''," + " 'deptId': '2',"
		// + " 'dept': '第二内分泌科门诊（滑翔）'" + " }" + "]";
		// List<Map<String, String>> mapList = new ArrayList<>();
		// try {
		// mapList = JSONUtil.parse(docStr, mapList.getClass());
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// Doctor tempDoc = null;
		// for (Map<String, String> map : mapList) {
		// tempDoc = new Doctor();
		// BeanUtils.copyProperties(map, tempDoc);
		// docList.add(tempDoc);
		// }
		return responseOfGet(docList);
	}

	/**
	 * 指定日期医生一天出诊时间列表（只返回有号时间）
	 *
	 * @param date
	 *            日期
	 * @param doctorId
	 *            医生ID
	 * @return "example": ["09:10", "09:20"]
	 */
	@RequestMapping(path = "/times", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getTimes(@RequestParam(required = true) String date,
			@RequestParam(required = true) String doctorId) {
		List<String> timeList = new ArrayList<>();
		return responseOfGet(timeList);
	}
}
