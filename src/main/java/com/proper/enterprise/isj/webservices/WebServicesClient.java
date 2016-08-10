package com.proper.enterprise.isj.webservices;

import com.proper.enterprise.isj.webservices.model.req.OrderRegReq;
import com.proper.enterprise.isj.webservices.model.req.ReqModel;
import com.proper.enterprise.isj.webservices.model.res.NetTestResult;
import com.proper.enterprise.isj.webservices.model.res.RegInfo;
import com.proper.enterprise.isj.webservices.model.res.ResModel;
import com.proper.enterprise.isj.webservices.service.RegSJService;
import com.proper.enterprise.platform.core.utils.CipherUtil;
import com.proper.enterprise.platform.core.utils.ConfCenter;
import com.proper.enterprise.platform.core.utils.DateUtil;
import com.proper.enterprise.platform.core.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebServicesClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServicesClient.class);

    @Autowired WebApplicationContext wac;
    @Autowired CipherUtil aes;
    @Autowired RegSJService regSJService;
    @Autowired Marshaller marshaller;
    @Autowired Unmarshaller unmarshaller;

    /**
     * 在调用其它接口之前用于测试目标服务网络是否通畅，服务是否处于工作状态、数据库是否处于连接状态
     *
     * @param hosId 医院ID
     * @param ip    请求IP
     * @return NetTestResult in ResModel
     * @throws Exception
     */
    public ResModel<NetTestResult> netTest(String hosId, String ip) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        map.put("IP", ip);
        String res = invokeWS("netTest", map);
        return parseEnvelop(res, NetTestResult.class);
    }

    private String invokeWS(String methodName, Map<String, String> param) throws Exception {
        Method method = RegSJService.class.getDeclaredMethod(methodName, String.class);
        return (String) method.invoke(regSJService,
                envelopReq(ConfCenter.get("isj.funCode." + methodName), param));
    }

    protected String envelopReq(String funCode, Map<String, String> map) throws IOException {
        Assert.notNull(funCode, "FunCode should not null!");
        Writer writer = new StringWriter();
        ReqModel m = new ReqModel();
        m.setFunCode(funCode);
        m.setUserId(ConfCenter.get("isj.userId"));
        m.setReq(map);
        marshaller.marshal(m, new StreamResult(writer));

        String result = writer.toString();
        // 东软接口不识别转义后的形式，故需要将被 marshaller 自动转义的字符替换回去
        result = result.replace("&lt;", "<").replace("&gt;", ">");
        LOGGER.debug("Actual request after envelop is: {}", result);

        return result;
    }

    private <T> ResModel<T> parseEnvelop(String responseStr, Class<T> clz) throws Exception {
        ResModel<T> resModel = (ResModel<T>) unmarshaller.unmarshal(new StreamSource(new StringReader(responseStr)));
        if (signValid(resModel)) {
            String res = aes.decrypt(resModel.getResEncrypted());
            Unmarshaller u = wac.getBean("unmarshall" + clz.getSimpleName(), Unmarshaller.class);
            T resObj = (T) u.unmarshal(new StreamSource(new StringReader(res)));
            resModel.setRes(resObj);
            return resModel;
        } else {
            LOGGER.error("Sign is INVALID!! Could NOT parse response!!");
            return null;
        }
    }

    private boolean signValid(ResModel resModel) {
        Assert.notNull(resModel.getSign());

        String sign = MessageFormat.format(
                ConfCenter.get("isj.template.sign.res"),
                resModel.getResEncrypted(),
                resModel.getReturnCode().getCode(),
                resModel.getReturnMsg(),
                ConfCenter.get("isj.key"));

        return resModel.getSign().equalsIgnoreCase(MD5Util.md5Hex(sign));
    }

    /**
     * 查询医院列表及单个医院的详细信息。
     * 需要获取医院基本信息时调用，平台可通过该接口获取医院的信息更新。
     *
     * @param hosId 医院ID
     * @return
     *
     * <RES>
     *   <HOS_ID>1001</HOS_ID>                  医院ID
     *   <NAME>广东省人民医院</NAME>               医院名称
     *   <SHORT_NAME>省人医</SHORT_NAME>         医院名称简称
     *   <ADDRESS>地址</ADDRESS>                 医院地址
     *   <TEL>020-11231112</TEL>                联系电话
     *   <WEBSITE>http://www.xxx.com</WEBSITE>  医院网站
     *   <WEIBO></WEIBO>                        医院微博地址
     *   <LEVEL>3</LEVEL>                       医院等级，详见 “医院等级”
     *   <DESC></DESC>                          医院介绍
     *   <SPECIAL></SPECIAL>                    医院专长
     *   <LONGITUDE></LONGITUDE>                医院所在位置经度，例：113.452472
     *   <LATITUDE></LATITUDE>                  医院所在位置纬度，例：23.111814
     *   <MAX_REG_DAYS>0</MAX_REG_DAYS>         最大预约天数
     *   <START_REG_TIME></START_REG_TIME>      开始预约时间，格式：HH:MI
     *   <END_REG_TIME></END_REG_TIME>          停止预约时间，格式：HH:MI
     *   <STOP_BOOK_TIMEA></STOP_BOOK_TIMEA>    上午停止取号时间，格式：HH:MI
     *   <STOP_BOOK_TIMEP></STOP_BOOK_TIMEP>    下午停止取号时间，格式：HH:MI
     * </RES>
     *
     * @throws Exception
     */
    public String getHosInfo(String hosId) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        return invokeWS("getHosInfo", map);
    }

    /**
     * 平台可通过该接口获取医院科室信息更新，查询有排班科室。
     * 当科室ID (DEPT_ID) 为-1时查询所有科室信息，为0时查询所有一级科室信息，为其他的时查本科室以及所有子科室信息。
     *
     * @param hosId     医院ID
     * @param deptId    科室ID，HIS系统中科室唯一ID，为-1时查询所有科室信息，为0时查询所有一级科室信息，为其他的时查本科室以及所有子科室信息
     * @return
     *
     * <RES>
     *   <HOS_ID></HOS_ID>              医院ID
     *   <DEPT_LIST>                    科室信息集合
     *     <DEPT_ID>100101</DEPT_ID>    科室ID,HIS系统中科室唯一ID
     *     <DEPT_NAME>骨科</DEPT_NAME>   科室名称
     *     <PARENT_ID>-1</PARENT_ID>    上级科室ID，顶级时为-1
     *     <DESC></DESC>                介绍
     *     <LEVEL>1</LEVEL>             科室等级，详见 “科室等级”
     *     <ADDRESS>门诊一楼</ADDRESS>    科室所在位置
     *     <EXPERTISE></EXPERTISE>      科室主治
     *     <STATUS>1</STATUS>           状态，详见 “状态”
     *   </DEPT_LIST>
     *   <DEPT_LIST>
     *     …
     *   </DEPT_LIST>
     * </RES>
     *
     * @throws Exception
     */
    public String getDeptInfo(String hosId, String deptId) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        map.put("DEPT_ID", deptId);
        return invokeWS("getDeptInfo", map);
    }

    /**
     * 平台通过调用HIS的该接口获取某医生具体的排班信息。
     * 医生ID（DOCTOR_ID）为-1时查询科室ID下所有医生排班。
     *
     * @param hosId     医院ID
     * @param deptId    科室ID，HIS系统中科室唯一ID
     * @param doctorId  医生ID，HIS系统中医生唯一ID，为-1时查询科室ID下所有医生排班
     * @param startDate 排班开始日期，格式：YYYY-MM-DD
     * @param endDate   排班结束日期，格式：YYYY-MM-DD
     * @return 排班信息对象
     * @throws Exception
     */
    public ResModel<RegInfo> getRegInfo(String hosId, String deptId, String doctorId, Date startDate, Date endDate) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("HOS_ID", hosId);
        map.put("DEPT_ID", deptId);
        map.put("DOCTOR_ID", doctorId);
        map.put("START_DATE", DateUtil.toDateString(startDate));
        map.put("END_DATE", DateUtil.toDateString(endDate));

        String result = invokeWS("getRegInfo", map);
        return parseEnvelop(result, RegInfo.class);
    }

    /**
     * 用户通过平台进行挂号，生成相应的订单，同时平台调用HIS的接口将订单信息同步到HIS。
     * 对于给本人挂号的，必须要填写患者身份证号码信息，取号的时候需要出示患者的身份证；
     * 对于给子女挂号的，患者身份证件号码允许为空（此种情况适用于患者是孩子还没有身份证件），取号的时候出示大人(挂号人)或者子女(被监护人)的身份证，都允许取号。
     * 对于给本人及他人挂号的，必须要填写患者姓名、身份证号码、手机号码信息，取号的时候需要出示患者的身份证；
     * 对于给没有身份证小孩挂号的，患者身份证件号码允许为空，必须填写患者姓名、手机号码及监护人身份证号码，取号的时候出示本人(挂号人)或者子女(被监护人)的身份证，都允许取号。
     * 对于锁定号源类型的类型，挂号接口提交时，HIS需要将原来锁定的号源分配给挂号的患者。
     *
     * 挂号接口增加传入医院内部用户ID号，
     * 如果平台方传空，则表示医院在挂号成功后，必须返回此医院内部用户ID号（没有查到用户的需要注册用户后返回）；
     * 如果平台方不为空，则医院根据传入的用户ID去挂号。
     *
     * @param orderRegReq   预约挂号请求对象
     * @return
     *
     * <RES>
     *   <HOSP_PATIENT_ID>A001</HOSP_PATIENT_ID>    医院病人ID
     *   <HOSP_ORDER_ID>123</HOSP_ORDER_ID>         医院订单号
     *   <HOSP_SERIAL_NUM></HOSP_SERIAL_NUM>        医院候诊号
     *   <HOSP_MEDICAL_NUM></HOSP_MEDICAL_NUM>      医院病历号或门诊号
     *   <HOSP_GETREG_DATE></HOSP_GETREG_DATE>      医院取号时间段，格式：HH24MI-HH24MI
     *   <HOSP_SEE_DOCT_ADDR></HOSP_SEE_DOCT_ADDR>  医院就诊地址
     *   <HOSP_REMARK></HOSP_REMARK>                备注
     *   <HOSP_CARD_NO></HOSP_CARD_NO>              本次就诊院内就诊卡，主要用户首诊用户挂号后在医院开卡，返回通知平台用户凭此卡就诊
     *   <IS_CONCESSIONS></IS_CONCESSIONS>          是否享受院内优惠：0-不享受 1-享受
     *   <CONCESSIONS>                              优惠信息，当IS_CONCESSIONS=1必传
     *     <CONCESSIONS_FEE></CONCESSIONS_FEE>      本次挂号优惠的总金额，无优惠返回0，单位：分
     *     <REAL_REG_FEE></REAL_REG_FEE>            在院内优惠后实际需要支付的挂号费，无优惠返回原挂号费，单位：分
     *     <REAL_TREAT_FEE></REAL_TREAT_FEE>        在院内优惠后实际需要支付的诊疗费，无优惠返回原诊疗费，单位：分
     *     <REAL_TOTAL_FEE></REAL_TOTAL_FEE>        在院内优惠后实际需要支付的总费用，无优惠返回原挂号费用，单位：分
     *     <CONCESSIONS_TYPE></CONCESSIONS_TYPE>    优惠类型，例：老人优惠，军人优惠等
     *   </CONCESSIONS>
     * </RES>
     *
     * @throws Exception
     */
    public String orderReg(OrderRegReq orderRegReq) throws Exception {
        return invokeWS("orderReg", orderRegReq.toParams());
    }

}
