package com.proper.enterprise.isj.proxy.controller;

import com.proper.enterprise.platform.core.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by think on 2016/8/15 0015. 医院简介信息
 */
@RestController
@RequestMapping(path = "/hospitalIntroduce")
public class HospitalIntroduceController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getHospitalIntroduce() {
		String guideMsg = "<p>中国医科大学附属盛京医院介绍：<br>中国医科大学附属盛京医院是一所大型综合性现代化数字化大学附属医院。<br>医院素以精湛高超的医疗技术和齐全完善的学科特色闻名于国内外。<br>医院信息现代化建设行业领先，临床科室全部应用电子病历。</p>";
		return responseOfGet(guideMsg);
	}
}
