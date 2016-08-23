package com.proper.enterprise.isj.proxy.service.impl;

import java.util.*;

import com.proper.enterprise.isj.proxy.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;

import com.proper.enterprise.isj.proxy.model.Doctor;
import com.proper.enterprise.isj.proxy.service.IScheduleService;
import com.proper.enterprise.isj.webservices.WebServicesClient;
import com.proper.enterprise.isj.webservices.model.res.RegInfo;
import com.proper.enterprise.isj.webservices.model.res.ResModel;
import com.proper.enterprise.isj.webservices.model.res.reginfo.RegDoctor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by think on 2016/8/19 0019.
 */
@Service
@Transactional
public class SchedulingService implements IScheduleService {

	@Autowired
	private WebServicesClient webServicesClient;

	@Autowired
	private IDoctorService doctorService;

	@Override
	public List<Doctor> getScheduleDoctors(String hosId, String deptId, String doctorId,
			Date startDate, Date endDate) throws Exception {
		// ResModel<RegInfo> regInfo = webServicesClient.getRegInfo(hosId,
		// deptId, doctorId, startDate,
		// endDate);
		ResModel<RegInfo> regInfo = new ResModel<>();
		List<Doctor> docList = new ArrayList<>();
		if (regInfo != null && regInfo.getRes() != null) {
			Set<String> doctorIdSet = new HashSet<String>();
			List<RegDoctor> regDocList = regInfo.getRes().getRegDoctorList();
			Doctor doc = null;
			for (RegDoctor regDoctor : regDocList) {
				doctorIdSet.add(regDoctor.getDoctorId());
			}
			docList = doctorService.getDoctorListFromHisByIds(doctorIdSet);
		}
		return docList;
	}

}
