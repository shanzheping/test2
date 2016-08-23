package com.proper.enterprise.isj.proxy.service;

import com.proper.enterprise.isj.proxy.model.Doctor;

import java.util.Collection;
import java.util.List;

/**
 * Created by think on 2016/8/19 0019.
 * 医生相关接口
 */
public interface IDoctorService {

    Doctor getDoctorListFromHisById();

    List<Doctor> getDoctorListFromHisByIds(Collection<String> ids);
}
