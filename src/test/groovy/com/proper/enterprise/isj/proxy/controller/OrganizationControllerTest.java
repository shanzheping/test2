package com.proper.enterprise.isj.proxy.controller;

import com.proper.enterprise.platform.test.AbstractTest;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;

/**
 * Created by think on 2016/8/16 0016.
 */
public class OrganizationControllerTest extends AbstractTest {

	@Test
	public void testGetOrganization() throws Exception {
		MvcResult result = get("/organization/depts/1", HttpStatus.OK);
		System.out.println(result.getResponse().getContentAsString());
	}


	@Test
	public void testGetDistricts() throws Exception {
		MvcResult result = get("/organization/districts", HttpStatus.OK);
		System.out.println(result.getResponse().getContentAsString());
	}
}