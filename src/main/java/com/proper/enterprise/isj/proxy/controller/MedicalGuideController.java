package com.proper.enterprise.isj.proxy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.platform.core.controller.BaseController;

/**
 * Created by think on 2016/8/15 0015. 就医指南信息
 */
@RestController
@RequestMapping(path = "/medicalGuide")
public class MedicalGuideController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getMedicalGuide() {
		String guideMsg = "<p>检验结果查询指南：<br>*挂号时必须预留姓名身份证及手机号，如果没留APP上无法查看，必须来医院自助机上取报告。</p><p>*APP上的信息必须和窗口留的一致才可以查看到检验报告。<br>*如果要修改完善个人信息必须携带身份证来医院窗口修改。</p><p>挂号相关问题指南：<br>1、当日挂号不需要取号，直接携带身份证（就医卡）去对应科室候诊</p>";
		return responseOfGet(guideMsg);
	}
}
