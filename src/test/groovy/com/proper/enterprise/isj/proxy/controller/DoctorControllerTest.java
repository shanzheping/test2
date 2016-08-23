package com.proper.enterprise.isj.proxy.controller;

import com.proper.enterprise.isj.proxy.model.Doctor;
import com.proper.enterprise.platform.core.utils.JSONUtil;
import com.proper.enterprise.platform.test.AbstractTest;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by think on 2016/8/16 0016.
 */
public class DoctorControllerTest extends AbstractTest {

	@Test
	public void testGetDoctors() throws Exception {
		String obj = (String) getAndReturn("/doctors", "", HttpStatus.OK);
		List<Doctor> docList = JSONUtil.parse(obj, List.class);
		System.out.println(docList);
	}

	@Test
	public void testGetDoctorById() throws Exception {
		MvcResult result = get("/doctors/1", HttpStatus.OK);
		System.out.println(result.getResponse().getContentAsString());
	}
}