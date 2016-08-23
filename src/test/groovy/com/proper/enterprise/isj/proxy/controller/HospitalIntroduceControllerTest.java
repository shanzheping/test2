package com.proper.enterprise.isj.proxy.controller;

import com.proper.enterprise.platform.test.AbstractTest;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;

/**
 * Created by think on 2016/8/16 0016.
 */
public class HospitalIntroduceControllerTest extends AbstractTest {

	@Test
	public void testGetHospitalIntroduce() throws Exception {
		MvcResult result = get("/hospitalIntroduce", HttpStatus.OK);
		System.out.println(result.getResponse().getContentAsString());
	}
}