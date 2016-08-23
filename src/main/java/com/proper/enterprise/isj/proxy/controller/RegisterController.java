package com.proper.enterprise.isj.proxy.controller;

import com.proper.enterprise.isj.proxy.model.RegisterDoctor;
import com.proper.enterprise.isj.proxy.model.Registration;
import com.proper.enterprise.isj.proxy.model.RegistrationAdd;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.JSONUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by think on 2016/8/16 0016.
 */

@RestController
@RequestMapping(path = "/register")
public class RegisterController extends BaseController {

	/**
	 * 取得挂号须知信息
	 *
	 * @return
	 */
	@RequestMapping(path = "/agreement", method = RequestMethod.GET)
	public ResponseEntity<String> getAgreement() {
		String guideMsg = "1、当日挂号不需要取号，直接携带身份证（就医卡）去对应科室候诊，就诊排队序号可以在手机挂号单的详细界面里查看到，如果未携带身份证或就医卡需要持手机挂号单上的门诊号到窗口办理就医卡。<br/>2、预约挂号成功后。请持患者本人身份证（或户口本）原件或复印件，到任何门诊收费窗口取号。就诊人预约登记姓名必须和身份证（或户口本）姓名一致，否则不给取挂号票。首次看诊在盛京医院使用过身份证作为就医卡的患者，可以直接去自助机取号，否则需到窗口取号。<br/>3、当日挂号不需要取号，直接携带身份证（就医卡）去对应科室候诊，就诊排队序号可以在手机挂号单的详细界面里查看到，如果未携带身份证或就医卡需要持手机挂号单上的门诊号到窗口办理就医卡。<br/>4、挂当天号患者，直接在候诊区等候叫号。<br/>5、当日挂号不需要取号，直接携带身份证（就医卡）去对应科室候诊，就诊排队序号可以在手机挂号单的详细界面里查看到，如果未携带身份证或就医卡需要持手机挂号单上的门诊号到窗口办理就医卡<br/>";
		return responseOfGet(guideMsg);
	}

	/**
	 * 取得预约挂号提示信息
	 *
	 * @return
	 */
	@RequestMapping(path = "/apptPrompt", method = RequestMethod.GET)
	public ResponseEntity<String> getApptPrompt() {
		String guideMsg = "实名制挂号，需要持本人身份证到窗口取号<br/>儿童没有身份证可携带户口本到窗口取号<br/>耳鼻喉科预约挂号到诊取挂号票时请缴5.5元检查费<br/>预约vip特需专家，请到院后补交特需服务费194元<br/>辅助生殖门诊不采用叫号系统，以现场引导为准";
		return responseOfGet(guideMsg);
	}

	/**
	 * 取得当日挂号提示信息
	 *
	 * @return
	 */
	@RequestMapping(path = "/todayPrompt", method = RequestMethod.GET)
	public ResponseEntity<String> getTodayPrompt() {
		String guideMsg = "单日挂号不需要取号，直接携带身份证去对应科室候诊。<br/>儿童挂现场号需要绑定就诊卡。";
		return responseOfGet(guideMsg);
	}

	/**
	 * 取得医生挂号信息
	 *
	 * @param doctorId
	 * @param date
	 * @return
	 */
	@RequestMapping(path = "/doctor", method = RequestMethod.GET)
	public ResponseEntity<RegisterDoctor> getRegisterDoctor(
			@RequestParam(required = true) String doctorId,
			@RequestParam(required = true) String date) {
		String docStr = "{" + "  id: 1," + "  name: '姓名1'," + "  sexCode: 1,"
				+ "  clinicCategoryCode: 1," + "  amount: 5.00" + "}";
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = JSONUtil.parse(docStr, map.getClass());
		} catch (IOException e) {
			e.printStackTrace();
		}
		RegisterDoctor doc = new RegisterDoctor();
		BeanUtils.copyProperties(map, doc);
		return responseOfGet(doc);
	}

	/**
	 * 取得指定挂号单详细
	 *
	 * @return
	 */
	@RequestMapping(path = "/registration", method = RequestMethod.GET)
	public ResponseEntity<Registration> getRegistration() {
		String regStr = "";
		Registration reg = new Registration();
		return responseOfGet(reg);
	}

	/**
	 * 添加挂号单
	 *
	 * @return
	 */
	@RequestMapping(path = "/registration", method = RequestMethod.PUT)
	public ResponseEntity<String> putRegistration(RegistrationAdd reg) {
		String regId = "";
		return responseOfPut(regId);
	}
}
