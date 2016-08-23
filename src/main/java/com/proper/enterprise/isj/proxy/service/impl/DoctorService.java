package com.proper.enterprise.isj.proxy.service.impl;

import com.proper.enterprise.isj.proxy.model.Doctor;
import com.proper.enterprise.isj.proxy.service.IDoctorService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by think on 2016/8/19 0019.
 *
 */
@Service
@Transactional
public class DoctorService implements IDoctorService{

    @Override
    public Doctor getDoctorListFromHisById() {
        return null;
    }

    @Override
    public List<Doctor> getDoctorListFromHisByIds(Collection<String> ids) {
        List<Doctor> doctorList = new ArrayList<>();
        return doctorList;
    }
}
