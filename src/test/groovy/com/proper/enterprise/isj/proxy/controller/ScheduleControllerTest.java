package com.proper.enterprise.isj.proxy.controller;

import com.proper.enterprise.platform.test.AbstractTest;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;

/**
 * Created by think on 2016/8/16 0016.
 */
public class ScheduleControllerTest extends AbstractTest {

	@Test
	public void testGetDoctorDate() throws Exception {
		MvcResult result = get("/schedule/dates?doctorId=1", HttpStatus.NOT_FOUND);
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetDoctors() throws Exception {
		MvcResult result = get("/schedule/doctors?startDate=2016-08-22", HttpStatus.NOT_FOUND);
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetTimes() throws Exception {
		MvcResult result = get("/schedule/times?date=2016-08-22&doctorId=1", HttpStatus.NOT_FOUND);
		System.out.println(result.getResponse().getContentAsString());
	}
}