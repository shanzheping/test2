package com.proper.enterprise.isj.proxy.service;

import java.util.Date;
import java.util.List;

import com.proper.enterprise.isj.proxy.model.Doctor;

/**
 * Created by think on 2016/8/19 0019.
 */
public interface IScheduleService {

	/**
	 * 平台通过调用HIS的该接口获取某医生具体的排班信息。 医生ID（DOCTOR_ID）为-1时查询科室ID下所有医生排班。
	 *
	 * @param hosId
	 *            医院ID
	 * @param deptId
	 *            科室ID，HIS系统中科室唯一ID
	 * @param doctorId
	 *            医生ID，HIS系统中医生唯一ID，为-1时查询科室ID下所有医生排班
	 * @param startDate
	 *            排班开始日期，格式：YYYY-MM-DD
	 * @param endDate
	 *            排班结束日期，格式：YYYY-MM-DD
	 * @return 响应模型及排班信息对象
	 * @throws Exception
	 */
	List<Doctor> getScheduleDoctors(String hosId, String deptId, String doctorId, Date startDate,
			Date endDate) throws Exception;
}
