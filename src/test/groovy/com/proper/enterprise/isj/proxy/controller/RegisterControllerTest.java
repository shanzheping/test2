package com.proper.enterprise.isj.proxy.controller;

import com.proper.enterprise.platform.test.AbstractTest;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;

/**
 * Created by think on 2016/8/16 0016.
 */
public class RegisterControllerTest extends AbstractTest {

	@Test
	public void testGetAgreement() throws Exception {
		MvcResult result = get("/register/agreement", HttpStatus.OK);
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetApptPrompt() throws Exception {
		MvcResult result = get("/register/apptPrompt", HttpStatus.OK);
		System.out.println(result.getResponse().getContentAsString());

	}

	@Test
	public void testGetTodayPrompt() throws Exception {
		MvcResult result = get("/register/todayPrompt", HttpStatus.OK);
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetRegisterDoctor() throws Exception {
		MvcResult result = get("/register/doctor?doctorId=1&date=2016-01-01", HttpStatus.OK);
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetRegistration() throws Exception {
		MvcResult result = get("/register/registration", HttpStatus.OK);
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testPutRegistration() throws Exception {

	}
}